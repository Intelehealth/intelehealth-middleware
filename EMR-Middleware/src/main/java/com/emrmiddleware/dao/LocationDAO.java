
package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.LocationDMO;
import com.emrmiddleware.dto.LocationDTO;
import com.emrmiddleware.exception.DAOException;
import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocationDAO {
    private final Logger logger = LoggerFactory.getLogger(LocationDAO.class);

    public ArrayList<LocationDTO> getLocations(String lastpulldatatime) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<LocationDTO> locationlist = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            LocationDMO locationdmo = (LocationDMO)session.getMapper(LocationDMO.class);
            ArrayList<LocationDTO> arrayList = locationlist = locationdmo.getLocations(lastpulldatatime);
            return arrayList;
        }
    }
}

