package com.emrmiddleware.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.api.dto.AppointmentDTO;
import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.ProviderDMO;
import com.emrmiddleware.dto.CustomAppointmentDTO;
import com.emrmiddleware.dto.ProviderDTO;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.PushDataDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;

/**
 * Action class responsible for pushing data to the system.
 * Handles various types of data including persons, patients, visits, encounters, providers and appointments.
 */
public class PushDataAction {
    private static final Logger logger = LoggerFactory.getLogger(PushDataAction.class);
    private final String authString;

    public PushDataAction(String auth) {
        this.authString = auth;
    }

    /**
     * Pushes various types of data to the system
     * @param pushdatadto DTO containing all the data to be pushed
     * @return PullDataDTO containing the processed data
     * @throws DAOException if database operations fail
     * @throws ActionException if any other operation fails
     */
    public PullDataDTO pushData(PushDataDTO pushdatadto) throws DAOException, ActionException {
        PullDataDTO pulldatadto = new PullDataDTO();
        
        try {
            processPersons(pushdatadto, pulldatadto);
            processPatients(pushdatadto, pulldatadto);
            processVisits(pushdatadto, pulldatadto);
            processEncounters(pushdatadto, pulldatadto);
            processProviders(pushdatadto, pulldatadto);
            processAppointments(pushdatadto, pulldatadto);
            
        } catch (Exception e) {
            logger.error("Error processing data: {}", e.getMessage(), e);
            throw new ActionException("Failed to process data: " + e.getMessage(), e);
        }
        
        return pulldatadto;
    }

    private void processPersons(PushDataDTO pushdatadto, PullDataDTO pulldatadto) throws DAOException {
        Optional.ofNullable(pushdatadto.getPersons())
                .ifPresent(personList -> {
                    PersonAction personaction = new PersonAction(authString);
            try {
                pulldatadto.setPersonList(personaction.setPersons(personList));
            } catch (DAOException ex) {
            }
                });
    }

    private void processPatients(PushDataDTO pushdatadto, PullDataDTO pulldatadto) throws DAOException {
        Optional.ofNullable(pushdatadto.getPatients())
                .ifPresent(patientList -> {
                    PatientAction patientaction = new PatientAction(authString);
            try {
                pulldatadto.setPatientlist(patientaction.setPatients(patientList));
            } catch (DAOException | ActionException ex) {
            }
                });
    }

    private void processVisits(PushDataDTO pushdatadto, PullDataDTO pulldatadto) throws DAOException {
        Optional.ofNullable(pushdatadto.getVisits())
                .ifPresent(visitList -> {
                    VisitAction visitaction = new VisitAction(authString);
            try {
                pulldatadto.setVisitlist(visitaction.setVisits(visitList));
            } catch (DAOException ex) {
            }
                });
    }

