package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Dispositivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.DispositivoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.resource.moneda.MonedaModel;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.MonedaService;
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
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ArchivoServiceImpl archivoService;

    @Autowired
    private MonedaServiceImpl monedaService;


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
            usuarioModel.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioModel.setSexo(usuario.getSexo());
            usuarioModel.setImageProfileName(archivo.getNombre());
            usuarioModel.setMonedaModel(monedaService.findById(usuario.getMonedaId()));
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
            usuario.setFechaNacimiento(request.getFechaNacimiento());
            usuario.setSexo(request.getSexo());
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
    @Transactional
    public void registerToken(Long id, String token) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            try {
                Dispositivo dispositivo = dispositivoRepository.findByToken(token);
                if (dispositivo == null) {
                    dispositivo = new Dispositivo();
                    dispositivo.setUsuarioId(usuario);
                    dispositivo.setToken(token);
                    dispositivoRepository.save(dispositivo);
                }
            } catch (Exception e) {
                throw new Exception("No se pudo registrar el token del dispositivo! " + e.getMessage());
            }
        } else {
            throw new Exception("No exite el usuario!");
        }
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

}
