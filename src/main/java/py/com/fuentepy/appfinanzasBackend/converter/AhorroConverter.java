package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.*;
import py.com.fuentepy.appfinanzasBackend.resource.ahorro.AhorroModel;
import py.com.fuentepy.appfinanzasBackend.resource.ahorro.AhorroRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.ahorro.AhorroRequestUpdate;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("ahorroConverter")
public class AhorroConverter {

    private static final Log LOG = LogFactory.getLog(AhorroConverter.class);

    public static Ahorro ahorroNewToAhorroEntity(AhorroRequestNew request, Long usuarioId) {
        TipoAhorro tipoAhorro = new TipoAhorro();
        tipoAhorro.setId(request.getAhorroTipoId());
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(request.getEntidadFinancieraId());
        TipoCobro tipoCobro = new TipoCobro();
        tipoCobro.setId(request.getTipoCobroId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Ahorro entity = new Ahorro();
        entity.setNumeroComprobante(request.getNumeroComprobante());
        entity.setMontoCapital(request.getMontoCapital());
        entity.setFechaInicio(request.getFechaInicio());
        entity.setFechaVencimiento(request.getFechaVencimiento());
        entity.setPlazoTotal(request.getPlazoTotal());
        entity.setMontoCuota(request.getMontoCuota());
        entity.setInteres(request.getInteres());
        entity.setTasa(request.getTasa());
        entity.setCantidadCuotas(request.getCantidadCuotas());
        entity.setCantidadCuotasPagadas(request.getCantidadCuotasPagadas());
        entity.setMontoInteresCuota(request.getMontoInteresCuota());
        entity.setCantidadCobro(request.getCantidadCobro());
        entity.setEstado(request.getEstado());
        entity.setTipoAhorroId(tipoAhorro);
        entity.setMonedaId(moneda);
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setTipoCobroId(tipoCobro);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Ahorro ahorroUpdateToAhorroEntity(AhorroRequestUpdate request, Long usuarioId) {
        TipoAhorro tipoAhorro = new TipoAhorro();
        tipoAhorro.setId(request.getAhorroTipoId());
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(request.getEntidadFinancieraId());
        TipoCobro tipoCobro = new TipoCobro();
        tipoCobro.setId(request.getTipoCobroId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Ahorro entity = new Ahorro();
        entity.setId(request.getId());
        entity.setNumeroComprobante(request.getNumeroComprobante());
        entity.setMontoCapital(request.getMontoCapital());
        entity.setFechaInicio(request.getFechaInicio());
        entity.setFechaVencimiento(request.getFechaVencimiento());
        entity.setPlazoTotal(request.getPlazoTotal());
        entity.setMontoCuota(request.getMontoCuota());
        entity.setInteres(request.getInteres());
        entity.setTasa(request.getTasa());
        entity.setCantidadCuotas(request.getCantidadCuotas());
        entity.setCantidadCuotasPagadas(request.getCantidadCuotasPagadas());
        entity.setMontoInteresCuota(request.getMontoInteresCuota());
        entity.setCantidadCobro(request.getCantidadCobro());
        entity.setEstado(request.getEstado());
        entity.setTipoAhorroId(tipoAhorro);
        entity.setMonedaId(moneda);
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setTipoCobroId(tipoCobro);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Ahorro modelToEntity(AhorroModel model) {
        TipoAhorro tipoAhorro = new TipoAhorro();
        tipoAhorro.setId(model.getAhorroTipoId());
        tipoAhorro.setNombre(model.getTipoAhorroNombre());
        Moneda moneda = new Moneda();
        moneda.setId(model.getMonedaId());
        moneda.setNombre(model.getMonedaNombre());
        moneda.setCodigo(model.getMonedaCodigo());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(model.getEntidadFinancieraId());
        entidadFinanciera.setNombre(model.getEntidadFinancieraNombre());
        TipoCobro tipoCobro = new TipoCobro();
        tipoCobro.setId(model.getTipoCobroId());
        tipoCobro.setNombre(model.getTipoAhorroNombre());
        Ahorro entity = new Ahorro();
        entity.setId(model.getId());
        entity.setNumeroComprobante(model.getNumeroComprobante());
        entity.setMontoCapital(model.getMontoCapital());
        entity.setFechaInicio(model.getFechaInicio());
        entity.setFechaVencimiento(model.getFechaVencimiento());
        entity.setPlazoTotal(model.getPlazoTotal());
        entity.setMontoCuota(model.getMontoCuota());
        entity.setInteres(model.getInteres());
        entity.setTasa(model.getTasa());
        entity.setCantidadCuotas(model.getCantidadCuotas());
        entity.setCantidadCuotasPagadas(model.getCantidadCuotasPagadas());
        entity.setMontoInteresCuota(model.getMontoInteresCuota());
        entity.setCantidadCobro(model.getCantidadCobro());
        entity.setEstado(model.getEstado());
        entity.setTipoAhorroId(tipoAhorro);
        entity.setMonedaId(moneda);
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setTipoCobroId(tipoCobro);
        return entity;
    }

    public static AhorroModel entityToModel(Ahorro entity) {
        AhorroModel model = new AhorroModel();
        model.setId(entity.getId());
        model.setNumeroComprobante(entity.getNumeroComprobante());
        model.setMontoCapital(entity.getMontoCapital());
        model.setFechaInicio(entity.getFechaInicio());
        model.setFechaVencimiento(entity.getFechaVencimiento());
        model.setPlazoTotal(entity.getPlazoTotal());
        model.setMontoCuota(entity.getMontoCuota());
        model.setInteres(entity.getInteres());
        model.setTasa(entity.getTasa());
        model.setCantidadCuotas(entity.getCantidadCuotas());
        model.setCantidadCuotasPagadas(entity.getCantidadCuotasPagadas());
        model.setMontoInteresCuota(entity.getMontoInteresCuota());
        model.setCantidadCobro(entity.getCantidadCobro());
        model.setEstado(entity.getEstado());
        model.setAhorroTipoId(entity.getTipoAhorroId().getId());
        model.setTipoAhorroNombre(entity.getTipoAhorroId().getNombre());
        model.setMonedaId(entity.getMonedaId().getId());
        model.setMonedaNombre(entity.getMonedaId().getNombre());
        model.setMonedaCodigo(entity.getMonedaId().getCodigo());
        model.setEntidadFinancieraId(entity.getEntidadFinancieraId().getId());
        model.setEntidadFinancieraNombre(entity.getEntidadFinancieraId().getNombre());
        model.setTipoCobroId(entity.getTipoCobroId().getId());
        model.setTipoCobroNombre(entity.getTipoCobroId().getNombre());
        return model;
    }

    public static List<AhorroModel> listEntitytoListModel(List<Ahorro> listEntity) {
        List<AhorroModel> listModel = new ArrayList<>();
        for (Ahorro entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<AhorroModel> mapEntitiesIntoDTOs(Iterable<Ahorro> entities) {
        List<AhorroModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<AhorroModel> pageEntitytoPageModel(Pageable pageable, Page<Ahorro> pageEntity) {
        List<AhorroModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
