package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.resource.moneda.MonedaModel;
import py.com.fuentepy.appfinanzasBackend.resource.moneda.MonedaRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.moneda.MonedaRequestUpdate;

import java.util.List;

public interface MonedaService {

    List<MonedaModel> findAll();

    Page<MonedaModel> findAll(Pageable pageable);

    MonedaModel findById(Integer id);

    boolean create(MonedaRequestNew request);

    boolean update(MonedaRequestUpdate request);

    void delete(Integer id);
}
