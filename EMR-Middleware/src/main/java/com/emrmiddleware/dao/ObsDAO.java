
package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.ObsDMO;
import com.emrmiddleware.dto.ObsDTO;
import com.emrmiddleware.exception.DAOException;
import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObsDAO {
    private final Logger logger = LoggerFactory.getLogger(ObsDAO.class);

    public ObsDTO getObs(String obsuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ObsDTO obs = new ObsDTO();
        try (SqlSession session = sessionfactory.openSession();){
            ObsDMO obsdmo = (ObsDMO)session.getMapper(ObsDMO.class);
            ObsDTO obsDTO = obs = obsdmo.getObs(obsuuid);
            return obsDTO;
        }
    }

    public ArrayList<ObsDTO> getObsList(String lastpulldatatime, String locationuuid) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<ObsDTO> obslist = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            ObsDMO obsdmo = (ObsDMO)session.getMapper(ObsDMO.class);
            ArrayList<ObsDTO> arrayList = obslist = obsdmo.getObsList(lastpulldatatime, locationuuid);
            return arrayList;
        }
    }
}

