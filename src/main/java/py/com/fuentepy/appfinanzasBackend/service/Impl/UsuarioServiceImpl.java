package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.tomcat.util.codec.binary.Base64;
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
            usuarioModel.setName(usuario.getFirstName());
            usuarioModel.setLastName(usuario.getLastName());
            usuarioModel.setEmail(usuario.getEmail());
            usuarioModel.setImageProfileBase64(Base64.encodeBase64String(usuario.getImageProfileData()));
            usuarioModel.setImageProfileName(usuario.getImageProfileName());
        }
        return usuarioModel;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional findUserByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional findUserByResetToken(String resetToken) {
        return usuarioRepository.findByResetToken(resetToken);
    }

    @Override
    @Transactional
    public void save(Usuario user) {
        usuarioRepository.save(user);
    }

    @Override
    @Transactional
    public UsuarioModel uploadImage(byte[] imageBase64, String imageName, Long id) {
        UsuarioModel usuarioModel = null;
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            usuario.setImageProfileData(imageBase64);
            usuario.setImageProfileName(imageName);
            usuario = usuarioRepository.save(usuario);
            usuarioModel = new UsuarioModel();
            usuarioModel.setId(usuario.getId());
            usuarioModel.setName(usuario.getFirstName());
            usuarioModel.setLastName(usuario.getLastName());
            usuarioModel.setEmail(usuario.getEmail());
            usuarioModel.setImageProfileBase64(Base64.encodeBase64String(usuario.getImageProfileData()));
            usuarioModel.setImageProfileName(usuario.getImageProfileName());
        }
        return usuarioModel;
    }
}
