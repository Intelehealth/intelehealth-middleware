
package com.emrmiddleware.action;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.dao.PersonDAO;
import com.emrmiddleware.dto.PersonDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

public class PersonAction {
    private final Logger logger = LoggerFactory.getLogger(PersonAction.class);
    APIClient apiclient;
    RestAPI restapiintf;
    String authString;

    public PersonAction(String auth) {
        this.authString = auth;
        this.apiclient = new APIClient(this.authString);
        this.restapiintf = (RestAPI)this.apiclient.getClient().create(RestAPI.class);
    }

    public ArrayList<PersonDTO> setPersons(ArrayList<PersonAPIDTO> personList) throws DAOException, ActionException {
        ArrayList<PersonDTO> persons = new ArrayList<PersonDTO>();
        PersonAPIDTO personforerror = new PersonAPIDTO();
        boolean isPersonSet = true;
        Gson gson = new Gson();
        try {
            Iterator<PersonAPIDTO> iterator = personList.iterator();
            while (iterator.hasNext()) {
                PersonAPIDTO person;
                personforerror = person = iterator.next();
                isPersonSet = this.isPersonExists(person.getUuid()) ? this.editPersonOpenMRS(person) : this.addPersonOpenMRS(person);
                PersonDTO persondto = new PersonDTO();
                persondto.setUuid(person.getUuid());
                persondto.setSyncd(isPersonSet);
                persons.add(persondto);
            }
        }
        catch (Exception e) {
            this.logger.error("Error occurred for json string : " + gson.toJson((Object)personforerror));
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        return persons;
    }

    private boolean isPersonExists(String personuuid) throws DAOException {
        boolean isPersonExists = false;
        PersonDAO persondao = new PersonDAO();
        PersonDTO persondto = persondao.getPerson(personuuid);
        if (persondto != null) {
            isPersonExists = true;
        }
        return isPersonExists;
    }

    private boolean addPersonOpenMRS(PersonAPIDTO persondto) {
        Gson gson = new Gson();
        String val = "";
        this.logger.info("add person value : " + gson.toJson((Object)persondto));
        try {
            Call<ResponseBody> callperson = this.restapiintf.addPerson(persondto);
            Response response = callperson.execute();
            if (!response.isSuccessful()) {
                val = response.errorBody().string();
                this.logger.error("REST failed : " + val);
                return false;
            }
            val = ((ResponseBody)response.body()).string();
            this.logger.info("Response is : " + val);
        }
        catch (IOException | NullPointerException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        return true;
    }

    private boolean editPersonOpenMRS(PersonAPIDTO persondto) {
        Gson gson = new Gson();
        String val = "";
        this.logger.info("edit person value : " + gson.toJson((Object)persondto));
        try {
            Call<ResponseBody> callperson = this.restapiintf.editPerson(persondto.getUuid(), persondto);
            Response response = callperson.execute();
            if (!response.isSuccessful()) {
                val = response.errorBody().string();
                this.logger.error("REST failed : " + val);
                return false;
            }
            val = ((ResponseBody)response.body()).string();
            this.logger.info("Response for edit is : " + val);
        }
        catch (IOException | NullPointerException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        return true;
    }
}

