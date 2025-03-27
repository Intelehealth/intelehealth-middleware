package com.emrmiddleware.api.dto;

public class LinkDTO {
    static final String type = "visit-summary-verification";
    String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }


}
