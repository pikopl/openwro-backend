package com.xaltome.smartcity.weather.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@EnableScheduling
@ComponentScan({ 	"com.xaltome.smartcity.weather.rest.controller", 
					"com.xaltome.smartcity.weather.dbservice", 
					"com.xaltome.smartcity.weather.config",
					"com.xaltome.smartcity.weather.scheduler"})
public class SpringRootConfig {

}
