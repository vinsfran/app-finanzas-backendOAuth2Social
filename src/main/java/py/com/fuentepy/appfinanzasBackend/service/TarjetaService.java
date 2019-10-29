package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Tarjeta;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaModel;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestPago;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestUpdate;

import java.util.List;

public interface TarjetaService {

    List<TarjetaModel> findAll();

    List<TarjetaModel> findByUsuarioId(Long usuarioId);

    Page<TarjetaModel> findAll(Pageable pageable);

    Page<TarjetaModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    TarjetaModel findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean create(TarjetaRequestNew request, Long usuarioId);

    boolean update(TarjetaRequestUpdate request, Long usuarioId);

    boolean pagar(TarjetaRequestPago request, Long usuarioId);

    void delete(Long id);

    Long countByTenantName(Long usuarioId);

    List<Tarjeta> findByUsuarioIdLista(Long usuarioId);
}
