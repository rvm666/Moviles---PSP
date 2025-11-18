package org.example.restspring.ui.restController;

import jakarta.servlet.http.HttpSession;
import org.example.restspring.domain.model.Produccion;
import org.example.restspring.domain.model.Usuario;
import org.example.restspring.ui.config.Constantes;
import org.example.restspring.ui.dto.ProduccionDTO;
import org.example.restspring.ui.service.AuthService;
import org.example.restspring.ui.service.ProduccionService;
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
    public ResponseEntity<List<ProduccionDTO>> listarProducciones(HttpSession session) {
        if (!authService.isAuthenticated(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        if (authService.isAdmin(session)) {
            return ResponseEntity.ok(producciones.getAll());
        }else {
         int userId = authService.getUsuarioIdFromSession(session);
            List<ProduccionDTO> produccionesUser = producciones.getByUserId(userId);
            return ResponseEntity.ok(produccionesUser);
        }



    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduccionDTO> getProduccion(@PathVariable int id) {
        ProduccionDTO produccion = producciones.getById(id);
        if (produccion != null) {
            return ResponseEntity.ok(produccion);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/filtrar")
    public ResponseEntity<ProduccionDTO> filtroProduccion(@RequestParam String nombre) {
        ProduccionDTO produccion = producciones.getByName(nombre);
        if(produccion != null){
            return ResponseEntity.ok(produccion);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping
    public ResponseEntity<ProduccionDTO> crearProduccion(@RequestBody Produccion produccion,HttpSession session) {
        if (authService.isAuthenticated(session)) {
            if (authService.isAdmin(session)) {
                ProduccionDTO nuevaProduccion = producciones.save(produccion);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevaProduccion);
            }
            else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduccionDTO> actualizarProduccion(@PathVariable int id, @RequestBody ProduccionDTO produccion) {
        ProduccionDTO actualizada = producciones.update(id, produccion);
        if (actualizada != null) {
            return ResponseEntity.ok(produccion);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/producciones_user")
    public ResponseEntity<List<ProduccionDTO>> getProduccionesUser(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute(Constantes.USUARIO);
        if(usuario != null){
            List<ProduccionDTO> produccion = producciones.getByUserId(usuario.id());
            if (produccion.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(produccion);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProduccion(@PathVariable int id, HttpSession session) {
        if (authService.isAuthenticated(session)) {
            int userId = authService.getUsuarioIdFromSession(session);
            if (producciones.delete(id,userId)) {
                return ResponseEntity.noContent().build();

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
