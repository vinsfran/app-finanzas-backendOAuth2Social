package py.com.fuentepy.appfinanzasBackend.service;

import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;

import java.util.Optional;

public interface UsuarioService {

    UsuarioModel findById(Long id);

    boolean changePassword(Long id, String passwordOld, String passwordNew) throws Exception;

    Optional<Usuario> findUserByEmail(String email);

    Optional<Usuario> findUserByResetToken(String resetToken);

    public void save(Usuario user);

    UsuarioModel uploadImage(byte[] imageBase64, String imageName, Long id);
}
