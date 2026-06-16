package com.emrmiddleware.action;

import com.emrmiddleware.api.APIClient;
import com.emrmiddleware.api.RestAPI;
import com.emrmiddleware.api.dto.AttributeAPIDTO;
import com.emrmiddleware.api.dto.LinkDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class LinkAction {
    private final Logger logger = LoggerFactory.getLogger(LinkAction.class);
    APIClient apiclient;
    RestAPI restapiintf;
    String authString;

    public LinkAction(String authString) {
        this.authString = authString;
        apiclient = new APIClient(authString);
        restapiintf = apiclient.getMMClient().create(RestAPI.class);
    }

    public void genLink(LinkDTO linkDTO) {
        try {
            Call<ResponseBody> genLink = restapiintf.generateShortLink(linkDTO);
            Response<ResponseBody> response = genLink.execute();
            String val = response.body().string();

            JsonObject jsonObject = new JsonParser().parse(val).getAsJsonObject();

            JsonObject hash = jsonObject.get("data").getAsJsonObject();
            String hashed = hash.get("hash").getAsString();
            String visitUUID = linkDTO.getLink().split("/")[2];
            logger.info("Hash for visit {} is {}",
                    visitUUID,
                    hashed);
            AttributeAPIDTO attributeAPIDTO = new AttributeAPIDTO();

            attributeAPIDTO.setAttributeType(System.getenv("SUMMARY_LINK"));
            attributeAPIDTO.setValue("/i/" + hashed);

            VisitAction visitAction = new VisitAction(authString);
            visitAction.addSummaryLink(visitUUID, attributeAPIDTO);

        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }
    }
}
