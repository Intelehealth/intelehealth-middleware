package com.emrmiddleware.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.dao.EncounterDAO;
import com.emrmiddleware.dao.LocationDAO;
import com.emrmiddleware.dao.ObsDAO;
import com.emrmiddleware.dao.PatientDAO;
import com.emrmiddleware.dao.ProviderDAO;
import com.emrmiddleware.dao.VisitDAO;
import com.emrmiddleware.dto.PatientAttributeDTO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.VisitAttributeDTO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;

/**
 * The PullDataAction class is responsible for retrieving patient, visit, encounter, and related medical data 
 * from an EMR system. It implements a paginated data retrieval mechanism to efficiently handle large datasets 
 * and supports incremental data synchronization based on timestamps.
 * 
 * <p>This class serves as a core component in the EMR middleware system, facilitating data synchronization 
 * between the server and client devices. It handles both initial data pulls and incremental updates,
 * ensuring efficient data transfer while maintaining data consistency.</p>
 */
public class PullDataAction {
  
  private static final Logger logger = LoggerFactory.getLogger(PullDataAction.class);
  
  /**
   * Reference timestamp used for initial data pulls. When lastpulldatatime equals this value,
   * the system performs an initial data pull with comprehensive master data.
   */
  public static final String DATESTRING="2006-08-22 22:21:48";

  // Shared executor with fixed small thread pool to prevent resource exhaustion
  private static final Executor executor = Executors.newFixedThreadPool(3);

  // Thread-safe DAO instances
  private final PatientDAO patientDAO;
  private final VisitDAO visitDAO;
  private final ObsDAO obsDAO;
  private final EncounterDAO encounterDAO;
  private final LocationDAO locationDAO;
  private final ProviderDAO providerDAO;

  private static final Set<Integer> processedVisitIds = new HashSet<>();

  public PullDataAction() {
    this.patientDAO = new PatientDAO();
    this.visitDAO = new VisitDAO();
    this.obsDAO = new ObsDAO();
    this.encounterDAO = new EncounterDAO();
    this.locationDAO = new LocationDAO();
    this.providerDAO = new ProviderDAO();
  }

  /**
   * Retrieves medical data from the EMR system with pagination support.
   * 
   * <p>This method handles two main scenarios:
   * <ul>
   *   <li>Initial data pull (when lastpulldatatime equals DATESTRING)</li>
   *   <li>Incremental data pull (for subsequent syncs)</li>
   * </ul>
   * </p>
   * 
   * <p>The method maintains data consistency by:
   * <ul>
   *   <li>Fetching related patient attributes for retrieved patients</li>
   *   <li>Getting visits associated with pulled patients</li>
   *   <li>Retrieving encounters and observations linked to visits</li>
   * </ul>
   * </p>
   *
   * @param lastpulldatatime Timestamp of last data pull for incremental sync
   * @param locationuuid UUID of the healthcare location
   * @param pageno Page number for pagination (0-based)
   * @param limit Maximum number of records per page
   * @return PullDataDTO containing patient information, visits, encounters, observations, and related data
   * @throws ActionException if there's an error in processing the request
   * @throws DAOException if there's an error in database operations

*/


public PullDataDTO getPullData(final String lastPullDateTime, final String locationUuid, 
        final int pageNo, final int limit) throws ActionException {
    try {
        logger.info("Starting getPullData with pageNo: {}, limit: {}, lastPullDateTime: {}, locationUuid: {}", 
            pageNo, limit, lastPullDateTime, locationUuid);
        
        if (pageNo == 0) {
            processedVisitIds.clear(); // Reset the set when starting a new pull
        }
        
        final PullDataDTO pullData = new PullDataDTO();
        int offset = calculateOffset(pageNo, limit);
        logger.info("Calculated offset: {}", offset);

        initializeArrays(pullData);
        pullData.setPullexecutedtime(visitDAO.getDBCurrentTime());

        final int patientCount = patientDAO.getPatientsCount(lastPullDateTime, locationUuid);
        final int visitCount = visitDAO.getVisitCount(lastPullDateTime, locationUuid);
        
        logger.info("Patient count: {}, Visit count: {}", patientCount, visitCount);
        
        pullData.setTotalCount(patientCount);
        
        // Calculate next page number
        if (patientCount == 0) {
            pullData.setPageNo(-1); // No data available
        } 
        else if (offset >= patientCount) {
            // If we've gone past the end, return last page
            int lastPageOffset = ((patientCount - 1) / limit) * limit;
            pullData.setPageNo(-1);
            offset = lastPageOffset;
        }
        else {
            // Calculate if there's a next page
            boolean hasNextPage = (offset + limit) < patientCount;
            pullData.setPageNo(hasNextPage ? pageNo + 1 : -1);
        }

        if (lastPullDateTime.equals(DATESTRING)) {
            logger.info("Performing initial pull");
            return handleInitialPull(lastPullDateTime, locationUuid, offset, limit, pullData);
        }

        logger.info("Performing incremental pull");
        return handleIncrementalPull(lastPullDateTime, locationUuid, offset, limit, 
                pullData, patientCount, visitCount);

    } catch (DAOException | CompletionException e) {
        logger.error("Error in getPullData", e);
        throw new ActionException(e.getMessage(), e);
    }
}



