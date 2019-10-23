package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Moneda;
import py.com.fuentepy.appfinanzasBackend.resource.moneda.MonedaModel;

import java.util.List;

public interface MonedaService {

    List<Moneda> findAll();

    Page<Moneda> findAll(Pageable pageable);

    Moneda findById(Integer id);

    MonedaModel save(MonedaModel monedaModel);

    void delete(Integer id);
}
