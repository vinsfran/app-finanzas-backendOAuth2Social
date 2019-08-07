package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.model.TipoPagoModel;

import java.util.List;

public interface TipoPagoService {

    List<TipoPagoModel> findAll();

    Page<TipoPagoModel> findAll(Pageable pageable);

    TipoPagoModel findById(Integer id);

    TipoPagoModel save(TipoPagoModel model);

    void delete(Integer id);
}
