package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.resource.tipoPago.TipoPagoModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoPago.TipoPagoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoPago.TipoPagoRequestUpdate;

import java.util.List;

public interface TipoPagoService {

    List<TipoPagoModel> findByUsuarioId(Long usuarioId);

    Page<TipoPagoModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    TipoPagoModel findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean create(TipoPagoRequestNew request, Long usuarioId);

    boolean update(TipoPagoRequestUpdate request, Long usuarioId);

    void delete(Long id);
}
