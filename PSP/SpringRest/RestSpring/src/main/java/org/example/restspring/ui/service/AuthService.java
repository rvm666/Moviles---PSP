package org.example.restspring.ui.service;

import jakarta.servlet.http.HttpSession;
import org.example.restspring.data.UsuarioRepository;
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

    public UsuarioDTO login(UsuarioDTO usuarioDTO, HttpSession session){
        Usuario usuario = usuarioRepository.getByName(usuarioDTO.username());

        if(passwordEncoder.matches(usuarioDTO.password(), usuario.password())){
            session.setAttribute(Constantes.USUARIO, usuario);
            return new UsuarioDTO(usuario.username(), null);
        }

        return null;
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
