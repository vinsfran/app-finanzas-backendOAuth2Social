package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.ConceptoModel;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.ConceptoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.ConceptoRequestUpdate;

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

    void delete(Long id);
}
