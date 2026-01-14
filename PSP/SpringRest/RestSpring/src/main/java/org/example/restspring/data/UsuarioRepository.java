package org.example.restspring.data;

import org.example.restspring.domain.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UsuarioRepository {

    private final List<Usuario> usuarios;
    private final PasswordEncoder encoder;

    public UsuarioRepository(PasswordEncoder encoder) {
        this.encoder = encoder;
        this.usuarios = new java.util.ArrayList<>();
        saveWithPlainPassword(new Usuario(1, "admin", "admin123", "admin@gmail.com", "Juan", true, "", true, LocalDateTime.now(), false, null));
        saveWithPlainPassword(new Usuario(2, "user", "user123", "user@gmail.com", "Carlos", false, "", true, LocalDateTime.now().minusDays(10), false, null));
        saveWithPlainPassword(new Usuario(3, "user2", "user123456", "user2@gmail.com", "Maria", false, "", true, LocalDateTime.now().minusDays(5), false, null));

    }

    public void saveWithPlainPassword(Usuario usuarioo) {
        String hashedPassword = encoder.encode(usuarioo.password());
        Usuario usuario = new Usuario(0, usuarioo.username(), hashedPassword, usuarioo.email(), usuarioo.codigo(), usuarioo.activado(), usuarioo.nombre(), usuarioo.esAdmin(), usuarioo.fecha(), usuarioo.twoFactorEnabled(), usuarioo.twoFactorCode());
        save(usuario);
    }

    private void save(Usuario usuario) {
        int id = nextId();
        Usuario usuarioGuardado = new Usuario(id, usuario.username(), usuario.password(), usuario.email(), usuario.codigo(), usuario.activado(), usuario.nombre(), usuario.esAdmin(), usuario.fecha(), usuario.twoFactorEnabled(), usuario.twoFactorCode());
        usuarios.add(usuarioGuardado);
    }


    public List<Usuario> getAll() {
        return usuarios;
    }


    public Usuario getById(int id) {
        return usuarios.stream().filter(produccion -> produccion.id() == id).findFirst().orElse(null);
    }

    public Usuario getByCodigo(String codigo){
        return usuarios.stream().filter(usuario -> usuario.codigo().equals(codigo)).findFirst().orElse(null);
    }

    public Usuario update(int id, Usuario usuario) {
        Usuario antigua = getById(id);
        if(antigua == null){
            return null;
        }
        int index = usuarios.indexOf(antigua);
        Usuario actualizada = new Usuario(id, usuario.username(), usuario.password(), usuario.email(), usuario.codigo(), usuario.activado(), usuario.nombre(), usuario.esAdmin(), usuario.fecha(), usuario.twoFactorEnabled(), usuario.twoFactorCode());
        usuarios.set(index, actualizada);
        return actualizada;
    }

    public Usuario getByName(String name) {
        return usuarios.stream().filter(usuario -> usuario.username().equals(name)).findFirst().orElse(null);
    }

    private int nextId(){
        return usuarios.size()+1;
    }
}
