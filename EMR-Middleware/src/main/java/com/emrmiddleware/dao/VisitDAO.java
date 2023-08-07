package com.emrmiddleware.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.exceptions.CJConnectionFeatureNotAvailableException;
import com.mysql.cj.jdbc.ConnectionGroupManager;
import com.sun.tools.sjavac.server.Sjavac;
import com.sun.tools.sjavac.server.SjavacServer;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.VisitDMO;
import com.emrmiddleware.dto.VisitAttributeDTO;
import com.emrmiddleware.dto.VisitAttributeTypeDTO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.DAOException;

public class VisitDAO {

	private final Logger logger = LoggerFactory.getLogger(VisitDAO.class);

	public ArrayList<VisitDTO> getVisits(String lastpulldatatime, String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<VisitDTO> visitlist = new ArrayList<VisitDTO>();
		try {

			VisitDMO patientdmo = session.getMapper(VisitDMO.class);
			visitlist = patientdmo.getVisits(lastpulldatatime, locationuuid);
			return visitlist;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public VisitDTO getVisit(String visituuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		VisitDTO visitdto = new VisitDTO();
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			visitdto = visitdmo.getVisit(visituuid);
			return visitdto;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public String getDBCurrentTime() throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		String currentTime=null;
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			currentTime = visitdmo.getDBCurrentTime();
			return currentTime;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public ArrayList<VisitAttributeTypeDTO> getVisitAttributeTypeMaster(String lastpulldatatime) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<VisitAttributeTypeDTO> visitAttributeMasterList= new ArrayList<VisitAttributeTypeDTO>();
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			visitAttributeMasterList = visitdmo.getVisitAttributeMaster(lastpulldatatime);
			return visitAttributeMasterList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}
	
	public ArrayList<VisitAttributeDTO> getVisitAttributes(String lastpulldatatime,String locationuuid) throws DAOException {

		SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
		SqlSession session = sessionfactory.openSession();
		ArrayList<VisitAttributeDTO> visitAttributesList= new ArrayList<VisitAttributeDTO>();
		try {

			VisitDMO visitdmo = session.getMapper(VisitDMO.class);
			visitAttributesList = visitdmo.getVisitAttributes(lastpulldatatime, locationuuid);
			return visitAttributesList;
		} catch (PersistenceException e) {
			logger.error(e.getMessage(),e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			session.close();
		}
	}


	/*
	Method to void all previous visit_holder attribute rows in visit_attribute table
	for this visit -- Mithun and Zeeshan need this - 03082023
	 */
	public void voidVisitHolder(String uuid)  {


		String voidVisitHolders = "update visit_attribute set voided=1, " +
				"date_voided = now(), " +
				"void_reason = 'Nurse Change' " +
				"WHERE visit_id = (select visit_id from visit where uuid = ? ) " +
				"AND attribute_type_id = 7";
		try {


			Class.forName("com.mysql.jdbc.Driver");
			Connection con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs", "root", "i10hi1c");

			PreparedStatement pstmt = con.prepareStatement(voidVisitHolders);

			pstmt.setString(1,uuid);
			int jum = pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch(ClassNotFoundException e) {
			logger.error(e.getMessage(),e);

		}
		catch (SQLException e ) {
			logger.error(e.getMessage(),e);

		}


	}
}

