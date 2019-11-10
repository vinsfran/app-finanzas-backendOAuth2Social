package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.*;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaModel;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaMovimientoModel;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestUpdate;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("tarjetaConverter")
public class TarjetaConverter {

    private static final Log LOG = LogFactory.getLog(TarjetaConverter.class);

    public static TarjetaMovimientoModel movimientoToTarjetaMovimientoModel(Movimiento movimiento) {
        TarjetaMovimientoModel tarjetaMovimientoModel = new TarjetaMovimientoModel();
        tarjetaMovimientoModel.setMovimientoId(movimiento.getId());
        tarjetaMovimientoModel.setNumeroComprobante(movimiento.getNumeroComprobante());
        tarjetaMovimientoModel.setFechaMovimiento(movimiento.getFechaMovimiento());
        tarjetaMovimientoModel.setMonto(movimiento.getMonto());
        tarjetaMovimientoModel.setTipoMovimiento("INGRESO");
        if (movimiento.getSigno().equals("-")) {
            tarjetaMovimientoModel.setTipoMovimiento("EGRESO");
        }
        tarjetaMovimientoModel.setMoneda(movimiento.getMonedaId().getNombre());
        return tarjetaMovimientoModel;
    }

    public static List<TarjetaMovimientoModel> listMovimientosToListTarjetaMovimientoModel(List<Movimiento> listEntity) {
        List<TarjetaMovimientoModel> listModel = new ArrayList<>();
        for (Movimiento entity : listEntity) {
            listModel.add(movimientoToTarjetaMovimientoModel(entity));
        }
        return listModel;
    }

    public static Tarjeta tarjetaNewToTarjetaEntity(TarjetaRequestNew request, Long usuarioId) {
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(request.getEntidadFinancieraId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Tarjeta entity = new Tarjeta();
        entity.setMontoDisponible(request.getMontoDisponible());
        entity.setNumeroTarjeta(request.getNumeroTarjeta());
        entity.setMarca(request.getMarca());
        entity.setLineaCredito(request.getLineaCredito());
        entity.setFechaVencimiento(request.getFechaVencimiento());
        entity.setEstado(request.getEstado());
        entity.setMonedaId(moneda);
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Tarjeta tarjetaUpdateToTarjetaEntity(TarjetaRequestUpdate request, Long usuarioId) {
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(request.getEntidadFinancieraId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Tarjeta entity = new Tarjeta();
        entity.setId(request.getId());
        entity.setMontoDisponible(request.getMontoDisponible());
        entity.setNumeroTarjeta(request.getNumeroTarjeta());
        entity.setMarca(request.getMarca());
        entity.setLineaCredito(request.getLineaCredito());
        entity.setFechaVencimiento(request.getFechaVencimiento());
        entity.setEstado(request.getEstado());
        entity.setMonedaId(moneda);
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Tarjeta modelToEntity(TarjetaModel model) {
        Moneda moneda = new Moneda();
        moneda.setId(model.getMonedaId());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(model.getEntidadFinancieraId());
        entidadFinanciera.setNombre(model.getEntidadFinancieraNombre());
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        Tarjeta entity = new Tarjeta();
        entity.setId(model.getId());
        entity.setMontoDisponible(model.getMontoDisponible());
        entity.setNumeroTarjeta(model.getNumeroTarjeta());
        entity.setMarca(model.getMarca());
        entity.setLineaCredito(model.getLineaCredito());
        entity.setFechaVencimiento(model.getFechaVencimiento());
        entity.setEstado(model.getEstado());
        entity.setMonedaId(moneda);
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
        model.setMontoDisponible(entity.getMontoDisponible());
        model.setFechaVencimiento(entity.getFechaVencimiento());
        model.setEstado(entity.getEstado());
        model.setMonedaId(entity.getMonedaId().getId());
        model.setMonedaNombre(entity.getMonedaId().getNombre());
        model.setMonedaCodigo(entity.getMonedaId().getCodigo());
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
