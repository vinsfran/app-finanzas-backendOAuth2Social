package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera.EntidadFinancieraModel;
import py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera.EntidadFinancieraRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera.EntidadFinancieraRequestUpdate;

import java.util.List;

public interface EntidadFinancieraService {

    List<EntidadFinancieraModel> findAll();

    List<EntidadFinancieraModel> findByUsuarioId(Long usuarioId);

    Page<EntidadFinancieraModel> findAll(Pageable pageable);

    Page<EntidadFinancieraModel> findByUsuarioId(Long usuarioId, Pageable pageable);

    EntidadFinancieraModel findByIdAndUsuarioId(Long id, Long usuarioId);

    EntidadFinancieraModel findById(Long id);

    boolean create(EntidadFinancieraRequestNew request, Long usuarioId);

    boolean update(EntidadFinancieraRequestUpdate request, Long usuarioId);

    void delete(Long id);

    void saveDefault(Usuario usuario);
}
