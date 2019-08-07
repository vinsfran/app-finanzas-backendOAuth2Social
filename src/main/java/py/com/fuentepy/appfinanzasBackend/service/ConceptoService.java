package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.model.ConceptoModel;

import java.util.List;

public interface ConceptoService {

    List<ConceptoModel> findAll();

    List<ConceptoModel> findByUsuarioId(Long usuarioId);

    Page<ConceptoModel> findAll(Pageable pageable);

    Page<ConceptoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    ConceptoModel findById(Integer id);

    ConceptoModel save(ConceptoModel ahorroModel);

    void delete(Integer id);
}
