
package com.emrmiddleware.authentication;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath(value="/")
public class CustomApplication
extends ResourceConfig {
    public CustomApplication() {
        this.packages(new String[]{"com.emrmiddleware.rest"});
        this.packages(new String[]{"com.emrmiddleware.conf"});
        this.property("jersey.config.beanValidation.enableOutputValidationErrorEntity.server", true);
        this.property("jersey.config.beanValidation.disable.validateOnExecutableCheck.server", true);
        this.register(RolesAllowedDynamicFeature.class);
        this.register(JsonProcessingFeature.class);
        this.property("javax.json.stream.JsonGenerator.prettyPrinting", true);
    }
}

