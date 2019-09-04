package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.model.PrestamoModel;

import java.util.Date;
import java.util.List;

public interface PrestamoService {

    List<PrestamoModel> findAll();

    List<PrestamoModel> findByUsuarioId(Long usuarioId);

    Page<PrestamoModel> findAll(Pageable pageable);

    Page<PrestamoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    PrestamoModel findById(Long id);

    PrestamoModel save(PrestamoModel prestamoModel);

    void delete(Long id);

    Long countByTenantName(Long usuarioId);

    List<Prestamo> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin);
}
