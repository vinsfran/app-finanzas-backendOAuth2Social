package py.com.fuentepy.appfinanzasBackend.service;

import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

public interface UsuarioService {

    Usuario findById(Long id);
}