    private void processEncounters(PushDataDTO pushdatadto, PullDataDTO pulldatadto) throws DAOException {
        Optional.ofNullable(pushdatadto.getEncounters())
                .ifPresent(encounterList -> {
                    try {
                        EncounterAction encounteraction = new EncounterAction(authString);
                        try {
                            pulldatadto.setEncounterlist(encounteraction.setEncounters(encounterList));
                        } catch (ActionException ex) {
                        }
                    } catch (DAOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void processProviders(PushDataDTO pushdatadto, PullDataDTO pulldatadto) {
        Optional.ofNullable(pushdatadto.getProviders())
                .ifPresent(providerList -> {
                    Set<ProviderDTO> uniqueProviders = new HashSet<>();
                    providerList.forEach(provider -> 
                        uniqueProviders.addAll(getProviders(Integer.parseInt(provider.providerId))));
                    pulldatadto.setProviderlist(new ArrayList<>(uniqueProviders));
                });
    }

    private void processAppointments(PushDataDTO pushdatadto, PullDataDTO pulldatadto) {
        Optional.ofNullable(pushdatadto.getAppointments())
                .ifPresent(appointmentList -> {
                    AppointmentAction appointmentAction = new AppointmentAction(authString);
                    ArrayList<CustomAppointmentDTO> customAppointments = updateAppointments(appointmentList);
                    appointmentAction.addAppointmentOpenMRS(customAppointments);
                    pulldatadto.setAppointmentList(customAppointments);
                });
    }

    private ArrayList<CustomAppointmentDTO> updateAppointments(ArrayList<AppointmentDTO> appointmentDTOArrayList) {
        return appointmentDTOArrayList.stream()
                .map(this::createCustomAppointmentDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private CustomAppointmentDTO createCustomAppointmentDTO(AppointmentDTO appointmentDTO) {
        CustomAppointmentDTO customAppointmentDTO = new CustomAppointmentDTO();
        
        // Set basic properties
        customAppointmentDTO.setAppointmentId(appointmentDTO.getAppointmentId());
        customAppointmentDTO.setSlotDay(appointmentDTO.getSlotDay());
        customAppointmentDTO.setSlotDate(appointmentDTO.getSlotDate());
        customAppointmentDTO.setSlotDuration(appointmentDTO.getSlotDuration());
        customAppointmentDTO.setSlotDurationUnit(appointmentDTO.getSlotDurationUnit());
        customAppointmentDTO.setSlotTime(appointmentDTO.getSlotTime());
        customAppointmentDTO.setSpeciality(appointmentDTO.getSpeciality());
        customAppointmentDTO.setUserUuid(appointmentDTO.getUserUuid());
        customAppointmentDTO.setDrName(appointmentDTO.getDrName());
        customAppointmentDTO.setVisitUuid(appointmentDTO.getVisitUuid());
        customAppointmentDTO.setPatientName(appointmentDTO.getPatientName());
        customAppointmentDTO.setOpenMrsId(appointmentDTO.getOpenMrsId());
        customAppointmentDTO.setPatientId(appointmentDTO.getPatientId());
        customAppointmentDTO.setLocationUuid(appointmentDTO.getLocationUuid());
        customAppointmentDTO.setHwUUID(appointmentDTO.getHwUUID());
        customAppointmentDTO.setReason(appointmentDTO.getReason());
        customAppointmentDTO.setVoided(appointmentDTO.getVoided());
        customAppointmentDTO.setSyncd(false);

        // Fetch additional data from database
        fetchAdditionalData(customAppointmentDTO, appointmentDTO);

        return customAppointmentDTO;
    }

    private void fetchAdditionalData(CustomAppointmentDTO customAppointmentDTO, AppointmentDTO appointmentDTO) {
        String dbUrl = System.getenv("DBURL");
        String dbUser = System.getenv("DBUSER");
        String dbPass = System.getenv("DBPASS");

        if (dbUrl == null || dbUser == null || dbPass == null) {
            logger.error("Database credentials not properly configured");
            return;
        }

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             PreparedStatement pstmtPatient = con.prepareStatement("select gender, TIMESTAMPDIFF(YEAR, birthdate, now()) FROM person WHERE uuid = ?");
             PreparedStatement pstmtHealthWorker = con.prepareStatement("select a.gender, ifnull(TIMESTAMPDIFF(YEAR, a.birthdate, now()), 'NA'), concat_ws(' ', b.given_name, b.middle_name, b.family_name) FROM person a, person_name b WHERE a.person_id = b.person_id and a.person_id = (select person_id from provider where uuid = ?)")) {
            
            fetchPatientData(customAppointmentDTO, pstmtPatient, appointmentDTO.getPatientId());
            fetchHealthWorkerData(customAppointmentDTO, pstmtHealthWorker, appointmentDTO.getHwUUID());
            
        } catch (Exception e) {
            logger.error("Error fetching additional data: {}", e.getMessage(), e);
        }
    }

    private void fetchPatientData(CustomAppointmentDTO customAppointmentDTO, PreparedStatement pstmt, String patientId) throws Exception {
        pstmt.setString(1, patientId);
        try (ResultSet rstPatient = pstmt.executeQuery()) {
            if (rstPatient.next()) {
                customAppointmentDTO.setPatientGender(rstPatient.getString(1));
                customAppointmentDTO.setPatientAge(rstPatient.getString(2));
            }
        }
    }

    private void fetchHealthWorkerData(CustomAppointmentDTO customAppointmentDTO, PreparedStatement pstmt, String hwUUID) throws Exception {
        pstmt.setString(1, hwUUID);
        try (ResultSet rstHW = pstmt.executeQuery()) {
            if (rstHW.next()) {
                customAppointmentDTO.setHwName(rstHW.getString(3));
                customAppointmentDTO.setHwAge(rstHW.getString(2));
                customAppointmentDTO.setHwGender(rstHW.getString(1));
            }
        }
    }

    private ArrayList<ProviderDTO> getProviders(int providerId) {
        try (SqlSession session = DBconfig.getSessionFactory().openSession()) {
            ProviderDMO providerdmo = session.getMapper(ProviderDMO.class);
            return providerdmo.getProviders2(providerId);
        } catch (Exception e) {
            logger.error("Error fetching providers: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