  private void initializeArrays(PullDataDTO pullData) {
    pullData.setLocationlist(new ArrayList<>());
    pullData.setPatientAttributeTypeListMaster(new ArrayList<>());
    pullData.setVisitAttributeTypeList(new ArrayList<>());
    pullData.setProviderAttributeTypeList(new ArrayList<>());
    pullData.setProviderlist(new ArrayList<>());
    pullData.setProviderAttributeList(new ArrayList<>());
    pullData.setPatientlist(new ArrayList<>());
    pullData.setPatientAttributesList(new ArrayList<>());
    pullData.setVisitlist(new ArrayList<>());
    pullData.setVisitAttributeList(new ArrayList<>());
    pullData.setEncounterlist(new ArrayList<>());
    pullData.setObslist(new ArrayList<>());
  }


  private int calculateOffset(int pageNo, int limit) {
    // Make it consistently 0-based (pageNo 0 = first page)
    return pageNo * limit;
}


  private PullDataDTO handleInitialPull(final String lastPullDateTime, final String locationUuid,
          final int offset, final int limit, final PullDataDTO pullData) throws DAOException {
    try {
      logger.info("Starting handleInitialPull with offset: {}, limit: {}", offset, limit);
      
      final ArrayList<PatientDTO> patients = patientDAO.getPatients(
              lastPullDateTime, locationUuid, offset, limit);
      logger.info("Retrieved {} patients", patients.size());

      final String patientIds = patients.isEmpty() ? "" : "(" + patients.stream()
              .map(p -> String.valueOf(p.getPatientid()))
              .collect(Collectors.joining(",")) + ")";
      logger.info("Formatted patientIds: {}", patientIds);

      if (!patients.isEmpty()) {
        CompletableFuture<ArrayList<PatientAttributeDTO>> patientAttrFuture = 
                CompletableFuture.supplyAsync(() -> {
                  try {
                    return patientDAO.getPatientAttributes(patientIds);
                  } catch (Exception e) {
                    logger.error("Error in getPatientAttributes: {}", e.getMessage(), e);
                    throw new CompletionException(e);
                  }
                }, executor);

        pullData.setPatientAttributesList(patientAttrFuture.join());
      } else {
        pullData.setPatientAttributesList(new ArrayList<>());
      }

      ArrayList<VisitDTO> visits;
      if (lastPullDateTime.equals(DATESTRING)) {
        visits = !patientIds.isEmpty() ? visitDAO.getVisits(lastPullDateTime, locationUuid, patientIds) : new ArrayList<>();
      } else {
        visits = visitDAO.getVisitsNew(lastPullDateTime, locationUuid);
      }
      logger.info("Retrieved {} visits", visits.size());

      if (!visits.isEmpty()) {
        processVisitData(lastPullDateTime, locationUuid, visits, pullData);
      }

      if (offset == 0) {
        loadMasterData(lastPullDateTime, locationUuid, pullData, offset);
      }

      pullData.setPatientlist(patients);
      pullData.setVisitlist(visits);

      return pullData;
    } catch (CompletionException e) {
      logger.error("Error in handleInitialPull", e);
      Throwable cause = e.getCause();
      if (cause instanceof DAOException) {
        throw (DAOException) cause;
      }
      throw new DAOException("Error processing data: " + e.getMessage(), e);
    }
  }

