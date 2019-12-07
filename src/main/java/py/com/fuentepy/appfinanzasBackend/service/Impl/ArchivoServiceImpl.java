package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.converter.ArchivoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.ArchivoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;
import py.com.fuentepy.appfinanzasBackend.resource.usuario.UsuarioModel;
import py.com.fuentepy.appfinanzasBackend.service.ArchivoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;
import py.com.fuentepy.appfinanzasBackend.util.StringUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    private static final Log LOG = LogFactory.getLog(ArchivoServiceImpl.class);

    @Autowired
    private ArchivoRepository archivoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ArchivoModel> getArchivos(Long usuarioId, Long tablaId, String tablaNombre) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return ArchivoConverter.listEntitytoListModel(archivoRepository.findByUsuarioIdAndTablaIdAndTablaNombre(usuario, tablaId, tablaNombre));
    }

    @Override
    @Transactional(readOnly = true)
    public Archivo findFotoPerfil(Long usuarioId) {
        Archivo archivo = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        List<Archivo> archivos = archivoRepository.findByUsuarioIdAndTablaIdAndTablaNombre(usuario, usuarioId, ConstantUtil.USUARIOS);
        if (archivos != null && !archivos.isEmpty()) {
            archivo = archivos.get(0);
        }
        return archivo;
    }

    @Override
    public String save(Long tablaId, String tablaNombre, Long usuarioId, MultipartFile multipartFile) throws Exception {
        String nombreArchivo = null;
        try {
            nombreArchivo = StringUtil.armarNombreArchivo(tablaId, tablaNombre, multipartFile.getContentType(), multipartFile.getOriginalFilename());
            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            Archivo archivo = new Archivo();
            archivo.setTablaId(tablaId);
            archivo.setTablaNombre(tablaNombre);
            archivo.setContentType(multipartFile.getContentType());
            archivo.setNombre(nombreArchivo);
            archivo.setUsuarioId(usuario);
            Path rutaArchivo = Paths.get(ConstantUtil.UPLOADS).resolve(archivo.getNombre()).toAbsolutePath();
            Files.copy(multipartFile.getInputStream(), rutaArchivo);
            archivoRepository.save(archivo);
        } catch (Exception e) {
            throw new Exception("No se pudo guardar el Archivo! " + e.getMessage());
        }
        return nombreArchivo;
    }

    @Override
    public void deleteFile(String nombreArchivo) throws Exception {
        try {
            Path rutaArchivoAnterior = Paths.get(ConstantUtil.UPLOADS).resolve(nombreArchivo).toAbsolutePath();
            File archivoBorrar = rutaArchivoAnterior.toFile();
            if (archivoBorrar.exists() && archivoBorrar.canRead()) {
                archivoBorrar.delete();
            } else {
                throw new Exception("No existe o no se puede leer el Archivo!");
            }
        } catch (Exception e) {
            throw new Exception("No se pudo eliminar el Archivo! " + e.getMessage());
        }
    }

    @Override
    public boolean uploadDocumentsList(Long tablaId, String tablaNombre, Long usuarioId, List<MultipartFile> multipartFileList) throws Exception {
        for (MultipartFile multipartFile : multipartFileList) {
            try {
                save(tablaId, tablaNombre, usuarioId, multipartFile);
            } catch (Exception e) {
                throw new Exception("No se pudieron guardar los Archivos! " + e.getMessage());
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void deleteFiles(Long tablaId, String tablaNombre, Long usuarioId) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        List<Archivo> archivos = archivoRepository.findByUsuarioIdAndTablaIdAndTablaNombre(usuario, tablaId, tablaNombre);
        try {
            for (Archivo archivo : archivos) {
                deleteFile(archivo.getNombre());
                archivoRepository.deleteById(archivo.getId());
            }
        } catch (Exception e) {
            throw new Exception("No se pudieron eliminar los Archivos! " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Resource getResourceByName(Long usuarioId, String nombreArchivo) throws Exception {
        Resource resource = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Archivo archivo = archivoRepository.findByUsuarioIdAndNombre(usuario, nombreArchivo);
        if (archivo != null) {
            Path rutaArchivo = Paths.get(ConstantUtil.UPLOADS).resolve(archivo.getNombre()).toAbsolutePath();
            try {
                resource = new UrlResource(rutaArchivo.toUri());
            } catch (MalformedURLException e) {
                throw new Exception("Malformed URL! " + e.getCause().getMessage());
            }
            if (!resource.exists() && !resource.isReadable()) {
                throw new Exception("No pudo cargar el Archivo: " + archivo.getNombre());
            }
        } else {
            throw new Exception("No existe el Archivo en la BD!");
        }
        return resource;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resource> getResources(Long usuarioId, Long tablaId, String tablaNombre) throws Exception {
        List<Resource> resources = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        List<Archivo> archivos = archivoRepository.findByUsuarioIdAndTablaIdAndTablaNombre(usuario, tablaId, tablaNombre);
        if (archivos != null && !archivos.isEmpty()) {
            for (Archivo archivo : archivos) {
                Resource resource = null;
                Path rutaArchivo = Paths.get(ConstantUtil.UPLOADS).resolve(archivo.getNombre()).toAbsolutePath();
                try {
                    resource = new UrlResource(rutaArchivo.toUri());
                } catch (MalformedURLException e) {
                    throw new Exception("Malformed URL! " + e.getCause().getMessage());
                }
                if (!resource.exists() && !resource.isReadable()) {
                    throw new Exception("No pudo cargar el Archivo: " + archivo.getNombre());
                }
                resources.add(resource);
            }
        } else {
            throw new Exception("No existe el Archivo en la BD!");
        }
        return resources;
    }

    @Override
    @Transactional
    public UsuarioModel uploadImagenPerfil(MultipartFile multipartFile, Long id) throws Exception {
        UsuarioModel usuarioModel = null;
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            try {
                Archivo archivo = findFotoPerfil(usuario.getId());
                if (archivo != null && archivo.getNombre().length() > 0) {
                    deleteFile(archivo.getNombre());
                }
                String nombreArchivo = save(id, ConstantUtil.USUARIOS, usuario.getId(), multipartFile);
                usuarioModel = new UsuarioModel();
                usuarioModel.setId(usuario.getId());
                usuarioModel.setFirstName(usuario.getFirstName());
                usuarioModel.setLastName(usuario.getLastName());
                usuarioModel.setEmail(usuario.getEmail());
                usuarioModel.setImageProfileName(nombreArchivo);
            } catch (Exception e) {
                throw new Exception("No se pudo subir la imagen! " + e.getCause().getMessage());
            }
        }
        return usuarioModel;
    }

    @Override
    @Transactional(readOnly = true)
    public Resource getImagenPerfil(Long usuarioId) throws Exception {
        Resource resource = null;
        Optional<Usuario> optional = usuarioRepository.findById(usuarioId);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            resource = getResources(usuario.getId(), usuarioId, ConstantUtil.USUARIOS).get(0);
        } else {
            throw new Exception("No existe usuario.");
        }
        return resource;
    }

}
