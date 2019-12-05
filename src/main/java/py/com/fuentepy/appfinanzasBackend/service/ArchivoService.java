package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;

import java.util.List;

public interface ArchivoService {

    List<ArchivoModel> getArchivos(Long usuarioId, Long tablaId, String tablaNombre);

    Archivo findFotoPerfil(Long usuarioId);

    String save(Long tablaId, String tablaNombre, Long usuarioId, MultipartFile multipartFile) throws Exception;

    boolean saveList(Long tablaId, String tablaNombre, Long usuarioId, List<MultipartFile> multipartFileList) throws Exception;

    void deleteFiles(Long tablaId, String tablaNombre, Long usuarioId) throws Exception;

    Resource getArchivo(Long usuarioId, Long tablaId, String tablaNombre, String nombreArchivo) throws Exception;
}
