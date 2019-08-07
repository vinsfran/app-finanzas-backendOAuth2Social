package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.entity.Mes;
import py.com.fuentepy.appfinanzasBackend.model.MesModel;

import java.util.ArrayList;
import java.util.List;

@Component("mesConverter")
public class MesConverter {

    public static Mes modelToEntity(MesModel model) {
        Mes entity = new Mes();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setNumero(model.getNumero());
        return entity;
    }

    public static MesModel entityToModel(Mes entity) {
        MesModel model = new MesModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setNumero(entity.getNumero());
        return model;
    }

    public static List<MesModel> listEntitytoListModel(List<Mes> listEntity) {
        List<MesModel> listModel = new ArrayList<>();
        for (Mes entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<MesModel> mapEntitiesIntoDTOs(Iterable<Mes> entities) {
        List<MesModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<MesModel> pageEntitytoPageModel(Pageable pageable, Page<Mes> pageEntity) {
        List<MesModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
