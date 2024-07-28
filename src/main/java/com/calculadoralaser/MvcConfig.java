package com.calculadoralaser;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

//	private final Logger log = LoggerFactory.getLogger(getClass());
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		// TODO Auto-generated method stub
//		WebMvcConfigurer.super.addResourceHandlers(registry);
//		
//		String resourcePath=Paths.get("uploads").toAbsolutePath().toUri().toString();
//		log.info(resourcePath);
//		registry.addResourceHandler("/uploads/**")
//		.addResourceLocations(resourcePath);
//		
//		String resourcePathClientes=Paths.get("uploads/clientes").toAbsolutePath().toUri().toString();
//		log.info(resourcePath);
//		registry.addResourceHandler("/uploads/clientes/**")
//		.addResourceLocations(resourcePathClientes);
//		
//		String resourcePathVectores=Paths.get("uploads/vectores").toAbsolutePath().toUri().toString();
//		log.info(resourcePath);
//		registry.addResourceHandler("/uploads/vectores/**")
//		.addResourceLocations(resourcePathVectores);
//		
//		String resourcePathProductos=Paths.get("uploads/productos").toAbsolutePath().toUri().toString();
//		log.info(resourcePath);
//		registry.addResourceHandler("/uploads/productos/**")
//		.addResourceLocations(resourcePathProductos);
//		
//		String resourcePathFotos=Paths.get("uploads/fotos").toAbsolutePath().toUri().toString();
//		log.info(resourcePath);
//		registry.addResourceHandler("/uploads/fotos/**")
//		.addResourceLocations(resourcePathProductos);
//	}
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}

	
	
}
