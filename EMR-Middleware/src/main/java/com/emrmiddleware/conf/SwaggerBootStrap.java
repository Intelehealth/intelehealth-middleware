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
import io.swagger.models.auth.BasicAuthDefinition;
import io.swagger.models.auth.In;
import io.swagger.models.auth.SecuritySchemeDefinition;

public class SwaggerBootStrap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ResourcesEnvironment resourcesEnv = new ResourcesEnvironment();
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.2");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost(resourcesEnv.getHostPath());
		beanConfig.setBasePath("/webapi");
		beanConfig.setResourcePackage("com.emrmiddleware.rest");
		Info info = new Info().title("Intelehealth")
				.description("Intelehealth API");
				
		Swagger swagger = new Swagger().info(info);

		/*swagger.securityDefinition("basic",
				(SecuritySchemeDefinition) new ApiKeyAuthDefinition("Authorization", In.HEADER));
         */
		 swagger.securityDefinition("basicAuth", new BasicAuthDefinition());
		  //  new SwaggerContextService().withServletConfig(servletConfig).updateSwagger(swagger);
		beanConfig.setScan(true);

		new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
	}
}