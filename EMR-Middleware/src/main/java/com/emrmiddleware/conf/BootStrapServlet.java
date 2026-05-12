/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.jaxrs.config.BeanConfig
 *  io.swagger.jaxrs.config.SwaggerContextService
 *  io.swagger.models.Swagger
 *  io.swagger.models.auth.BasicAuthDefinition
 *  io.swagger.models.auth.SecuritySchemeDefinition
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServlet
 */
package com.emrmiddleware.conf;

import com.emrmiddleware.conf.ResourcesEnvironment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Swagger;
import io.swagger.models.auth.BasicAuthDefinition;
import io.swagger.models.auth.SecuritySchemeDefinition;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BootStrapServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig paramServletConfig) throws ServletException {
        super.init(paramServletConfig);
        BeanConfig config = new BeanConfig();
        ResourcesEnvironment resourcesEnv = new ResourcesEnvironment();
        config.setResourcePackage("com.emrmiddleware.rest");
        config.setSchemes(new String[]{"http", "https"});
        config.setTitle("Intelehealth ");
        config.setVersion("1.0");
        config.setDescription("Intelehealth API");
        config.setHost(resourcesEnv.getHostPath());
        config.setBasePath("/webapi");
        Swagger swagger = new Swagger();
        swagger.securityDefinition("basicAuth", (SecuritySchemeDefinition)new BasicAuthDefinition());
        new SwaggerContextService().withServletConfig(paramServletConfig).updateSwagger(swagger);
        config.setScan(true);
    }
}

