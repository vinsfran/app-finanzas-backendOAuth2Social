package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Tarjeta;
import py.com.fuentepy.appfinanzasBackend.model.TarjetaModel;

import java.util.List;

public interface TarjetaService {

    List<TarjetaModel> findAll();

    List<TarjetaModel> findByUsuarioId(Long usuarioId);

    Page<TarjetaModel> findAll(Pageable pageable);

    Page<TarjetaModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    TarjetaModel findById(Long id);

    TarjetaModel save(TarjetaModel TarjetaModel);

    void delete(Long id);

    Long countByTenantName(Long usuarioId);

    List<Tarjeta> findByUsuarioIdLista(Long usuarioId);
}
