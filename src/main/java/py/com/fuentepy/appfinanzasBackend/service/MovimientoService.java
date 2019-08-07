package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.model.MovimientoModel;

import java.util.Date;
import java.util.List;

public interface MovimientoService {

    List<MovimientoModel> findAll();

    List<MovimientoModel> findByUsuarioId(Long usuarioId);

    Page<MovimientoModel> findAll(Pageable pageable);

    Page<MovimientoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    MovimientoModel findById(Long id);

    MovimientoModel save(MovimientoModel movimientoModel, String action);

    void delete(Long id);

    List<Movimiento> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin);

    Page<MovimientoModel> findByPrestamoId(Long prestamoId, Pageable pageable);

    Page<MovimientoModel> findByAhorroId(Long ahorroId, Pageable pageable);

    Page<MovimientoModel> findByTarjetaId(Long tarjetaId, Pageable pageable);
}
