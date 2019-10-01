package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;
import py.com.fuentepy.appfinanzasBackend.service.UsuarioService;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UsuarioModel findById(Long id) {
        UsuarioModel usuarioModel = null;
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            usuarioModel = new UsuarioModel();
            usuarioModel.setId(usuario.getId());
            usuarioModel.setLastName(usuario.getLastName());
            usuarioModel.setName(usuario.getName());
            usuarioModel.setEmail(usuario.getEmail());
        }
        return usuarioModel;
    }

    @Override
    public UsuarioModel uploadImage(String imageBase64, String imageName, Long id) {
        UsuarioModel usuarioModel = null;
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            usuario.setImageProfileBase64(imageBase64);
            usuario.setImageProfileName(imageName);
            usuario = usuarioRepository.save(usuario);
            usuarioModel = new UsuarioModel();
            usuarioModel.setId(usuario.getId());
            usuarioModel.setLastName(usuario.getLastName());
            usuarioModel.setName(usuario.getName());
            usuarioModel.setEmail(usuario.getEmail());
            usuarioModel.setImageProfileBase64(usuario.getImageProfileBase64());
            usuarioModel.setImageProfileName(usuario.getImageProfileName());
        }
        return usuarioModel;
    }
}
