package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.entity.EntidadFinanciera;
import py.com.fuentepy.appfinanzasBackend.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.model.EntidadFinancieraModel;

import java.util.ArrayList;
import java.util.List;

@Component("entidadFinancieraConverter")
public class EntidadFinancieraConverter {

    public static EntidadFinanciera modelToEntity(EntidadFinancieraModel model) {
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        EntidadFinanciera entity = new EntidadFinanciera();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static EntidadFinancieraModel entityToModel(EntidadFinanciera entity) {
        EntidadFinancieraModel model = new EntidadFinancieraModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setUsuarioId(entity.getUsuarioId().getId());
        return model;
    }

    public static List<EntidadFinancieraModel> listEntitytoListModel(List<EntidadFinanciera> listEntity) {
        List<EntidadFinancieraModel> listModel = new ArrayList<>();
        for (EntidadFinanciera entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<EntidadFinancieraModel> mapEntitiesIntoDTOs(Iterable<EntidadFinanciera> entities) {
        List<EntidadFinancieraModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<EntidadFinancieraModel> pageEntitytoPageModel(Pageable pageable, Page<EntidadFinanciera> pageEntity) {
        List<EntidadFinancieraModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
