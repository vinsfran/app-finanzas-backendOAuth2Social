package py.com.fuentepy.appfinanzasBackend.resource;

import py.com.fuentepy.appfinanzasBackend.exception.ResourceNotFoundException;
import py.com.fuentepy.appfinanzasBackend.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.security.CurrentUser;
import py.com.fuentepy.appfinanzasBackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public Usuario getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return usuarioRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", userPrincipal.getId()));
    }
}
