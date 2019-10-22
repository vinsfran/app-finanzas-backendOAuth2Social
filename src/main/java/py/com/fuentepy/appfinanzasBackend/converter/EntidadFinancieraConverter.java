package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.EntidadFinanciera;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera.EntidadFinancieraModel;
import py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera.EntidadFinancieraRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera.EntidadFinancieraRequestUpdate;

import java.util.ArrayList;
import java.util.List;

@Component("entidadFinancieraConverter")
public class EntidadFinancieraConverter {

    public static EntidadFinanciera entidadFinancieraRequestNewToEntidadFinancieraEntity(EntidadFinancieraRequestNew request, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        EntidadFinanciera entity = new EntidadFinanciera();
        entity.setNombre(request.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static EntidadFinanciera entidadFinancieraRequestUpdateToAhorroEntity(EntidadFinancieraRequestUpdate request, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        EntidadFinanciera entity = new EntidadFinanciera();
        entity.setId(request.getId());
        entity.setNombre(request.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }


    public static EntidadFinanciera modelToEntity(EntidadFinancieraModel model) {
        EntidadFinanciera entity = new EntidadFinanciera();
        entity.setNombre(model.getNombre());
        return entity;
    }

    public static EntidadFinancieraModel entityToModel(EntidadFinanciera entity) {
        EntidadFinancieraModel model = new EntidadFinancieraModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
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
