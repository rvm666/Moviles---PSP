package org.example.restspring.ui.service;

import jakarta.servlet.http.HttpSession;
import org.example.restspring.data.UsuarioRepository;
import org.example.restspring.domain.errores.BadRequestException;
import org.example.restspring.domain.errores.UnauthorizedException;
import org.example.restspring.domain.model.Usuario;
import org.example.restspring.ui.config.Constantes;
import org.example.restspring.ui.dto.UsuarioDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public Usuario registrar(Usuario usuario){
        Usuario user = usuarioRepository.getByName(usuario.username());
        if(user != null) throw new BadRequestException("El usuario " + usuario.username() + " ya esta registrado");


        usuarioRepository.saveWithPlainPassword(usuario.username(), usuario.password(), usuario.email(), usuario.nombre(), usuario.esAdmin(), usuario.codigo(), usuario.activado());
        return new Usuario(usuario.id(), usuario.username(), null, usuario.email(), usuario.codigo(), usuario.activado(), usuario.nombre(), null);
    }

    public UsuarioDTO activarCuenta(String codigo){
        Usuario usuario = usuarioRepository.getByCodigo(codigo);
        if(usuario == null) throw new BadRequestException(Constantes.CODIGO_INVALIDO);

        Usuario user = new Usuario(
                usuario.id(),
                usuario.username(),
                usuario.password(),
                usuario.email(),
                codigo,
                true,
                usuario.nombre(),
                usuario.esAdmin()
        );

        Usuario usuarioActivado = usuarioRepository.update(usuario.id(), user);
        return new UsuarioDTO(usuarioActivado.username(), null);
    }

    public UsuarioDTO login(UsuarioDTO usuarioDTO, HttpSession session){
        Usuario usuario = usuarioRepository.getByName(usuarioDTO.username());

        if(passwordEncoder.matches(usuarioDTO.password(), usuario.password())){
            /*
            Usuario muestra linea amarilla de warning por que al ser guardado en la session
            tiene que ser Serializable. He probado a implementar Serializable en el Record
            y me ha dao muchos errores, por eso lo dejo asi.
             */
            session.setAttribute(Constantes.USUARIO, usuario);
            return new UsuarioDTO(usuario.username(), null);
        }

        throw new UnauthorizedException(Constantes.CREDENCIALES_INVALIDAS);
    }

    public boolean isAdmin(HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute(Constantes.USUARIO);
        return usuario.esAdmin();
    }

    public boolean isAuthenticated(HttpSession session) {
        return session.getAttribute(Constantes.USUARIO) != null;
    }

    public int getUsuarioIdFromSession(HttpSession session) {
        return ((Usuario) session.getAttribute(Constantes.USUARIO)).id();
    }
}
