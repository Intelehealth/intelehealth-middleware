
package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.EncounterDMO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.exception.DAOException;
import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncounterDAO {
    private final Logger logger = LoggerFactory.getLogger(EncounterDAO.class);

    public ArrayList<EncounterDTO> getEncounters(String lastpulldatatime, String locationuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<EncounterDTO> encounterlist = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            EncounterDMO encounterdmo = (EncounterDMO)session.getMapper(EncounterDMO.class);
            ArrayList<EncounterDTO> arrayList = encounterlist = encounterdmo.getEncounters(lastpulldatatime, locationuuid);
            return arrayList;
        }
    }

    public EncounterDTO getEncounter(String encounteruuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        EncounterDTO encounterdto = new EncounterDTO();
        try (SqlSession session = sessionfactory.openSession();){
            EncounterDMO encounterdmo = (EncounterDMO)session.getMapper(EncounterDMO.class);
            EncounterDTO encounterDTO = encounterdto = encounterdmo.getEncounter(encounteruuid);
            return encounterDTO;
        }
    }
}

