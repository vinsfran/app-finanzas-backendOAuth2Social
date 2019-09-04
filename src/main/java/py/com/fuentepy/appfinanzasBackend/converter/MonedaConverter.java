package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.Moneda;
import py.com.fuentepy.appfinanzasBackend.model.MonedaModel;

import java.util.ArrayList;
import java.util.List;

@Component("monedaConverter")
public class MonedaConverter {

    public static Moneda modelToEntity(MonedaModel model) {
        Moneda entity = new Moneda();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setCodigo(model.getCodigo());
        return entity;
    }

    public static MonedaModel entityToModel(Moneda entity) {
        MonedaModel model = new MonedaModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setCodigo(entity.getCodigo());
        return model;
    }

    public static List<MonedaModel> listEntitytoListModel(List<Moneda> listEntity) {
        List<MonedaModel> listModel = new ArrayList<>();
        for (Moneda entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<MonedaModel> mapEntitiesIntoDTOs(Iterable<Moneda> entities) {
        List<MonedaModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<MonedaModel> pageEntitytoPageModel(Pageable pageable, Page<Moneda> pageEntity) {
        List<MonedaModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
