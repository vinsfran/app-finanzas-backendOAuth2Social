package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.*;

import java.util.List;

public interface ConceptoService {

    List<ConceptoModel> findAll();

    List<ConceptoModel> findByUsuarioId(Long usuarioId);

    Page<ConceptoModel> findAll(Pageable pageable);

    Page<ConceptoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    ConceptoModel findByIdAndUsuarioId(Long id, Long usuarioId);

    ConceptoModel findById(Long id);

    boolean create(ConceptoRequestNew request, Long usuarioId);

    boolean update(ConceptoRequestUpdate request, Long usuarioId);

    boolean pagar(ConceptoRequestPago request, Long usuarioId);

    boolean cobrar(ConceptoRequestCobro request, Long usuarioId);

    void delete(Long id);

    List<ConceptoMovimientoModel> findByUsuarioAndConceptoId(Long usuarioId, Long ahorroId);
}
