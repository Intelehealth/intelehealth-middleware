
package com.emrmiddleware.dto;

public class FileDTO {
    String uuid;
    String objectuuid;
    String objectname;
    String imagerawname;
    String filepath;
    String attributes;
    int voided;
    String filename;
    String locationuuid;

    public String getLocationuuid() {
        return this.locationuuid;
    }

    public void setLocationuuid(String locationuuid) {
        this.locationuuid = locationuuid;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getObjectuuid() {
        return this.objectuuid;
    }

    public void setObjectuuid(String objectuuid) {
        this.objectuuid = objectuuid;
    }

    public String getObjectname() {
        return this.objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    public String getImagerawname() {
        return this.imagerawname;
    }

    public void setImagerawname(String imagerawname) {
        this.imagerawname = imagerawname;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getAttributes() {
        return this.attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public int getVoided() {
        return this.voided;
    }

    public void setVoided(int voided) {
        this.voided = voided;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

