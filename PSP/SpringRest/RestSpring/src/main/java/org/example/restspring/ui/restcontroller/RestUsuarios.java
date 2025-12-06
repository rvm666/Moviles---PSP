package org.example.restspring.ui.restcontroller;
/*Aqui me da un warning pero es por el nombre del paquete,
 lo cambie poniendo la primera en mayuscula pero seguia igual*/
import jakarta.servlet.http.HttpSession;
import org.example.restspring.domain.model.Usuario;
import org.example.restspring.domain.service.EmailService;
import org.example.restspring.ui.config.Constantes;
import org.example.restspring.ui.config.interceptor.RequiresAuth;
import org.example.restspring.ui.dto.UsuarioDTO;
import org.example.restspring.ui.service.AuthService;
import org.example.restspring.domain.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Constantes.REST_USUARIOS)
public class RestUsuarios {

    private final UsuarioService usuarios;
    private final AuthService authService;
    private final EmailService emailService;

    public RestUsuarios(UsuarioService usuarios, AuthService authService, EmailService emailService) {
        this.usuarios = usuarios;
        this.authService = authService;
        this.emailService = emailService;
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

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody Usuario usuario){
        String codigoActivacion = UUID.randomUUID().toString();
        Usuario nuevo = new Usuario(usuario.id(), usuario.username(), usuario.password(), usuario.email(), codigoActivacion, usuario.activado(), usuario.nombre(), usuario.esAdmin());
        Usuario user = authService.registrar(nuevo);

        emailService.enviarMail(usuario.email(), usuario.nombre(), codigoActivacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/activar")
    public ResponseEntity<UsuarioDTO> activarCuenta(@RequestParam String codigo){
        UsuarioDTO usuario = authService.activarCuenta(codigo);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @RequiresAuth(admin = true)
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarios.getAll());
    }

    @GetMapping("/{id}")
    @RequiresAuth(admin = true)
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable int id) {
        UsuarioDTO usuarioDTO = usuarios.getById(id);
        return ResponseEntity.ok(usuarioDTO);
    }


    @GetMapping("/filtrar")
    @RequiresAuth(admin = true)
    public ResponseEntity<UsuarioDTO> filtroUsuario(@RequestParam String nombre) {
        UsuarioDTO usuario = usuarios.getByName(nombre);
        return ResponseEntity.ok(usuario);

    }

}
