package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;
import py.com.fuentepy.appfinanzasBackend.resource.movimiento.MovimientoModel;

import java.util.Date;
import java.util.List;

public interface MovimientoService {

    List<MovimientoModel> findAll();

    List<MovimientoModel> findByUsuarioIdAndParent(Long usuarioId, String parent);

    Page<MovimientoModel> findAll(Pageable pageable);

    Page<MovimientoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    MovimientoModel findByIdAndUsuarioId(Long id, Long usuarioId);

    Movimiento registrarMovimiento(Movimiento movimiento);

    List<Movimiento> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin);

    List<Movimiento> findByUsuarioIdAndTablaIdAndTablaNombre(Long usuarioId, Long tablaId, String tablaNombre);

    void deleteMovimiento(Long usuarioId, Long id) throws Exception;

    void deleteMovimientos(Long tablaId, String tablaNombre, Long usuarioId) throws Exception;

}
