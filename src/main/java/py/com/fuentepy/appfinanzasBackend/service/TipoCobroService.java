package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoCobro;
import py.com.fuentepy.appfinanzasBackend.resource.tipoCobro.TipoCobroModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoCobro.TipoCobroRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoCobro.TipoCobroRequestUpdate;

import java.util.List;

public interface TipoCobroService {

    List<TipoCobroModel> findByUsuarioId(Long usuarioId);

    Page<TipoCobroModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    TipoCobroModel findByIdAndUsuarioId(Long id, Long usuarioId);

    boolean create(TipoCobroRequestNew request, Long usuarioId);

    boolean update(TipoCobroRequestUpdate request, Long usuarioId);

    void delete(Long id);
}
