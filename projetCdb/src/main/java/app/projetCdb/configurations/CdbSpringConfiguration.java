package app.projetCdb.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages = { "app.projetCdb.tmpServices", "app.projetCdb.controllers", "app.projetCdb.persistance",
		"app.projetCdb.services", "app.projetCdb.configurations" })
@Import(value = { DataSourceConfig.class })
public class CdbSpringConfiguration implements WebMvcConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(CdbSpringConfiguration.class);

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		WebMvcConfigurer.super.configureDefaultServletHandling(configurer);
		configurer.enable();
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

}
