package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.EntidadFinanciera;
import py.com.fuentepy.appfinanzasBackend.data.entity.Tarjeta;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.model.TarjetaModel;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("tarjetaConverter")
public class TarjetaConverter {

    private static final Log LOG = LogFactory.getLog(TarjetaConverter.class);

    public static Tarjeta modelToEntity(TarjetaModel model) {
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(model.getEntidadFinancieraId());
        entidadFinanciera.setNombre(model.getEntidadFinancieraNombre());
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        Tarjeta entity = new Tarjeta();
        entity.setId(model.getId());
        entity.setNumeroTarjeta(model.getNumeroTarjeta());
        entity.setMarca(model.getMarca());
        entity.setLineaCredito(model.getLineaCredito());
        entity.setFechaVencimiento(model.getFechaVencimiento());
        entity.setEstado(model.getEstado());
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static TarjetaModel entityToModel(Tarjeta entity) {
        TarjetaModel model = new TarjetaModel();
        model.setId(entity.getId());
        model.setNumeroTarjeta(entity.getNumeroTarjeta());
        model.setMarca(entity.getMarca());
        model.setLineaCredito(entity.getLineaCredito());
        model.setFechaVencimiento(entity.getFechaVencimiento());
        model.setEstado(entity.getEstado());
        model.setEntidadFinancieraId(entity.getEntidadFinancieraId().getId());
        model.setEntidadFinancieraNombre(entity.getEntidadFinancieraId().getNombre());
        model.setUsuarioId(entity.getUsuarioId().getId());
        return model;
    }

    public static List<TarjetaModel> listEntitytoListModel(List<Tarjeta> listEntity) {
        List<TarjetaModel> listModel = new ArrayList<>();
        for (Tarjeta entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<TarjetaModel> mapEntitiesIntoDTOs(Iterable<Tarjeta> entities) {
        List<TarjetaModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<TarjetaModel> pageEntitytoPageModel(Pageable pageable, Page<Tarjeta> pageEntity) {
        List<TarjetaModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
