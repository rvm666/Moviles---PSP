package org.example.restspring.ui.restcontroller;

import org.example.restspring.ui.config.Constantes;
import org.example.restspring.ui.dto.UsuarioDTO;
import org.example.restspring.ui.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(Constantes.REST_ACTIVACION)
public class ControllerActivar {

    private final AuthService authService;

    public ControllerActivar(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping
    public String activarCuenta(@RequestParam String codigo,
                                Model model){
        UsuarioDTO usuario = authService.activarCuenta(codigo);
        model.addAttribute("activada", true);
        model.addAttribute(Constantes.USUARIO, usuario.username());
        return "activacion";
    }
}
