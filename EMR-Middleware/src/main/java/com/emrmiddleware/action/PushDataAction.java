
package com.emrmiddleware.action;

import com.emrmiddleware.action.AppointmentAction;
import com.emrmiddleware.action.EncounterAction;
import com.emrmiddleware.action.PatientAction;
import com.emrmiddleware.action.PersonAction;
import com.emrmiddleware.action.VisitAction;
import com.emrmiddleware.api.dto.AppointmentDTO;
import com.emrmiddleware.api.dto.EncounterAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.api.dto.VisitAPIDTO;
import com.emrmiddleware.conf.DBconfig;
import com.emrmiddleware.dmo.ProviderDMO;
import com.emrmiddleware.dto.CustomAppointmentDTO;
import com.emrmiddleware.dto.EncounterDTO;
import com.emrmiddleware.dto.PatientDTO;
import com.emrmiddleware.dto.PersonDTO;
import com.emrmiddleware.dto.ProviderDTO;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.PushDataDTO;
import com.emrmiddleware.dto.VisitDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class PushDataAction {
    String authString;

    public PushDataAction(String auth) {
        this.authString = auth;
    }

    public PullDataDTO pushData(PushDataDTO pushdatadto) throws DAOException, ActionException {
        ArrayList<PersonAPIDTO> personList = new ArrayList();
        PullDataDTO pulldatadto = new PullDataDTO();
        try {
            personList = pushdatadto.getPersons();
            ArrayList<PatientAPIDTO> patientList = pushdatadto.getPatients();
            ArrayList<VisitAPIDTO> visitList = pushdatadto.getVisits();
            ArrayList<EncounterAPIDTO> encounterList = pushdatadto.getEncounters();
            ArrayList<ProviderDTO> providerlist = pushdatadto.getProviders();
            ArrayList<AppointmentDTO> appointmentDTOArrayList = pushdatadto.getAppointments();
            PersonAction personaction = new PersonAction(this.authString);
            PatientAction patientaction = new PatientAction(this.authString);
            VisitAction visitaction = new VisitAction(this.authString);
            EncounterAction encounteraction = new EncounterAction(this.authString);
            AppointmentAction appointmentAction = new AppointmentAction(this.authString);
            if (personList != null) {
                ArrayList<PersonDTO> persons = personaction.setPersons(personList);
                pulldatadto.setPersonList(persons);
            }
            if (patientList != null) {
                ArrayList<PatientDTO> patients = patientaction.setPatients(patientList);
                pulldatadto.setPatientlist(patients);
            }
            if (visitList != null) {
                ArrayList<VisitDTO> visits = visitaction.setVisits(visitList);
                pulldatadto.setVisitlist(visits);
            }
            if (encounterList != null) {
                ArrayList<EncounterDTO> encounters = encounteraction.setEncounters(encounterList);
                pulldatadto.setEncounterlist(encounters);
            }
            if (providerlist != null) {
                for (ProviderDTO provider : providerlist) {
                    pulldatadto.setProviderlist(this.getProviders(new Integer(provider.providerId)));
                }
            }
            if (appointmentDTOArrayList != null) {
                ArrayList<CustomAppointmentDTO> customAppointmentDTOArrayList = this.updateAppointments(appointmentDTOArrayList);
                boolean b = appointmentAction.addAppointmentOpenMRS(customAppointmentDTOArrayList);
                pulldatadto.setAppointmentList(customAppointmentDTOArrayList);
            }
        }
        catch (Exception e) {
            throw new ActionException(e.getMessage(), e);
        }
        return pulldatadto;
    }

    private ArrayList<CustomAppointmentDTO> updateAppointments(ArrayList<AppointmentDTO> appointmentDTOArrayList) {
        ArrayList<CustomAppointmentDTO> customAppointmentDTOArrayList = new ArrayList<CustomAppointmentDTO>();
        for (AppointmentDTO appointmentDTO : appointmentDTOArrayList) {
            CustomAppointmentDTO customAppointmentDTO = new CustomAppointmentDTO();
            customAppointmentDTO.setAppointmentId(appointmentDTO.getAppointmentId());
            customAppointmentDTO.setSlotDay(appointmentDTO.getSlotDay());
            customAppointmentDTO.setSlotDate(appointmentDTO.getSlotDate());
            customAppointmentDTO.setSlotDuration(appointmentDTO.getSlotDuration());
            customAppointmentDTO.setSlotDurationUnit(appointmentDTO.getSlotDurationUnit());
            customAppointmentDTO.setSlotTime(appointmentDTO.getSlotTime());
            customAppointmentDTO.setSpeciality(appointmentDTO.getSpeciality());
            customAppointmentDTO.setUserUuid(appointmentDTO.getUserUuid());
            customAppointmentDTO.setDrName(appointmentDTO.getDrName());
            customAppointmentDTO.setVisitUuid(appointmentDTO.getVisitUuid());
            customAppointmentDTO.setPatientName(appointmentDTO.getPatientName());
            customAppointmentDTO.setOpenMrsId(appointmentDTO.getOpenMrsId());
            customAppointmentDTO.setPatientId(appointmentDTO.getPatientId());
            customAppointmentDTO.setLocationUuid(appointmentDTO.getLocationUuid());
            customAppointmentDTO.setHwUUID(appointmentDTO.getHwUUID());
            customAppointmentDTO.setReason(appointmentDTO.getReason());
            customAppointmentDTO.setVoided(appointmentDTO.getVoided());
            customAppointmentDTO.setSyncd(false);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/openmrs", "root", "i10hi1c");
                PreparedStatement pstmtPatient = con.prepareStatement("select gender, TIMESTAMPDIFF(YEAR, birthdate, now()) FROM person WHERE uuid = ? ");
                PreparedStatement pstmtHealthWorker = con.prepareStatement("select a.gender, ifnull(TIMESTAMPDIFF(YEAR, a.birthdate, now()), 'NA'), concat_ws(' ', b.given_name, b.middle_name, b.family_name) FROM person a, person_name b WHERE a.person_id = b.person_id and a.person_id  = (select person_id from provider where uuid = ?)  ");
                pstmtPatient.setString(1, appointmentDTO.getPatientId());
                ResultSet rstPatient = pstmtPatient.executeQuery();
                rstPatient.next();
                customAppointmentDTO.setPatientGender(rstPatient.getString(1));
                customAppointmentDTO.setPatientAge(rstPatient.getString(2));
                pstmtHealthWorker.setString(1, appointmentDTO.getHwUUID());
                ResultSet rstHW = pstmtHealthWorker.executeQuery();
                rstHW.next();
                customAppointmentDTO.setHwName(rstHW.getString(3));
                customAppointmentDTO.setHwAge(rstHW.getString(2));
                customAppointmentDTO.setHwGender(rstHW.getString(1));
                customAppointmentDTOArrayList.add(customAppointmentDTO);
                rstHW.close();
                rstPatient.close();
                pstmtHealthWorker.close();
                pstmtPatient.close();
                con.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return customAppointmentDTOArrayList;
    }

    private ArrayList<ProviderDTO> getProviders(int providerId) {
        SqlSessionFactory sessionfactory = DBconfig.getSessionFactory();
        SqlSession session = sessionfactory.openSession();
        ArrayList<ProviderDTO> providerlist = new ArrayList<ProviderDTO>();
        try {
            ProviderDMO providerdmo = (ProviderDMO)session.getMapper(ProviderDMO.class);
            providerlist = providerdmo.getProviders2(providerId);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return providerlist;
    }
}

