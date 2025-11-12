package org.example.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebListener
public class ThymeleafConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        JakartaServletWebApplication application  = JakartaServletWebApplication.buildApplication(servletContext);

        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
        templateResolver.setPrefix(Constantes.PREFIJO);
        templateResolver.setSuffix(Constantes.SUFIJO);
        templateResolver.setTemplateMode(Constantes.TEMPLATE_MODE);
        templateResolver.setCharacterEncoding(Constantes.ENCODING);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        servletContext.setAttribute(Constantes.TEMPLATE_ENGINE, templateEngine);
    }
}
