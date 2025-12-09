package org.example.restspring.ui.config;

import org.example.restspring.ui.config.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Interceptor interceptor;

    @Value(Constantes.LOGIN_URL)
    private String pathLogin;

    @Value(Constantes.REGISTRAR_URL)
    private String pathRegistro;

    public WebConfig(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns(Constantes.PATH_PATTERNS)
                .excludePathPatterns(pathLogin, pathRegistro);
    }

}
