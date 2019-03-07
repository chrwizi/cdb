package app.projetCdb.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages="app.projetCdb.tmpServices")
public class CdbSpringConfiguration {
	private static final Logger logger=LoggerFactory.getLogger(CdbSpringConfiguration.class);
	
}
