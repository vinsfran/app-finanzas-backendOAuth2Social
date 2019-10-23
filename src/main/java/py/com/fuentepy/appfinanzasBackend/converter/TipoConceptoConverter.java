package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoConcepto;
import py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto.TipoConceptoModel;

import java.util.ArrayList;
import java.util.List;

@Component("tipoConceptoConverter")
public class TipoConceptoConverter {

    public static TipoConcepto modelToEntity(TipoConceptoModel model) {
        TipoConcepto entity = new TipoConcepto();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setSigno(model.getSigno());
        return entity;
    }

    public static TipoConceptoModel entityToModel(TipoConcepto entity) {
        TipoConceptoModel model = new TipoConceptoModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setSigno(entity.getSigno());
        return model;
    }

    public static List<TipoConceptoModel> listEntitytoListModel(List<TipoConcepto> listEntity) {
        List<TipoConceptoModel> listModel = new ArrayList<>();
        for (TipoConcepto entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<TipoConceptoModel> mapEntitiesIntoDTOs(Iterable<TipoConcepto> entities) {
        List<TipoConceptoModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<TipoConceptoModel> pageEntitytoPageModel(Pageable pageable, Page<TipoConcepto> pageEntity) {
        List<TipoConceptoModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
