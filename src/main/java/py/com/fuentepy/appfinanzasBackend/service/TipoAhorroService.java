package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro.TipoAhorroModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro.TipoAhorroRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro.TipoAhorroRequestUpdate;

import java.util.List;

public interface TipoAhorroService {

    List<TipoAhorroModel> findByUsuarioId(Long usuarioId);

    Page<TipoAhorroModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    TipoAhorroModel findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean create(TipoAhorroRequestNew request, Long usuarioId);

    boolean update(TipoAhorroRequestUpdate request, Long usuarioId);

    void delete(Long id);

    void saveDefault(Usuario usuario);
}
