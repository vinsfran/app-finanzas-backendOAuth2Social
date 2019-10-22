package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.AhorroTipo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo.AhorroTipoModel;
import py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo.AhorroTipoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo.AhorroTipoRequestUpdate;

import java.util.ArrayList;
import java.util.List;

@Component("ahorroTipoConverter")
public class AhorroTipoConverter {

    public static AhorroTipo ahorroTipoRequestNewToAhorroTipoEntity(AhorroTipoRequestNew request, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        AhorroTipo entity = new AhorroTipo();
        entity.setNombre(request.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static AhorroTipo ahorroTipoRequestUpdateToAhorroEntity(AhorroTipoRequestUpdate request, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        AhorroTipo entity = new AhorroTipo();
        entity.setId(request.getId());
        entity.setNombre(request.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }


    public static AhorroTipo modelToEntity(AhorroTipoModel model) {
        AhorroTipo entity = new AhorroTipo();
        entity.setNombre(model.getNombre());
        return entity;
    }

    public static AhorroTipoModel entityToModel(AhorroTipo entity) {
        AhorroTipoModel model = new AhorroTipoModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        return model;
    }

    public static List<AhorroTipoModel> listEntitytoListModel(List<AhorroTipo> listEntity) {
        List<AhorroTipoModel> listModel = new ArrayList<>();
        for (AhorroTipo entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<AhorroTipoModel> mapEntitiesIntoDTOs(Iterable<AhorroTipo> entities) {
        List<AhorroTipoModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<AhorroTipoModel> pageEntitytoPageModel(Pageable pageable, Page<AhorroTipo> pageEntity) {
        List<AhorroTipoModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
