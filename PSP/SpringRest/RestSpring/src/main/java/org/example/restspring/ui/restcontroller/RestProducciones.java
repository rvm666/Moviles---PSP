package org.example.restspring.ui.restcontroller;
import jakarta.servlet.http.HttpSession;
import org.example.restspring.domain.model.Produccion;
import org.example.restspring.domain.model.Usuario;
import org.example.restspring.ui.config.Constantes;
import org.example.restspring.ui.config.interceptor.RequiresAuth;
import org.example.restspring.ui.dto.ProduccionDTO;
import org.example.restspring.ui.service.AuthService;
import org.example.restspring.domain.service.ProduccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.REST_PRODUCCIONES)
public class RestProducciones {

    private final ProduccionService producciones;
    private final AuthService authService;

    public RestProducciones(ProduccionService producciones, AuthService authService) {
        this.producciones = producciones;
        this.authService = authService;
    }

    @GetMapping
    @RequiresAuth
    public ResponseEntity<List<ProduccionDTO>> listarProducciones(HttpSession session) {
        if (authService.isAdmin(session)) return ResponseEntity.ok(producciones.getAll());
        int userId = authService.getUsuarioIdFromSession(session);
        return ResponseEntity.ok(producciones.getByUserId(userId));
    }

    @GetMapping("/{id}")
    @RequiresAuth
    public ResponseEntity<ProduccionDTO> getProduccion(@PathVariable int id) {
        ProduccionDTO produccion = producciones.getById(id);
        return ResponseEntity.ok(produccion);
    }


    @GetMapping("/filtrar")
    @RequiresAuth
    public ResponseEntity<ProduccionDTO> filtroProduccion(@RequestParam String nombre) {
        ProduccionDTO produccion = producciones.getByName(nombre);
        return ResponseEntity.ok(produccion);
    }


    @PostMapping
    @RequiresAuth(admin = true)
    public ResponseEntity<ProduccionDTO> crearProduccion(@RequestBody Produccion produccion) {
        ProduccionDTO nuevaProduccion = producciones.save(produccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaProduccion);
    }

    @PutMapping("/{id}")
    @RequiresAuth(admin = true)
    public ResponseEntity<ProduccionDTO> actualizarProduccion(@PathVariable int id, @RequestBody ProduccionDTO produccion) {
        ProduccionDTO actualizada = producciones.update(id, produccion);
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/producciones_user")
    @RequiresAuth
    public ResponseEntity<List<ProduccionDTO>> getProduccionesUser(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute(Constantes.USUARIO);
        List<ProduccionDTO> produccion = producciones.getByUserId(usuario.id());
        return ResponseEntity.ok(produccion);

    }


    @DeleteMapping("/{id}")
    @RequiresAuth(admin = true)
    public ResponseEntity<Void> eliminarProduccion(@PathVariable int id, HttpSession session) {
            int userId = authService.getUsuarioIdFromSession(session);
            producciones.delete(id,userId);
            return ResponseEntity.noContent().build();
    }

}
