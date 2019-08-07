package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.model.EntidadFinancieraModel;

import java.util.List;

public interface EntidadFinancieraService {

    List<EntidadFinancieraModel> findAll();

    List<EntidadFinancieraModel> findByUsuarioId(Long usuarioId);

    Page<EntidadFinancieraModel> findAll(Pageable pageable);

    Page<EntidadFinancieraModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    EntidadFinancieraModel findById(Integer id);

    EntidadFinancieraModel save(EntidadFinancieraModel entidadFinancieraModel);

    void delete(Integer id);
}
