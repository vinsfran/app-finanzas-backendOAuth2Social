package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Ahorro;
import py.com.fuentepy.appfinanzasBackend.resource.ahorro.AhorroModel;
import py.com.fuentepy.appfinanzasBackend.resource.ahorro.AhorroRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.ahorro.AhorroRequestUpdate;

import java.util.Date;
import java.util.List;

public interface AhorroService {

    List<AhorroModel> findAll();

    List<AhorroModel> findByUsuarioId(Long usuarioId);

    Page<AhorroModel> findAll(Pageable pageable);

    Page<AhorroModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    AhorroModel findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean create(AhorroRequestNew request, Long usuarioId);

    boolean update(AhorroRequestUpdate request, Long usuarioId);

    void delete(Long id);

    Long countByTenantName(Long usuarioId);

    List<Ahorro> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin);
}
