package com.jsp.workSpace.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WorkSpaceConfig {  //to check apis 
	@Bean  //to get details in webpage,like company name, url for details , email for any complaint to raise by client
	public Docket getDocket() {   //after writting  complete this method run application, after running open chrome and type ""
		Contact contact=new Contact("Jspiders", "www.jspiders.com", "jspiders@gmail.com"); //we need contact object in apiinfo as parameter and arguments with ("companyname","url",email)
		List<VendorExtension> extensions=new ArrayList<VendorExtension>();//we need contact object in apiinfo as parameter 
		ApiInfo apiInfo=new ApiInfo("workSpace", "description", "1.01", "termsOfServiceUrl", contact, "license", "licenseUrl", extensions);  //paramenrs are(projectname, description, version, termsOfServiceUrl, license, licenseUrl, contact object reference variable)
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.jsp.workSpace")).build().apiInfo(apiInfo).useDefaultResponseMessages(false);
		
		
			
	}
	

}
