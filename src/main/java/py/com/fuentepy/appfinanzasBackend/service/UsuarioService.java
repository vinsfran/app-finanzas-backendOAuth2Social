package py.com.fuentepy.appfinanzasBackend.service;

import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;

public interface UsuarioService {

    UsuarioModel findById(Long id);
}
