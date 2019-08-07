package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.entity.Mes;
import py.com.fuentepy.appfinanzasBackend.model.MesModel;

import java.util.List;

public interface MesService {

    List<Mes> findAll();

    Page<Mes> findAll(Pageable pageable);

    Mes findById(Integer id);

    MesModel save(MesModel mesModel);

    void delete(Integer id);
}
