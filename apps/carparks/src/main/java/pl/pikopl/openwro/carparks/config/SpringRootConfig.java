package pl.pikopl.openwro.carparks.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@EnableScheduling
@ComponentScan({ 	"pl.pikopl.openwro.carparks.rest.controller", 
					"pl.pikopl.openwro.carparks.dbservice", 
					"pl.pikopl.openwro.carparks.config",
					"pl.pikopl.openwro.carparks.scheduler",
					"pl.pikopl.openwro.carparks.util"})
public class SpringRootConfig {
	
}
