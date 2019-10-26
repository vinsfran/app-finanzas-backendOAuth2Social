package py.com.fuentepy.appfinanzasBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.fuentepy.appfinanzasBackend.resource.mes.MesModel;
import py.com.fuentepy.appfinanzasBackend.resource.mes.MesRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.mes.MesRequestUpdate;

import java.util.List;

public interface MesService {

    List<MesModel> findAll();

    Page<MesModel> findAll(Pageable pageable);

    MesModel findById(Integer id);

    boolean create(MesRequestNew request);

    boolean update(MesRequestUpdate request);

    void delete(Integer id);
}
