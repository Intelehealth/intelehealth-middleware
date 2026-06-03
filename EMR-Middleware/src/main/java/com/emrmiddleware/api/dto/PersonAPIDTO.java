
package com.emrmiddleware.api.dto;

import com.emrmiddleware.api.dto.AddressAPIDTO;
import com.emrmiddleware.api.dto.AttributeAPIDTO;
import com.emrmiddleware.api.dto.NameAPIDTO;
import java.util.ArrayList;

public class PersonAPIDTO {
    private String uuid;
    private ArrayList<NameAPIDTO> names = new ArrayList();
    private String birthdate;
    ArrayList<AttributeAPIDTO> attributes = new ArrayList();
    private ArrayList<AddressAPIDTO> addresses = new ArrayList();
    private String gender;

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ArrayList<NameAPIDTO> getNames() {
        return this.names;
    }

    public void setNames(ArrayList<NameAPIDTO> names) {
        this.names = names;
    }

    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public ArrayList<AttributeAPIDTO> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(ArrayList<AttributeAPIDTO> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<AddressAPIDTO> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(ArrayList<AddressAPIDTO> addresses) {
        this.addresses = addresses;
    }

    public void addAddresses(AddressAPIDTO addressdto) {
        this.addresses.add(addressdto);
    }

    public void addName(NameAPIDTO namedto) {
        this.names.add(namedto);
    }
}

