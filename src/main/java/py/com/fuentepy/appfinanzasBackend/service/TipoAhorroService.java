package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoAhorro;

import java.util.List;

public interface TipoAhorroService {

    List<TipoAhorro> findAll();

    Page<TipoAhorro> findAll(Pageable pageable);

    TipoAhorro findById(Integer id);

    TipoAhorro save(TipoAhorro tipoAhorro);

    void delete(Integer id);
}
