
package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.ProviderDMO;
import com.emrmiddleware.dto.ProviderAttributeDTO;
import com.emrmiddleware.dto.ProviderAttributeTypeDTO;
import com.emrmiddleware.dto.ProviderDTO;
import com.emrmiddleware.exception.DAOException;
import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderDAO {
    private final Logger logger = LoggerFactory.getLogger(ProviderDAO.class);

    public ArrayList<ProviderDTO> getProviders(String lastpulldatatime) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<ProviderDTO> providerlist = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            ProviderDMO providerdmo = (ProviderDMO)session.getMapper(ProviderDMO.class);
            ArrayList<ProviderDTO> arrayList = providerlist = providerdmo.getProviders();
            return arrayList;
        }
    }

    public ArrayList<ProviderAttributeTypeDTO> getProviderAttributeTypeMaster(String lastpulldatatime) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<ProviderAttributeTypeDTO> providerAttributeTypeList = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            ProviderDMO providerdmo = (ProviderDMO)session.getMapper(ProviderDMO.class);
            ArrayList<ProviderAttributeTypeDTO> arrayList = providerAttributeTypeList = providerdmo.getProviderAttributeTypeMaster(lastpulldatatime);
            return arrayList;
        }
    }

    public ArrayList<ProviderAttributeDTO> getProviderAttributes(String lastpulldatatime) throws DAOException {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        ArrayList<ProviderAttributeDTO> providerAttributeList = new ArrayList();
        try (SqlSession session = sessionfactory.openSession();){
            ProviderDMO providerdmo = (ProviderDMO)session.getMapper(ProviderDMO.class);
            ArrayList<ProviderAttributeDTO> arrayList = providerAttributeList = providerdmo.getProviderAttributes(lastpulldatatime);
            return arrayList;
        }
    }
}

