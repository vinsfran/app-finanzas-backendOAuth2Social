package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Tarjeta;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.*;

import java.util.List;

public interface TarjetaService {

    List<TarjetaModel> findAll();

    List<TarjetaModel> findByUsuarioId(Long usuarioId);

    Page<TarjetaModel> findAll(Pageable pageable);

    Page<TarjetaModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    TarjetaModel findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean create(TarjetaRequestNew request, Long usuarioId);

    boolean update(TarjetaRequestUpdate request, Long usuarioId);

    Movimiento pagar(TarjetaRequestPago request, Long usuarioId);

    Long countByTenantName(Long usuarioId);

    List<Tarjeta> findByUsuarioIdLista(Long usuarioId);

    List<TarjetaMovimientoModel> findByUsuarioAndTarjetaId(Long usuarioId, Long tarjetaId);

    void delete(Long usuarioId, Long tarjetaId) throws Exception;
}
