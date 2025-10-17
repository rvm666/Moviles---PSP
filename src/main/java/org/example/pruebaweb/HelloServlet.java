package org.example.pruebaweb;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hola")
public class HelloServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var contador = 1;
        if (request.getSession().getAttribute("contador") != null) {
            contador = (Integer) request.getSession().getAttribute("contador");
        }
        response.getWriter().write("HOLA MUNDO " + contador);
        request.getSession().setAttribute("contador", contador+1);

    }

}