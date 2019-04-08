package com.emrmiddleware.dao;



import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.VisitDMO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.DAOException;

public class VisitDAO {

	public ArrayList<VisitDTO> getVisits(Timestamp lastdatapulltime, String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<VisitDTO> visitlist = new ArrayList<VisitDTO>();
		try {

			VisitDMO patientdmo = session.getMapper(VisitDMO.class);
			visitlist = patientdmo.getVisits(lastdatapulltime, locationuuid);
			return visitlist;
		} catch (PersistenceException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}

}
