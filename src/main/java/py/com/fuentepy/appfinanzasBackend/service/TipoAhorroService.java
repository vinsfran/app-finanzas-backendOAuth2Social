package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.AhorroTipo;
import py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo.AhorroTipoModel;
import py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo.AhorroTipoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo.AhorroTipoRequestUpdate;

import java.util.List;

public interface TipoAhorroService {

    List<AhorroTipo> findAll();

    List<AhorroTipoModel> findByUsuarioId(Long usuarioId);

    Page<AhorroTipo> findAll(Pageable pageable);

    Page<AhorroTipoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    AhorroTipoModel findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean create(AhorroTipoRequestNew request, Long usuarioId);

    boolean update(AhorroTipoRequestUpdate request, Long usuarioId);

    void delete(Integer id);
}
