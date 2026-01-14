package org.example.restspring.ui.restcontroller;

import jakarta.servlet.http.HttpSession;
import org.example.restspring.domain.model.Usuario;
import org.example.restspring.domain.service.EmailService;
import org.example.restspring.domain.service.ToptService;
import org.example.restspring.ui.config.Constantes;
import org.example.restspring.ui.config.interceptor.RequiresAuth;
import org.example.restspring.ui.dto.Enable2FAResponse;
import org.example.restspring.ui.dto.UsuarioDTO;
import org.example.restspring.ui.service.AuthService;
import org.example.restspring.domain.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Constantes.REST_USUARIOS)
public class RestUsuarios {

    private final UsuarioService usuarios;
    private final AuthService authService;
    private final EmailService emailService;
    private final ToptService toptService;

    public RestUsuarios(UsuarioService usuarios, AuthService authService, EmailService emailService, ToptService toptService) {
        this.usuarios = usuarios;
        this.authService = authService;
        this.emailService = emailService;
        this.toptService = toptService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody UsuarioDTO usuario, HttpSession session) {
        UsuarioDTO usuarioDTO = authService.login(usuario, session);
        return ResponseEntity.ok(usuarioDTO);

    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody Usuario usuario){
        String codigoActivacion = UUID.randomUUID().toString();
        LocalDateTime fechaRegistro = LocalDateTime.now().plusHours(24);
        Usuario nuevo = new Usuario(usuario.id(), usuario.username(), usuario.password(), usuario.email(), codigoActivacion, usuario.activado(), usuario.nombre(), usuario.esAdmin(), fechaRegistro, usuario.twoFactorEnabled(), usuario.twoFactorCode());
        Usuario user = authService.registrar(nuevo);

        emailService.enviarMail(usuario.email(), usuario.nombre(), codigoActivacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
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


    @PostMapping("/2fa/enable")
    @RequiresAuth
    public ResponseEntity<Enable2FAResponse> enable2FA(HttpSession session) {



    }

    @PostMapping("/2fa/confirm")
    @RequiresAuth
    public ResponseEntity<?> confirm2FA(@RequestBody Confirm2FARequest request, HttpSession session) {
        // Verificar que el usuario esté autenticado
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "No autenticado"));
        }

        Long usuarioId = authService.getUsuarioIdFromSession(session);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Usuario no encontrado"));
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar que tiene un secreto pendiente
        if (usuario.twoFactorSecret() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "No hay un proceso de habilitación 2FA pendiente"));
        }

        // Verificar el código TOTP
        boolean isValid = totpService.verifyCode(usuario.twoFactorSecret(), request.code());

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Código inválido. Verifica que tu app esté sincronizada correctamente."));
        }

        // Activar 2FA
        usuario = usuario.set2FA(true,usuario.twoFactorSecret());
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Autenticación de dos factores activada correctamente"
        ));
    }

    @PostMapping("/2fa/disable")
    @RequiresAuth
    public ResponseEntity<?> disable2FA(HttpSession session) {
        // Verificar que el usuario esté autenticado
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "No autenticado"));
        }

        Long usuarioId = authService.getUsuarioIdFromSession(session);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Usuario no encontrado"));
        }

        Usuario usuario = usuarioOpt.get().set2FA(false,null);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Autenticación de dos factores desactivada"
        ));
    }

    @PostMapping("/2fa/verify")
    public ResponseEntity<?> verify2FA(@RequestBody Verify2FARequest request, HttpSession session) {
        // Verificar que hay un login pendiente de 2FA
        String pendingUsername = (String) session.getAttribute("pendingTwoFactorUsername");

        if (pendingUsername == null || !pendingUsername.equals(request.username())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "No hay un login pendiente de verificación 2FA"));
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.username());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Usuario no encontrado"));
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar que tiene 2FA habilitado
        if (!Boolean.TRUE.equals(usuario.twoFactorEnabled()) || usuario.twoFactorSecret() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "El usuario no tiene 2FA habilitado"));
        }

        // Verificar el código TOTP
        boolean isValid = totpService.verifyCode(usuario.twoFactorSecret(), request.code());

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Código de verificación inválido"));
        }

        // Código válido - completar el login
        session.removeAttribute("pendingTwoFactorUsername");
        session.setAttribute("usuarioId", usuario.id());
        session.setAttribute("username", usuario.username());
        session.setAttribute("rol", usuario.rol());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Login completado exitosamente",
                "usuario", usuario
        ));
    }


    @GetMapping("/2fa/status")
    public ResponseEntity<?> get2FAStatus(HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "No autenticado"));
        }

        Long usuarioId = authService.getUsuarioIdFromSession(session);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Usuario no encontrado"));
        }

        Usuario usuario = usuarioOpt.get();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "twoFactorEnabled", usuario.twoFactorEnabled()
        ));
    }

}
