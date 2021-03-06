package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
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

    Movimiento pagar(ConceptoRequestPago request, Long usuarioId);

    Movimiento cobrar(ConceptoRequestCobro request, Long usuarioId);

    List<ConceptoMovimientoModel> findByUsuarioAndConceptoId(Long usuarioId, Long ahorroId);

    void delete(Long usuarioId, Long ConceptoId) throws Exception;

    void saveDefault(Usuario usuario);
}
