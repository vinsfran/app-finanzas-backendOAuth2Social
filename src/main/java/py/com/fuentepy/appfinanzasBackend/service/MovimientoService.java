package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.resource.movimiento.MovimientoModel;

import java.util.Date;
import java.util.List;

public interface MovimientoService {

    List<MovimientoModel> findAll();

    List<MovimientoModel> findByUsuarioId(Long usuarioId);

    Page<MovimientoModel> findAll(Pageable pageable);

    Page<MovimientoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    MovimientoModel findByIdAndUsuarioId(Long id, Long usuarioId);

    MovimientoModel save(MovimientoModel movimientoModel, String action);

    void delete(Long id);

    List<Movimiento> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin);

}
