package com.emrmiddleware.conf;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import io.swagger.models.auth.SecuritySchemeDefinition;

public class SwaggerBootStrap extends HttpServlet {
	private static final long serialVersionUID = 1L;

@Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("139.59.88.232:8080/MahindraAgri");
        beanConfig.setBasePath("/webapi");
        beanConfig.setResourcePackage("com.mahindraagri.rest");
        Info info = new Info()
      	      .title("Mahindra Agri")
      	      .description("Mahindra Agri is the best way to keep you updated with your crop growth for best yeild " )
      	      .termsOfService("http://MahindraAgri/terms/")
      	      .contact(new Contact()
      	        .email("admins@mahindraAgri.com"))
      	      .license(new License()
      	        .name("Apache 2.0")
      	        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));
       
	    Swagger swagger = new Swagger().info(info);
	  
	    swagger.securityDefinition("Bearer", (SecuritySchemeDefinition) new ApiKeyAuthDefinition("Authorization", In.HEADER));
	       
        beanConfig.setScan(true);
        
        	   
        	   new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
    }
}