package org.springframework.samples.volleymate.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.samples.volleymate.cookies.CookiesInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CookiesInterceptor())
                .excludePathPatterns("/cookies") 	// Excluir la ruta de la página de aviso de cookies
                .addPathPatterns("/**"); 			// Aplicar el interceptor a todas las demás rutas
    }

}
