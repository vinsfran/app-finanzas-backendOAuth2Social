package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoConcepto;
import py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto.TipoConceptoModel;

import java.util.List;

public interface TipoConceptoService {

    List<TipoConcepto> findAll();

    Page<TipoConcepto> findAll(Pageable pageable);

    TipoConcepto findById(Integer id);

    TipoConceptoModel save(TipoConceptoModel monedaModel);

    void delete(Integer id);
}
