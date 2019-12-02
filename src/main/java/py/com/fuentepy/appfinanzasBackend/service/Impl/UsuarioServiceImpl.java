package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.UsuarioService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;
import py.com.fuentepy.appfinanzasBackend.util.StringUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

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
    public UsuarioModel uploadImage(MultipartFile multipartFile, Long id) throws Exception {
        UsuarioModel usuarioModel = null;
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            Archivo archivo = new Archivo();
            archivo.setTablaId(id);
            archivo.setTablaNombre(ConstantUtil.USUARIOS);
            archivo.setContentType(multipartFile.getContentType());
            archivo.setNombre(StringUtil.encodeBase64(id + "_" + ConstantUtil.USUARIOS) + "_" + multipartFile.getOriginalFilename().replace(" ", "_").toLowerCase());
            archivo.setUsuarioId(usuario);
            try {
                archivoService.save(archivo, multipartFile);
                usuarioModel = new UsuarioModel();
                usuarioModel.setId(usuario.getId());
                usuarioModel.setFirstName(usuario.getFirstName());
                usuarioModel.setLastName(usuario.getLastName());
                usuarioModel.setEmail(usuario.getEmail());
                usuarioModel.setImageProfileName(archivo.getNombre());
            } catch (Exception e) {
                throw new Exception("No se pudo subir la imagen! " + e.getCause().getMessage());
            }
        }
        return usuarioModel;
    }

    @Override
    @Transactional(readOnly = true)
    public Resource getImagenPerfil(Long usuarioId, String nombreArchivo) throws Exception {
        Resource resource = null;
        Optional<Usuario> optional = usuarioRepository.findById(usuarioId);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            resource = archivoService.getArchivo(usuarioId, usuarioId, ConstantUtil.USUARIOS, nombreArchivo);
        } else {
            throw new Exception("No existe usuario.");
        }
        return resource;
    }
}
