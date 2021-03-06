package py.com.fuentepy.appfinanzasBackend.service;

import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioRequestUpdate;

import java.util.Optional;

public interface UsuarioService {

    UsuarioModel findById(Long id);

    boolean update(UsuarioRequestUpdate request, Long usuarioId);

    boolean changePassword(Long id, String passwordOld, String passwordNew) throws Exception;

    void registerToken(Long id, String token) throws Exception;

    Optional<Usuario> findUserByEmail(String email);

    Optional<Usuario> findUserByResetToken(String resetToken);

    void save(Usuario user);

}
