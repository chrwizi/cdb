package cdb.persistence.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="{app.persistence.dao,app.persistence.configuration}")
public class SpringConfiguration {

}
 