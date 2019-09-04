package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoCobro;

import java.util.List;

public interface TipoCobroService {

    List<TipoCobro> findAll();

    Page<TipoCobro> findAll(Pageable pageable);

    TipoCobro findById(Integer id);

    TipoCobro save(TipoCobro tipoCobro);

    void delete(Integer id);
}
