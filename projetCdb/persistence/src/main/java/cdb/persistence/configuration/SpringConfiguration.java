package cdb.persistence.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "{cdb.persistence.dao, scdb.persistence.configuration}")
public class SpringConfiguration {

}
