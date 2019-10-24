package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto.TipoConceptoModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto.TipoConceptoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto.TipoConceptoRequestUpdate;

import java.util.List;

public interface TipoConceptoService {

    List<TipoConceptoModel> findAll();

    Page<TipoConceptoModel> findAll(Pageable pageable);

    TipoConceptoModel findById(Integer id);

    boolean create(TipoConceptoRequestNew request);

    boolean update(TipoConceptoRequestUpdate request);

    void delete(Integer id);
}
