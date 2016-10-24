package com.xaltome.smartcity.carparks.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@EnableScheduling
@ComponentScan({ 	"com.xaltome.smartcity.carparks.rest.controller", 
					"com.xaltome.smartcity.carparks.dbservice", 
					"com.xaltome.smartcity.carparks.config",
					"com.xaltome.smartcity.carparks.scheduler",
					"com.xaltome.smartcity.carparks.util"})
public class SpringRootConfig {
	
}
