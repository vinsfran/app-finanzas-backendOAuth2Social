package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.*;
import py.com.fuentepy.appfinanzasBackend.resource.prestamo.PrestamoModel;
import py.com.fuentepy.appfinanzasBackend.resource.prestamo.PrestamoMovimientoModel;
import py.com.fuentepy.appfinanzasBackend.resource.prestamo.PrestamoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.prestamo.PrestamoRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("prestamoConverter")
public class PrestamoConverter {

    private static final Log LOG = LogFactory.getLog(PrestamoConverter.class);

    public static PrestamoMovimientoModel movimientoToPrestamoMovimientoModel(Movimiento movimiento) {
        PrestamoMovimientoModel prestamoMovimientoModel = new PrestamoMovimientoModel();
        prestamoMovimientoModel.setMovimientoId(movimiento.getId());
        prestamoMovimientoModel.setDetalle(movimiento.getDetalle());
        prestamoMovimientoModel.setNumeroComprobante(movimiento.getNumeroComprobante());
        prestamoMovimientoModel.setFechaMovimiento(movimiento.getFechaMovimiento());
        prestamoMovimientoModel.setMonto(movimiento.getMonto());
        prestamoMovimientoModel.setNumeroCuota(movimiento.getNumeroCuota());
        prestamoMovimientoModel.setTipoMovimiento("INGRESO");
        if (movimiento.getSigno().equals("-")) {
            prestamoMovimientoModel.setTipoMovimiento("EGRESO");
        }
        prestamoMovimientoModel.setMoneda(movimiento.getMonedaId().getNombre());
        return prestamoMovimientoModel;
    }

    public static List<PrestamoMovimientoModel> listMovimientosToListPrestamoMovimientoModel(List<Movimiento> listEntity) {
        List<PrestamoMovimientoModel> listModel = new ArrayList<>();
        for (Movimiento entity : listEntity) {
            listModel.add(movimientoToPrestamoMovimientoModel(entity));
        }
        return listModel;
    }

    public static Prestamo prestamoNewToPrestamoEntity(PrestamoRequestNew request, Long usuarioId) {
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(request.getEntidadFinancieraId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Prestamo entity = new Prestamo();
        entity.setNumeroComprobante(request.getNumeroComprobante());
        entity.setMontoPrestamo(request.getMontoPrestamo());
        entity.setFechaDesembolso(request.getFechaDesembolso());
        entity.setFechaVencimiento(request.getFechaVencimiento());
        entity.setInteres(request.getInteres());
        entity.setTasa(request.getTasa());
        entity.setCantidadCuotas(request.getCantidadCuotas());
        entity.setCantidadCuotasPagadas(request.getCantidadCuotasPagadas());
        entity.setMontoCuota(request.getMontoCuota());
        entity.setMontoPagado(request.getMontoPagado());
        entity.setDestinoPrestamo(request.getDestinoPrestamo());
        entity.setEstado(request.getEstado());
        entity.setMonedaId(moneda);
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setUsuarioId(usuario);
        entity.setSaldoCuota(request.getMontoCuota());
        entity.setFechaProxVencimiento(DateUtil.sumarDiasAFecha(request.getFechaDesembolso(), 30));
        entity.setSiguienteCuota(1);
        entity.setMontoMoraTotal(0.0);
        return entity;
    }

    public static Prestamo prestamoUpdateToPrestamoEntity(PrestamoRequestUpdate request, Long usuarioId) {
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(request.getEntidadFinancieraId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Prestamo entity = new Prestamo();
        entity.setId(request.getId());
        entity.setNumeroComprobante(request.getNumeroComprobante());
        entity.setMontoPrestamo(request.getMontoPrestamo());
        entity.setFechaDesembolso(request.getFechaDesembolso());
        entity.setFechaVencimiento(request.getFechaVencimiento());
        entity.setInteres(request.getInteres());
        entity.setTasa(request.getTasa());
        entity.setCantidadCuotas(request.getCantidadCuotas());
        entity.setCantidadCuotasPagadas(request.getCantidadCuotasPagadas());
        entity.setMontoCuota(request.getMontoCuota());
        entity.setMontoPagado(request.getMontoPagado());
        entity.setDestinoPrestamo(request.getDestinoPrestamo());
        entity.setEstado(request.getEstado());
        entity.setMonedaId(moneda);
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Prestamo modelToEntity(PrestamoModel model) {
        Moneda moneda = new Moneda();
        moneda.setId(model.getMonedaId());
        moneda.setNombre(model.getMonedaNombre());
        moneda.setCodigo(model.getMonedaCodigo());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(model.getEntidadFinancieraId());
        entidadFinanciera.setNombre(model.getEntidadFinancieraNombre());
        Prestamo entity = new Prestamo();
        entity.setId(model.getId());
        entity.setNumeroComprobante(model.getNumeroComprobante());
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
        return entity;
    }

    public static PrestamoModel entityToModel(Prestamo entity) {
        PrestamoModel model = new PrestamoModel();
        model.setId(entity.getId());
        model.setNumeroComprobante(entity.getNumeroComprobante());
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
        model.setSaldo(entity.getMontoPrestamo() - entity.getMontoPagado());
        model.setFechaProxVencimiento(entity.getFechaProxVencimiento());
        model.setSaldoCuota(entity.getSaldoCuota());
        model.setSiguienteCuota(entity.getSiguienteCuota());
        model.setMontoMoraTotal(entity.getMontoMoraTotal());
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
