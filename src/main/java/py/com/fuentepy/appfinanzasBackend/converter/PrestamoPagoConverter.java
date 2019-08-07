package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.entity.PrestamoPago;
import py.com.fuentepy.appfinanzasBackend.entity.TipoPago;
import py.com.fuentepy.appfinanzasBackend.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.model.PrestamoPagoModel;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("prestamoPagoConverter")
public class PrestamoPagoConverter {

    private static final Log LOG = LogFactory.getLog(PrestamoPagoConverter.class);

    public static PrestamoPago modelToEntity(PrestamoPagoModel model) {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(model.getPrestamoId());
        TipoPago tipoPago = new TipoPago();
        tipoPago.setId(model.getTipoPagoId());
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());

        PrestamoPago entity = new PrestamoPago();
        entity.setId(model.getId());
        entity.setNumeroCuota(model.getNumeroCuota());
        entity.setFechaPago(model.getFechaPago());
        entity.setMontoPagado(model.getMontoPagado());
        entity.setPrestamoId(prestamo);
        entity.setTipoPagoId(tipoPago);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static PrestamoPagoModel entityToModel(PrestamoPago entity) {
        PrestamoPagoModel model = new PrestamoPagoModel();
        model.setId(entity.getId());
        model.setNumeroCuota(entity.getNumeroCuota());
        model.setFechaPago(entity.getFechaPago());
        model.setMontoPagado(entity.getMontoPagado());
        model.setPrestamoId(entity.getPrestamoId().getId());
        model.setDestinoPrestamo(entity.getPrestamoId().getDestinoPrestamo());
        model.setTipoPagoId(entity.getTipoPagoId().getId());
        model.setTipoPagoNombre(entity.getTipoPagoId().getNombre());
        model.setUsuarioId(entity.getUsuarioId().getId());
        return model;
    }

    public static List<PrestamoPagoModel> listEntitytoListModel(List<PrestamoPago> listEntity) {
        List<PrestamoPagoModel> listModel = new ArrayList<>();
        for (PrestamoPago entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<PrestamoPagoModel> mapEntitiesIntoDTOs(Iterable<PrestamoPago> entities) {
        List<PrestamoPagoModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<PrestamoPagoModel> pageEntitytoPageModel(Pageable pageable, Page<PrestamoPago> pageEntity) {
        List<PrestamoPagoModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
