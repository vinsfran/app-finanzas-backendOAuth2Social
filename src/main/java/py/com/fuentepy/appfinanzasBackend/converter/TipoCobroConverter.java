package py.com.fuentepy.appfinanzasBackend.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoCobro;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.tipoCobro.TipoCobroModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoCobro.TipoCobroRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoCobro.TipoCobroRequestUpdate;

import java.util.ArrayList;
import java.util.List;

@Component("tipoCobroConverter")
public class TipoCobroConverter {

    public static TipoCobro tipoCobroRequestNewToTipoCobroEntity(TipoCobroRequestNew request, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        TipoCobro entity = new TipoCobro();
        entity.setNombre(request.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static TipoCobro tipoCobroRequestUpdateToAhorroEntity(TipoCobroRequestUpdate request, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        TipoCobro entity = new TipoCobro();
        entity.setId(request.getId());
        entity.setNombre(request.getNombre());
        entity.setUsuarioId(usuario);
        return entity;
    }


    public static TipoCobro modelToEntity(TipoCobroModel model) {
        TipoCobro entity = new TipoCobro();
        entity.setNombre(model.getNombre());
        return entity;
    }

    public static TipoCobroModel entityToModel(TipoCobro entity) {
        TipoCobroModel model = new TipoCobroModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        return model;
    }

    public static List<TipoCobroModel> listEntitytoListModel(List<TipoCobro> listEntity) {
        List<TipoCobroModel> listModel = new ArrayList<>();
        for (TipoCobro entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<TipoCobroModel> mapEntitiesIntoDTOs(Iterable<TipoCobro> entities) {
        List<TipoCobroModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<TipoCobroModel> pageEntitytoPageModel(Pageable pageable, Page<TipoCobro> pageEntity) {
        List<TipoCobroModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
