package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.Moneda;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.movimiento.MovimientoModel;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("movimientoConverter")
public class MovimientoConverter {

    private static final Log LOG = LogFactory.getLog(MovimientoConverter.class);

    public static Movimiento modelToEntity(MovimientoModel model) {
        Moneda moneda = new Moneda();
        moneda.setId(model.getMonedaId());
        moneda.setNombre(model.getMonedaNombre());
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        Movimiento entity = new Movimiento();
        entity.setId(model.getId());
        entity.setNumeroComprobante(model.getNumeroComprobante());
        entity.setFechaMovimiento(model.getFechaMovimiento());
        entity.setMonto(model.getMonto());
        entity.setNumeroCuota(model.getNumeroCuota());
        entity.setSigno(model.getSigno());
        entity.setDetalle(model.getDetalle());
        entity.setNumeroCuota(model.getNumeroCuota());
        entity.setTablaId(model.getTablaId());
        entity.setTablaName(model.getTablaName());
        entity.setMonedaId(moneda);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static MovimientoModel entityToModel(Movimiento entity) {
        MovimientoModel model = new MovimientoModel();
        model.setId(entity.getId());
        model.setNumeroComprobante(entity.getNumeroComprobante());
        model.setFechaMovimiento(entity.getFechaMovimiento());
        model.setMonto(entity.getMonto());
        model.setNumeroCuota(entity.getNumeroCuota());
        model.setSigno(entity.getSigno());
        model.setDetalle(entity.getDetalle());
        model.setNumeroCuota(entity.getNumeroCuota());
        model.setTablaId(entity.getTablaId());
        model.setTablaName(entity.getTablaName());
        model.setMonedaId(entity.getMonedaId().getId());
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
