package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.model.PresupuestoModel;

import java.util.List;

public interface PresupuestoService {

    List<PresupuestoModel> findAll();

    List<PresupuestoModel> findByUsuarioId(Long usuarioId);

    Page<PresupuestoModel> findAll(Pageable pageable);

    Page<PresupuestoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    PresupuestoModel findById(Long id);

    PresupuestoModel save(PresupuestoModel presupuestoModel);

    void delete(Long id);
}
