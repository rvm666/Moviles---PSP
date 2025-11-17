package org.example.restspring.data;

import org.example.restspring.domain.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRepository {

    private final List<Usuario> usuarios;
    private final PasswordEncoder encoder;

    public UsuarioRepository(PasswordEncoder encoder) {
        this.encoder = encoder;
        this.usuarios = new java.util.ArrayList<>();
        saveWithPlainPassword("admin", "admin123", "admin@gmail.com", "Juan", true);
        saveWithPlainPassword("user", "user123", "user@gmail.com", "Carlos", false);
        saveWithPlainPassword("user2", "user123456", "user2@gmail.com", "Maria", false);

    }

    private void saveWithPlainPassword(String username, String plainPassword, String email, String nombre, boolean esAdmin) {
        String hashedPassword = encoder.encode(plainPassword);
        Usuario usuario = new Usuario(0, username, hashedPassword, email, nombre, esAdmin);
        save(usuario);
    }

    public Usuario save(Usuario usuario) {
        int id = nextId();
        Usuario usuarioGuardado = new Usuario(id, usuario.username(), usuario.password(), usuario.email(), usuario.nombre(), usuario.esAdmin());
        usuarios.add(usuarioGuardado);
        return usuarioGuardado;
    }


    public List<Usuario> getAll() {
        return usuarios;
    }


    public Usuario getById(int id) {
        return usuarios.stream().filter(produccion -> produccion.id() == id).findFirst().orElse(null);
    }


    public Usuario getByName(String name) {
        return usuarios.stream().filter(usuario -> usuario.username().equals(name)).findFirst().orElse(null);
    }

    public int nextId(){
        return usuarios.size()+1;
    }
}