  private void loadMasterData(final String lastPullDateTime, final String locationUuid, 
          final PullDataDTO pullData, final int offset) throws DAOException {
    try {
      if (offset == 0) {
        CompletableFuture.allOf(
          CompletableFuture.supplyAsync(() -> {
            try {
              pullData.setLocationlist(locationDAO.getLocations(lastPullDateTime));
              return null;
            } catch (Exception e) {
              throw new CompletionException(e);
            }
          }, executor),
          CompletableFuture.supplyAsync(() -> {
            try {
              pullData.setPatientAttributeTypeListMaster(
                patientDAO.getPatientAttributeType(lastPullDateTime, locationUuid));
              return null;
            } catch (Exception e) {
              throw new CompletionException(e);
            }
          }, executor),
          CompletableFuture.supplyAsync(() -> {
            try {
              pullData.setVisitAttributeTypeList(
                visitDAO.getVisitAttributeTypeMaster(lastPullDateTime));
              return null;
            } catch (Exception e) {
              throw new CompletionException(e);
            }
          }, executor),
          CompletableFuture.supplyAsync(() -> {
            try {
              pullData.setProviderAttributeTypeList(
                providerDAO.getProviderAttributeTypeMaster(lastPullDateTime));
              return null;
            } catch (Exception e) {
              throw new CompletionException(e);
            }
          }, executor),
          CompletableFuture.supplyAsync(() -> {
            try {
              pullData.setProviderlist(providerDAO.getProviders(lastPullDateTime));
              return null;
            } catch (Exception e) {
              throw new CompletionException(e);
            }
          }, executor),
          CompletableFuture.supplyAsync(() -> {
            try {
              pullData.setProviderAttributeList(
                providerDAO.getProviderAttributes(lastPullDateTime));
              return null;
            } catch (Exception e) {
              throw new CompletionException(e);
            }
          }, executor)
        ).join();
      }
    } catch (CompletionException e) {
      Throwable cause = e.getCause();
      if (cause instanceof DAOException) {
        throw (DAOException) cause;
      }
      throw new DAOException("Error loading master data: " + e.getMessage(), e);
    }
  }

  private void processVisitData(final String lastPullDateTime, final String locationUuid,
          final ArrayList<VisitDTO> visits, final PullDataDTO pullData) throws DAOException {
    try {
      final String visitIds = visits.isEmpty() ? "" : formatIds(
              visits.stream().map(VisitDTO::getVisitid).collect(Collectors.toSet()));
      logger.info("Formatted visitIds: {}", visitIds);

      CompletableFuture.allOf(
        CompletableFuture.supplyAsync(() -> {
          try {
            ArrayList<VisitAttributeDTO> allAttributes = visitDAO.getVisitAttributes(visitIds);
            
            // Create a map to store unique values for each visit and attribute type
            Map<String, Set<String>> uniqueValues = new HashMap<>();
            ArrayList<VisitAttributeDTO> uniqueAttributes = new ArrayList<>();
            
            // Sort by date_created descending to get most recent values first
            allAttributes.sort((a1, a2) -> {
              String key1 = a1.getVisit_uuid() + "|" + a1.getVisit_attribute_type_uuid();
              String key2 = a2.getVisit_uuid() + "|" + a2.getVisit_attribute_type_uuid();
              int keyCompare = key1.compareTo(key2);
              if (keyCompare != 0) return keyCompare;
              return a1.getValue().compareTo(a2.getValue());
            });
            
            // Filter unique values
            for (VisitAttributeDTO attr : allAttributes) {
              String key = attr.getVisit_uuid() + "|" + attr.getVisit_attribute_type_uuid();
              Set<String> values = uniqueValues.computeIfAbsent(key, k -> new HashSet<>());
              
              // Only add if this value hasn't been seen for this visit/attribute type combination
              if (values.add(attr.getValue())) {
                uniqueAttributes.add(attr);
              }
            }
            
            pullData.setVisitAttributeList(uniqueAttributes);
            return null;
          } catch (Exception e) {
            throw new CompletionException(e);
          }
        }, executor),
        CompletableFuture.supplyAsync(() -> {
          try {
            pullData.setEncounterlist(encounterDAO.getEncounters(visitIds));
            return null;
          } catch (Exception e) {
            throw new CompletionException(e);
          }
        }, executor),
        CompletableFuture.supplyAsync(() -> {
          try {
            pullData.setObslist(obsDAO.getObsList(lastPullDateTime, locationUuid, visitIds));
            return null;
          } catch (Exception e) {
            throw new CompletionException(e);
          }
        }, executor)
      ).join();
    } catch (CompletionException e) {
      Throwable cause = e.getCause();
      if (cause instanceof DAOException) {
        throw (DAOException) cause;
      }
      throw new DAOException("Error processing visit data: " + e.getMessage(), e);
    }
  }


private PullDataDTO handleIncrementalPull(final String lastPullDateTime, final String locationUuid,
        final int offset, final int limit, final PullDataDTO pullData, 
        final int patientCount, final int visitCount) throws DAOException {
    
    logger.info("Handling incremental pull with offset: {}, limit: {}, patientCount: {}", 
        offset, limit, patientCount);
    
    // Just delegate to handleInitialPull - all pagination logic is now in getPullData
    return handleInitialPull(lastPullDateTime, locationUuid, offset, limit, pullData);
}

  private String formatIds(Set<?> ids) {
    return ids.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(",", "(", ")"));
  }
}
