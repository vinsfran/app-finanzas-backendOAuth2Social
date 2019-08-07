package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.entity.EntidadFinanciera;
import py.com.fuentepy.appfinanzasBackend.entity.Moneda;
import py.com.fuentepy.appfinanzasBackend.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.model.PrestamoModel;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("prestamoConverter")
public class PrestamoConverter {

    private static final Log LOG = LogFactory.getLog(PrestamoConverter.class);

    public static Prestamo modelToEntity(PrestamoModel model) {
        Moneda moneda = new Moneda();
        moneda.setId(model.getMonedaId());
        moneda.setNombre(model.getMonedaNombre());
        moneda.setCodigo(model.getMonedaCodigo());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(model.getEntidadFinancieraId());
        entidadFinanciera.setNombre(model.getEntidadFinancieraNombre());
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        Prestamo entity = new Prestamo();
        entity.setId(model.getId());
        entity.setMontoPrestamo(model.getMontoPrestamo());
        entity.setFechaDesembolso(model.getFechaDesembolso());
        entity.setFechaVencimiento(model.getFechaVencimiento());
        entity.setInteres(model.getInteres());
        entity.setTasa(model.getTasa());
        entity.setCantidadCuotas(model.getCantidadCuotas());
        entity.setCantidadCuotasPagadas(model.getCantidadCuotasPagadas());
        entity.setMontoCuota(model.getMontoCuota());
        entity.setMontoPagado(model.getMontoPagado());
        entity.setDestinoPrestamo(model.getDestinoPrestamo());
        entity.setEstado(model.getEstado());
        entity.setMonedaId(moneda);
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static PrestamoModel entityToModel(Prestamo entity) {
        PrestamoModel model = new PrestamoModel();
        model.setId(entity.getId());
        model.setMontoPrestamo(entity.getMontoPrestamo());
        model.setFechaDesembolso(entity.getFechaDesembolso());
        model.setFechaVencimiento(entity.getFechaVencimiento());
        model.setInteres(entity.getInteres());
        model.setTasa(entity.getTasa());
        model.setCantidadCuotas(entity.getCantidadCuotas());
        model.setCantidadCuotasPagadas(entity.getCantidadCuotasPagadas());
        model.setMontoCuota(entity.getMontoCuota());
        model.setMontoPagado(entity.getMontoPagado());
        model.setDestinoPrestamo(entity.getDestinoPrestamo());
        model.setEstado(entity.getEstado());
        model.setMonedaId(entity.getMonedaId().getId());
        model.setMonedaNombre(entity.getMonedaId().getNombre());
        model.setMonedaCodigo(entity.getMonedaId().getCodigo());
        model.setEntidadFinancieraId(entity.getEntidadFinancieraId().getId());
        model.setEntidadFinancieraNombre(entity.getEntidadFinancieraId().getNombre());
        model.setUsuarioId(entity.getUsuarioId().getId());
        return model;
    }

    public static List<PrestamoModel> listEntitytoListModel(List<Prestamo> listEntity) {
        List<PrestamoModel> listModel = new ArrayList<>();
        for (Prestamo entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<PrestamoModel> mapEntitiesIntoDTOs(Iterable<Prestamo> entities) {
        List<PrestamoModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<PrestamoModel> pageEntitytoPageModel(Pageable pageable, Page<Prestamo> pageEntity) {
        List<PrestamoModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
