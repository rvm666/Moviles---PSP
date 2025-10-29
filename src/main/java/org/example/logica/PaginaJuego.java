package org.example.logica;

import java.io.*;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.utilities.Usuario;
import org.example.config.Constantes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebServlet("/juego")
public class PaginaJuego extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        nose(request,response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        nose(request,response);
    }

    private void nose(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Random random = new Random();
        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context =  new WebContext(webExchange);



        String nombreSesion = request.getParameter(Constantes.NOMBRE);
        if(nombreSesion==null){
            nombreSesion = (String) request.getSession().getAttribute(Constantes.NOMBRE_USUARIO);
            if(nombreSesion==null){
                response.sendRedirect(Constantes.LOGIN_HTML);
                return;
            }
        }

        Map<String, Usuario> usuarios = (Map<String, Usuario>) getServletContext().getAttribute(Constantes.USUARIOS);
        if(usuarios == null){
            usuarios = new HashMap<>();
            getServletContext().setAttribute(Constantes.USUARIOS, usuarios);
        }

        Usuario usuario = usuarios.get(nombreSesion);
        if (usuario == null) {
            usuario = new Usuario(nombreSesion);
            usuarios.put(nombreSesion, usuario);
        }

        request.getSession().setAttribute(Constantes.NOMBRE_USUARIO, nombreSesion);

        if(request.getSession().getAttribute(Constantes.INTENTOS) == null){
            request.getSession().setAttribute(Constantes.INTENTOS, 0);
        }

        int intentoss = (int) request.getSession().getAttribute(Constantes.INTENTOS);

        Integer numeroAleatorio = (Integer) request.getSession().getAttribute(Constantes.NUMERO_ALEATORIO);
        if (numeroAleatorio == null){
            numeroAleatorio = random.nextInt(10);
            request.getSession().setAttribute(Constantes.NUMERO_ALEATORIO, numeroAleatorio);
        }
        String numeroParam = request.getParameter(Constantes.NUMERO);
        String mensaje;
        String accion = request.getParameter(Constantes.ACCION);
        boolean juegoTerminado = false;

        if(Constantes.REINICIAR.equals(accion)){
            request.getSession().setAttribute(Constantes.INTENTOS, 0);
            request.getSession().setAttribute(Constantes.NUMERO_ALEATORIO, random.nextInt(10));
            response.sendRedirect(Constantes.REDIRECCION + nombreSesion);
            return;
        }

        if (numeroParam != null) {
            int numeroIntroducido = Integer.parseInt(numeroParam);
            if (numeroAleatorio == numeroIntroducido) {
                mensaje = Constantes.GANADO;
                usuario.aumentarGanados();
                juegoTerminado = true;
            } else if (intentoss > 9) {
                mensaje = Constantes.PERDIO + numeroAleatorio;
                usuario.aumentarPerdidos();
                juegoTerminado = true;
            } else {
                intentoss++;
                request.getSession().setAttribute(Constantes.INTENTOS, intentoss);
                mensaje = Constantes.NUMERO_INCORRECTO;
            }
        } else {
            mensaje = Constantes.NO_SE_HA_INTRODUCIDO;
        }




        context.setVariable(Constantes.INTRODUCE_NUMERO, Constantes.INTRODUCE_NUMERO_TEXT);
        context.setVariable(Constantes.BOTON_JUGAR, Constantes.BOTON_JUGAR_TEXT);
        context.setVariable(Constantes.MENSAJE, mensaje);
        context.setVariable(Constantes.INTENTOS, intentoss);
        context.setVariable(Constantes.TEXTO_H1, Constantes.TEXTO_H1_TEXT + nombreSesion);
        context.setVariable(Constantes.JUEGO_TERMINADO, Constantes.JUEGO_TERMINADO_TEXT);
        context.setVariable(Constantes.VOLVER_LOGIN, Constantes.VOLVER_LOGIN_TEXT);
        context.setVariable(Constantes.VOLVER_JUGAR, Constantes.VOLVER_JUGAR_TEXT);
        context.setVariable(Constantes.JUEGOTERMINADO, juegoTerminado);
        context.setVariable(Constantes.NOMBRE_USUARIO, nombreSesion);



        response.setContentType(Constantes.CONTENT_TYPE);
        ((TemplateEngine)getServletContext().getAttribute(Constantes.TEMPLATE_ENGINE))
                .process(Constantes.PAGJUEGO, context, response.getWriter());
    }

}