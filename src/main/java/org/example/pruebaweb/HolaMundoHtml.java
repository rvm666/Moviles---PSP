package org.example.pruebaweb;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("/html")
public class HolaMundoHtml extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var contador = 1;
        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context =  new WebContext(webExchange);

        context.setVariable("mensaje", "Hola Mundo HTML " + contador+1);
        resp.setContentType("text/html;charset=UTF-8");
        ((TemplateEngine)getServletContext().getAttribute("com.daw.TemplateEngine"))
                .process("prueba", context, resp.getWriter());
    }
}
