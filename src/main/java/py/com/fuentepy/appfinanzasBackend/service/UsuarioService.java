package py.com.fuentepy.appfinanzasBackend.service;

import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;

public interface UsuarioService {

    UsuarioModel findById(Long id);

    UsuarioModel uploadImage(byte[] imageBase64, String imageName, Long id);
}
