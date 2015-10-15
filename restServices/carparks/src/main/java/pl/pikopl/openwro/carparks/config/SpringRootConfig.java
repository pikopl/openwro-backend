package pl.pikopl.openwro.carparks.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan({ 	"pl.pikopl.openwro.carparks.controller", 
					"pl.pikopl.openwro.dbservice", 
					"pl.pikopl.openwro.carparks.config" })
public class SpringRootConfig {
	
}
