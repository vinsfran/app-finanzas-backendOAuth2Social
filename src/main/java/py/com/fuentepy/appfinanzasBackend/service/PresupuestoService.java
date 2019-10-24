package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.resource.presupuesto.PresupuestoModel;
import py.com.fuentepy.appfinanzasBackend.resource.presupuesto.PresupuestoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.presupuesto.PresupuestoRequestUpdate;

import java.util.List;

public interface PresupuestoService {

    List<PresupuestoModel> findAll();

    List<PresupuestoModel> findByUsuarioId(Long usuarioId);

    Page<PresupuestoModel> findAll(Pageable pageable);

    Page<PresupuestoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    PresupuestoModel findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean create(PresupuestoRequestNew request, Long usuarioId);

    boolean update(PresupuestoRequestUpdate request, Long usuarioId);

    void delete(Long id);
}
