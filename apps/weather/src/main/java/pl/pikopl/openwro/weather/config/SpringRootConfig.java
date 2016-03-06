package pl.pikopl.openwro.weather.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@EnableScheduling
@ComponentScan({ 	"pl.pikopl.openwro.weather.rest.controller", 
					"pl.pikopl.openwro.weather.dbservice", 
					"pl.pikopl.openwro.weather.config",
					"pl.pikopl.openwro.weather.scheduler"})
public class SpringRootConfig {

}
