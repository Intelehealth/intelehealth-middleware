package com.emrmiddleware.conf;

import org.slf4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.LoggerFactory;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;

public class DBFileConfig {

	private SqlSessionFactory sqlSessionFactory=null;
    ResourcesEnvironment dbenvironment = new ResourcesEnvironment();
	private static final DBFileConfig dbfileconfig = new DBFileConfig();
	final Logger logger = LoggerFactory.getLogger(DBconfig.class);

	private DBFileConfig() {
	
		String resource = "/db_properties.xml";
		String environment = dbenvironment.getFileDBEnvironment();
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,environment);
			inputStream.close();
		} catch (IOException e) {
			logger.error("Exception in DBconfig",e);		
		} catch(Exception e){
			logger.error("Exception : ",e);
		}

	}

	public static final SqlSessionFactory getSessionFactory() {
		return dbfileconfig.sqlSessionFactory;
	}

	public static final SqlSession openSession() {
		return dbfileconfig.sqlSessionFactory.openSession();
	}

	public static final SqlSession openSession(ExecutorType e) {
		return dbfileconfig.sqlSessionFactory.openSession(e);
	}

	public static final SqlSession openSession(ExecutorType e, TransactionIsolationLevel tl) {
		return dbfileconfig.sqlSessionFactory.openSession(e, tl);
	}

	public static final SqlSession openSession(ExecutorType e, boolean autoCommit) {
		return dbfileconfig.sqlSessionFactory.openSession(e, autoCommit);
	}

}
