package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Ahorro;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.resource.ahorro.*;

import java.util.Date;
import java.util.List;

public interface AhorroService {

    List<AhorroModel> findAll();

    List<AhorroModel> findByUsuarioId(Long usuarioId);

    Page<AhorroModel> findAll(Pageable pageable);

    Page<AhorroModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    AhorroModel findByAhorroIdAndUsuarioId(Long ahorroId, Long usuarioId);

    boolean create(AhorroRequestNew request, Long usuarioId);

    boolean update(AhorroRequestUpdate request, Long usuarioId);

    Movimiento pagar(AhorroRequestPago request, Long usuarioId);

    Movimiento cobrar(AhorroRequestCobro request, Long usuarioId);

    Long countByTenantName(Long usuarioId);

    List<Ahorro> findByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin);

    List<Ahorro> findByUsuarioAndEstado(Long usuarioId, boolean estado);

    List<AhorroMovimientoModel> findByUsuarioAndAhorroId(Long usuarioId, Long ahorroId);

    void delete(Long usuarioId, Long ahorroId) throws Exception;

    void deleteMovimiento(Long usuarioId, Long ahorroId, Long movimientoId) throws Exception;
}
