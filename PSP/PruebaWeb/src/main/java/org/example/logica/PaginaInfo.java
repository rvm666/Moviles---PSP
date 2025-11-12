package org.example.logica;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.utilities.Usuario;
import org.example.config.Constantes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Map;

@WebServlet("/info")
public class PaginaInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        info(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        info(request,response);
    }

    private void info(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context =  new WebContext(webExchange);

        context.setVariable(Constantes.TEXTO_TITULO, Constantes.TEXTO_TITULO_TEXT);

        Map<String, Usuario> usuarios = (Map<String, Usuario>) getServletContext().getAttribute(Constantes.USUARIOS);

        context.setVariable(Constantes.USUARIOS, usuarios);
        context.setVariable(Constantes.NO_HAY_USUARIOS, Constantes.NO_HAY_USUARIOS_TEXT);

        response.setContentType(Constantes.CONTENT_TYPE);
        ((TemplateEngine)getServletContext().getAttribute(Constantes.TEMPLATE_ENGINE))
                .process(Constantes.PAGINFO, context, response.getWriter());
    }
}
