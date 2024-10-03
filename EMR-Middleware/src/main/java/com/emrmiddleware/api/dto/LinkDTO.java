package com.emrmiddleware.api.dto;

public class LinkDTO {
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    String link;

    public String getType() {
        return type;
    }

   static final String type = "visit-summary-verification";


}
