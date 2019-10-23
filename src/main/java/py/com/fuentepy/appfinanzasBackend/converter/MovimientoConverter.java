package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.*;
import py.com.fuentepy.appfinanzasBackend.model.MovimientoModel;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("movimientoConverter")
public class MovimientoConverter {

    private static final Log LOG = LogFactory.getLog(MovimientoConverter.class);

    public static Movimiento modelToEntity(MovimientoModel model) {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(model.getPrestamoId());
        Ahorro ahorro = new Ahorro();
        ahorro.setId(model.getAhorroId());
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(model.getTarjetaId());
        TipoConcepto tipoConcepto = new TipoConcepto();
        tipoConcepto.setId(model.getTipoConceptoId());
        Concepto concepto = new Concepto();
        concepto.setId(model.getConceptoId());
        concepto.setNombre(model.getConceptoNombre());
        concepto.setTipoConceptoId(tipoConcepto);
        Moneda moneda = new Moneda();
        moneda.setId(model.getMonedaId());
        moneda.setNombre(model.getMonedaNombre());
        moneda.setCodigo(model.getMonedaCodigo());
        TipoPago tipoPago = new TipoPago();
        tipoPago.setId(model.getTipoPagoId());
        tipoPago.setNombre(model.getTipoPagoNombre());
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        Movimiento entity = new Movimiento();
        entity.setId(model.getId());
        entity.setNumeroComprobante(model.getNumeroComprobante());
        entity.setFechaMovimiento(model.getFechaMovimiento());
        entity.setMontoPagado(model.getMontoPagado());
        entity.setNombreEntidad(model.getNombreEntidad());
        entity.setPrestamoId(prestamo);
        entity.setAhorroId(ahorro);
        entity.setTarjetaId(tarjeta);
        entity.setNumeroCuota(model.getNumeroCuota());
        entity.setConceptoId(concepto);
        entity.setMonedaId(moneda);
        entity.setTipoPagoId(tipoPago);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static MovimientoModel entityToModel(Movimiento entity) {
        MovimientoModel model = new MovimientoModel();
        model.setId(entity.getId());
        model.setNumeroComprobante(entity.getNumeroComprobante());
        model.setFechaMovimiento(entity.getFechaMovimiento());
        model.setMontoPagado(entity.getMontoPagado());
        model.setNombreEntidad(entity.getNombreEntidad());
        if (entity.getPrestamoId() != null) {
            model.setPrestamoId(entity.getPrestamoId().getId());
        }
        if (entity.getAhorroId() != null) {
            model.setAhorroId(entity.getAhorroId().getId());
        }
        if (entity.getTarjetaId() != null) {
            model.setTarjetaId(entity.getTarjetaId().getId());
        }
        model.setNumeroCuota(entity.getNumeroCuota());
        model.setConceptoId(entity.getConceptoId().getId());
        model.setConceptoNombre(entity.getConceptoId().getNombre());
        model.setTipoConceptoId(entity.getConceptoId().getTipoConceptoId().getId());
        model.setTipoConceptoNombre(entity.getConceptoId().getTipoConceptoId().getNombre());
        model.setTipoConceptoSigno(entity.getConceptoId().getTipoConceptoId().getSigno());
        model.setMonedaId(entity.getMonedaId().getId());
        model.setMonedaNombre(entity.getMonedaId().getNombre());
        model.setMonedaCodigo(entity.getMonedaId().getCodigo());
        model.setTipoPagoId(entity.getTipoPagoId().getId());
        model.setTipoPagoNombre(entity.getTipoPagoId().getNombre());
        model.setUsuarioId(entity.getUsuarioId().getId());
        return model;
    }

    public static List<MovimientoModel> listEntitytoListModel(List<Movimiento> listEntity) {
        List<MovimientoModel> listModel = new ArrayList<>();
        for (Movimiento entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<MovimientoModel> mapEntitiesIntoDTOs(Iterable<Movimiento> entities) {
        List<MovimientoModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<MovimientoModel> pageEntitytoPageModel(Pageable pageable, Page<Movimiento> pageEntity) {
        List<MovimientoModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
