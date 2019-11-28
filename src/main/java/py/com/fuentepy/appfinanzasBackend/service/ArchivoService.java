package py.com.fuentepy.appfinanzasBackend.service;

import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;

import java.util.List;

public interface ArchivoService {

    List<ArchivoModel> getArchivos(Long usuarioId, Long tablaId, String tablaNombre);

    Archivo findFotoPerfil(Long usuarioId);

    boolean save(Archivo archivo) throws Exception;

    boolean saveList(List<ArchivoModel> archivos, Long tablaId, String tablaNombre, Long usuarioId) throws Exception;

    void deleteFiles(Long tablaId, String tablaNombre, Long usuarioId) throws Exception;
}
