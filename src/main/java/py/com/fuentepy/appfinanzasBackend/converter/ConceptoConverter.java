package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.Concepto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.model.ConceptoModel;

import java.util.ArrayList;
import java.util.List;

@Component("conceptoConverter")
public class ConceptoConverter {

    public static Concepto modelToEntity(ConceptoModel model) {
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        Concepto entity = new Concepto();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setTipoConcepto(model.getTipoConcepto());
        entity.setCodigoConcepto(model.getCodigoConcepto());
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static ConceptoModel entityToModel(Concepto entity) {
        ConceptoModel model = new ConceptoModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setTipoConcepto(entity.getTipoConcepto());
        model.setCodigoConcepto(entity.getCodigoConcepto());
        model.setUsuarioId(entity.getUsuarioId().getId());
        return model;
    }

    public static List<ConceptoModel> listEntitytoListModel(List<Concepto> listEntity) {
        List<ConceptoModel> listModel = new ArrayList<>();
        for (Concepto entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<ConceptoModel> mapEntitiesIntoDTOs(Iterable<Concepto> entities) {
        List<ConceptoModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<ConceptoModel> pageEntitytoPageModel(Pageable pageable, Page<Concepto> pageEntity) {
        List<ConceptoModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
