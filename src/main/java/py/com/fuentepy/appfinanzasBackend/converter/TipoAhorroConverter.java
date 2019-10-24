package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoAhorro;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro.TipoAhorroModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro.TipoAhorroRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro.TipoAhorroRequestUpdate;

import java.util.ArrayList;
import java.util.List;

@Component("tipoAhorroConverter")
public class TipoAhorroConverter {

    public static TipoAhorro tipoAhorroRequestNewToTipoAhorroEntity(TipoAhorroRequestNew request, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        TipoAhorro entity = new TipoAhorro();
        entity.setNombre(request.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static TipoAhorro tipoAhorroRequestUpdateToTipoAhorroEntity(TipoAhorroRequestUpdate request, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        TipoAhorro entity = new TipoAhorro();
        entity.setId(request.getId());
        entity.setNombre(request.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }


    public static TipoAhorro modelToEntity(TipoAhorroModel model) {
        TipoAhorro entity = new TipoAhorro();
        entity.setNombre(model.getNombre());
        return entity;
    }

    public static TipoAhorroModel entityToModel(TipoAhorro entity) {
        TipoAhorroModel model = new TipoAhorroModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        return model;
    }

    public static List<TipoAhorroModel> listEntitytoListModel(List<TipoAhorro> listEntity) {
        List<TipoAhorroModel> listModel = new ArrayList<>();
        for (TipoAhorro entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<TipoAhorroModel> mapEntitiesIntoDTOs(Iterable<TipoAhorro> entities) {
        List<TipoAhorroModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<TipoAhorroModel> pageEntitytoPageModel(Pageable pageable, Page<TipoAhorro> pageEntity) {
        List<TipoAhorroModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
