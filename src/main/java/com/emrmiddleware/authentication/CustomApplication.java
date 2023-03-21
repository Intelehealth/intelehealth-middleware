package com.emrmiddleware.authentication;

import javax.json.stream.JsonGenerator;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
 


@ApplicationPath("/")
public class    CustomApplication extends ResourceConfig
{
    public CustomApplication() 
    { 
        packages("com.emrmiddleware.rest");
        packages("com.emrmiddleware.conf");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        // @ValidateOnExecution annotations on subclasses won't cause errors.
        property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        register(RolesAllowedDynamicFeature.class);
      //  register(AuthenticationFilter.class);
      /*  register(AuthenticationResponseFilter.class);
        register(ClientAccessFilter.class);
        register(com.mahindraagri.conf.ConstraintViolationMapper.class);*/
        /*-------------------------------Trying to enable moxy-------*/
       register(JsonProcessingFeature.class);
        property(JsonGenerator.PRETTY_PRINTING, true);
        /*-------------------------------*/
      
       
        
    }
}
