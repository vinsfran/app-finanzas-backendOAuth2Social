package py.com.fuentepy.appfinanzasBackend.service;

import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;

import java.util.List;

public interface ArchivoService {

    Archivo findFotoPerfil(Long usuarioId);

    boolean save(Archivo archivo) throws Exception;

    boolean saveList(List<ArchivoModel> archivos, Long tablaId, String tablaNombre, Usuario usuario) throws Exception;
}
