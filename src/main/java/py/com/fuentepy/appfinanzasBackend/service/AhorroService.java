package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.entity.Ahorro;
import py.com.fuentepy.appfinanzasBackend.model.AhorroModel;
import py.com.fuentepy.appfinanzasBackend.resource.ahorro.AhorroNewRequest;

import java.util.Date;
import java.util.List;

public interface AhorroService {

    List<AhorroModel> findAll();

    List<AhorroModel> findByUsuarioId(Long usuarioId);

    Page<AhorroModel> findAll(Pageable pageable);

    Page<AhorroModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    AhorroModel findById(Long id);

    boolean create(AhorroNewRequest ahorroNewRequest, Long usuarioId);

    void delete(Long id);

    Long countByTenantName(Long usuarioId);

    List<Ahorro> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin);
}
