package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoPago;
import py.com.fuentepy.appfinanzasBackend.model.TipoPagoModel;

import java.util.ArrayList;
import java.util.List;

@Component("tipoPagoConverter")
public class TipoPagoConverter {

    public static TipoPago modelToEntity(TipoPagoModel model) {
        TipoPago entity = new TipoPago();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        return entity;
    }

    public static TipoPagoModel entityToModel(TipoPago entity) {
        TipoPagoModel model = new TipoPagoModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        return model;
    }

    public static List<TipoPagoModel> listEntitytoListModel(List<TipoPago> listEntity) {
        List<TipoPagoModel> listModel = new ArrayList<>();
        for (TipoPago entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<TipoPagoModel> mapEntitiesIntoDTOs(Iterable<TipoPago> entities) {
        List<TipoPagoModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<TipoPagoModel> pageEntitytoPageModel(Pageable pageable, Page<TipoPago> pageEntity) {
        List<TipoPagoModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
