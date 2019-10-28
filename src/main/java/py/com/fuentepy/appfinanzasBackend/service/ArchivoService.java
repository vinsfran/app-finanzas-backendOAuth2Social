package py.com.fuentepy.appfinanzasBackend.service;

import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;

import java.util.List;

public interface ArchivoService {

    Archivo findFotoPerfil(Long usuarioId);

    boolean save(Archivo archivo) throws Exception;
}
