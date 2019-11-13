package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.UsuarioService;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Log LOG = LogFactory.getLog(UsuarioServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ArchivoServiceImpl archivoService;


    @Override
    @Transactional(readOnly = true)
    public UsuarioModel findById(Long id) {
        UsuarioModel usuarioModel = null;
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Archivo archivo = archivoService.findFotoPerfil(id);
            if (archivo == null) {
                archivo = new Archivo();
            }
            Usuario usuario = optional.get();
            usuarioModel = new UsuarioModel();
            usuarioModel.setId(usuario.getId());
            usuarioModel.setFirstName(usuario.getFirstName());
            usuarioModel.setLastName(usuario.getLastName());
            usuarioModel.setEmail(usuario.getEmail());
            usuarioModel.setImageProfileBase64(Base64.encodeBase64String(archivo.getDato()));
            usuarioModel.setImageProfileName(archivo.getNombre());
        }
        return usuarioModel;
    }

    @Override
    @Transactional
    public boolean update(UsuarioRequestUpdate request, Long usuarioId) {
        Optional<Usuario> optional = usuarioRepository.findById(usuarioId);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            usuario.setFirstName(request.getFirstName());
            usuario.setLastName(request.getLastName());
            usuario = usuarioRepository.save(usuario);
            if (usuario != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean changePassword(Long id, String passwordOld, String passwordNew) throws Exception {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                usuario.getEmail(),
                                passwordOld
                        )
                );
                usuario.setPassword(passwordEncoder.encode(passwordNew));
                usuarioRepository.save(usuario);
            } catch (Exception e) {
                throw new Exception("Contraseña actual no valida! " + e.getMessage());
            }
        } else {
            throw new Exception("No exite el usuario!");
        }
        return true;
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
    public UsuarioModel uploadImage(byte[] imageBase64, String imageName, String contentType, Long id) {
        UsuarioModel usuarioModel = null;
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            Archivo archivo = archivoService.findFotoPerfil(id);
            if (archivo == null) {
                archivo = new Archivo();
            }
            archivo.setTablaId(id);
            archivo.setTablaNombre("usuarios");
            archivo.setContentType(contentType);
            archivo.setNombre(imageName);
            archivo.setDato(imageBase64);
            archivo.setUsuarioId(usuario);
            try {
                archivoService.save(archivo);
                usuarioModel = new UsuarioModel();
                usuarioModel.setId(usuario.getId());
                usuarioModel.setFirstName(usuario.getFirstName());
                usuarioModel.setLastName(usuario.getLastName());
                usuarioModel.setEmail(usuario.getEmail());
                usuarioModel.setImageProfileBase64(Base64.encodeBase64String(archivo.getDato()));
                usuarioModel.setImageProfileName(archivo.getNombre());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usuarioModel;
    }
}
