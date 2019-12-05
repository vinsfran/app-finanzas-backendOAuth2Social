package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.resource.prestamo.*;

import java.util.Date;
import java.util.List;

public interface PrestamoService {

    List<PrestamoModel> findAll();

    List<PrestamoModel> findByUsuarioId(Long usuarioId);

    Page<PrestamoModel> findAll(Pageable pageable);

    Page<PrestamoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    PrestamoModel findByIdAndUsuarioId(Long id, Long usuarioId);

    Movimiento create(PrestamoRequestNew request, Long usuarioId);

    boolean update(PrestamoRequestUpdate request, Long usuarioId);

    Movimiento pagar(PrestamoRequestPago prestamoRequestPago, Long usuarioId);

    PrestamoModel save(PrestamoModel prestamoModel);

    Long countByTenantName(Long usuarioId);

    List<Prestamo> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin);

    List<PrestamoMovimientoModel> findByUsuarioAndPrestamoId(Long usuarioId, Long prestamoId);

    void delete(Long usuarioId, Long PrestamoId) throws Exception;

}
