package org.example.restspring.ui.restController;

import jakarta.servlet.http.HttpSession;
import org.example.restspring.ui.config.Constantes;
import org.example.restspring.ui.dto.UsuarioDTO;
import org.example.restspring.ui.service.AuthService;
import org.example.restspring.ui.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.REST_USUARIOS)
public class RestUsuarios {

    private final UsuarioService usuarios;
    private final AuthService authService;

    public RestUsuarios(UsuarioService usuarios, AuthService authService) {
        this.usuarios = usuarios;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody UsuarioDTO usuario, HttpSession session) {
        UsuarioDTO usuarioDTO = authService.login(usuario, session);
        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(HttpSession session) {
        if (authService.isAuthenticated(session)) {
            if(authService.isAdmin(session)) {
                return ResponseEntity.ok(usuarios.getAll());
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable int id) {
        UsuarioDTO usuarioDTO = usuarios.getById(id);
        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/filtrar")
    public ResponseEntity<UsuarioDTO> filtroUsuario(@RequestParam String nombre) {
        UsuarioDTO produccion = usuarios.getByName(nombre);
        if (produccion != null) {
            return ResponseEntity.ok(produccion);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
