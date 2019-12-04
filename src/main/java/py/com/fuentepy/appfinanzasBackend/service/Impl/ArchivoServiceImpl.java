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
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;
import py.com.fuentepy.appfinanzasBackend.service.ArchivoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;
import py.com.fuentepy.appfinanzasBackend.util.StringUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    private static final Log LOG = LogFactory.getLog(ArchivoServiceImpl.class);

    @Autowired
    private ArchivoRepository archivoRepository;

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
            ArchivoModel archivoAnterior = null;
            List<ArchivoModel> archivos = getArchivos(archivo.getUsuarioId().getId(), archivo.getTablaId(), archivo.getTablaNombre());
            archivos.forEach(item -> System.out.println(item));
            if (archivos != null && !archivos.isEmpty() && archivos.get(0).getNombre().length() > 0) {
                archivoAnterior = archivos.get(0);
                if (archivoAnterior != null) {
                    Path rutaArchivoAnterior = Paths.get(ConstantUtil.UPLOADS).resolve(archivoAnterior.getNombre()).toAbsolutePath();
                    File archivoBorrar = rutaArchivoAnterior.toFile();
                    if (archivoBorrar.exists() && archivoBorrar.canRead()) {
                        archivoBorrar.delete();
                        archivo.setId(archivoAnterior.getId());
                    }
                }
            }
            Path rutaArchivo = Paths.get(ConstantUtil.UPLOADS).resolve(archivo.getNombre()).toAbsolutePath();
            Files.copy(multipartFile.getInputStream(), rutaArchivo);
            archivoRepository.save(archivo);
        } catch (Exception e) {
            throw new Exception("No se pudo guardar el Archivo! " + e.getMessage());
        }
        return nombreArchivo;
    }

    @Override
    public boolean saveList(Long tablaId, String tablaNombre, Long usuarioId, MultipartFile[] multipartFileList) throws Exception {
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
        List<Long> archivosIds = archivoRepository.listArchivoIdByUsuarioIdAndTablaIdAndTablaNombre(usuario, tablaId, tablaNombre);
        try {
            for (Long archivosId : archivosIds) {
                archivoRepository.deleteById(archivosId);
            }
        } catch (Exception e) {
            throw new Exception("No se pudieron eliminar los Archivos! " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Resource getArchivo(Long usuarioId, Long tablaId, String tablaNombre, String nombreArchivo) throws Exception {
        Resource resource = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        Archivo archivo = archivoRepository.findByUsuarioIdAndTablaIdAndTablaNombreAndNombre(usuario, tablaId, tablaNombre, nombreArchivo);
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

}
