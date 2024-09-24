package com.emrmiddleware.dao;

import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.MindmapDMO;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MindmapDAO {

    private final Logger logger = LoggerFactory.getLogger(MindmapDAO.class);

    public JsonObject getConfigFile() throws DAOException, RuntimeException {

        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        SqlSession session = sessionfactory.openSession();
        JsonObject configFileContent  = new JsonObject();
        try {
           MindmapDMO mindmapDMO = session.getMapper(MindmapDMO.class);
          String fname = mindmapDMO.getConfigFileName();
          String json =  readUrl("https://dev.intelehealth.org:4004/configs/"+fname);

            Gson gson = new Gson();
            configFileContent = gson.fromJson(json, JsonObject.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return configFileContent;
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
