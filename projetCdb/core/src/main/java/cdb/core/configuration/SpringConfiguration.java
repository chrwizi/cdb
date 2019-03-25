package cdb.core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="{cdb.core.configuration,cdb.core.models}")
public class SpringConfiguration {

}
