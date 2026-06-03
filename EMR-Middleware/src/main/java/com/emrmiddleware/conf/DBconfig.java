
package com.emrmiddleware.conf;

import com.emrmiddleware.conf.ResourcesEnvironment;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBconfig {
    private SqlSessionFactory sqlSessionFactory = null;
    ResourcesEnvironment dbenvironment = new ResourcesEnvironment();
    private static final DBconfig dbconfig = new DBconfig();
    final Logger logger = LoggerFactory.getLogger(DBconfig.class);

    private DBconfig() {
        String resource = "/db_properties.xml";
        String environment = this.dbenvironment.getDBEnvironment();
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream((String)resource);
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, environment);
            inputStream.close();
        }
        catch (IOException e) {
            this.logger.error("Exception in DBconfig", (Throwable)e);
        }
        catch (Exception e) {
            this.logger.error("Exception : ", (Throwable)e);
        }
    }

    public static final SqlSessionFactory getSessionFactory() {
        return DBconfig.dbconfig.sqlSessionFactory;
    }

    public static final SqlSession openSession() {
        return DBconfig.dbconfig.sqlSessionFactory.openSession();
    }

    public static final SqlSession openSession(ExecutorType e) {
        return DBconfig.dbconfig.sqlSessionFactory.openSession(e);
    }

    public static final SqlSession openSession(ExecutorType e, TransactionIsolationLevel tl) {
        return DBconfig.dbconfig.sqlSessionFactory.openSession(e, tl);
    }

    public static final SqlSession openSession(ExecutorType e, boolean autoCommit) {
        return DBconfig.dbconfig.sqlSessionFactory.openSession(e, autoCommit);
    }
}

