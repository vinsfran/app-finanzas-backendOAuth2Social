package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.Concepto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Moneda;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoConcepto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.ConceptoModel;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.ConceptoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.ConceptoRequestUpdate;

import java.util.ArrayList;
import java.util.List;

@Component("conceptoConverter")
public class ConceptoConverter {

    public static Concepto conceptoRequestNewToConceptoEntity(ConceptoRequestNew request, Long usuarioId) {
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Concepto entity = new Concepto();
        entity.setNombre(request.getNombre());
        System.out.println(request.getTipoConcepto());
        entity.setTipoConcepto(TipoConcepto.valueOf(request.getTipoConcepto().toLowerCase()));
        entity.setMonedaId(moneda);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Concepto conceptoRequestUpdateToConceptoEntity(ConceptoRequestUpdate request, Long usuarioId) {
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Concepto entity = new Concepto();
        entity.setId(request.getId());
        entity.setNombre(request.getNombre());
        entity.setTipoConcepto(TipoConcepto.valueOf(request.getTipoConcepto().toLowerCase()));
        entity.setMonedaId(moneda);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Concepto modelToEntity(ConceptoModel model) {
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        Concepto entity = new Concepto();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static ConceptoModel entityToModel(Concepto entity) {
        ConceptoModel model = new ConceptoModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setTipoConcepto(entity.getTipoConcepto().name());
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
