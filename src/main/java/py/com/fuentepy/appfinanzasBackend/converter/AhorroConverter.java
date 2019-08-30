package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.entity.*;
import py.com.fuentepy.appfinanzasBackend.model.AhorroModel;
import py.com.fuentepy.appfinanzasBackend.payload.request.ahorro.AhorroNew;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("ahorroConverter")
public class AhorroConverter {

    private static final Log LOG = LogFactory.getLog(AhorroConverter.class);

    public static Ahorro ahorroNewToAhorroEntity(AhorroNew ahorroNew, Long usuarioId) {
        TipoAhorro tipoAhorro = new TipoAhorro();
        tipoAhorro.setId(ahorroNew.getTipoAhorroId());
        Moneda moneda = new Moneda();
        moneda.setId(ahorroNew.getMonedaId());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(ahorroNew.getEntidadFinancieraId());
        TipoCobro tipoCobro = new TipoCobro();
        tipoCobro.setId(ahorroNew.getTipoAhorroId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Ahorro entity = new Ahorro();
        entity.setMontoCapital(ahorroNew.getMontoCapital());
        entity.setFechaInicio(ahorroNew.getFechaInicio());
        entity.setFechaVencimiento(ahorroNew.getFechaVencimiento());
        entity.setPlazoTotal(ahorroNew.getPlazoTotal());
        entity.setMontoCuota(ahorroNew.getMontoCuota());
        entity.setInteres(ahorroNew.getInteres());
        entity.setTasa(ahorroNew.getTasa());
        entity.setCantidadCuotas(ahorroNew.getCantidadCuotas());
        entity.setCantidadCuotasPagadas(ahorroNew.getCantidadCuotasPagadas());
        entity.setMontoInteresCuota(ahorroNew.getMontoInteresCuota());
        entity.setCantidadCobro(ahorroNew.getCantidadCobro());
        entity.setEstado(ahorroNew.getEstado());
        entity.setTipoAhorroId(tipoAhorro);
        entity.setMonedaId(moneda);
        entity.setEntidadFinancieraId(entidadFinanciera);
        entity.setTipoCobroId(tipoCobro);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Ahorro modelToEntity(AhorroModel model) {
        TipoAhorro tipoAhorro = new TipoAhorro();
        tipoAhorro.setId(model.getTipoAhorroId());
        tipoAhorro.setNombre(model.getTipoAhorroNombre());
        Moneda moneda = new Moneda();
        moneda.setId(model.getMonedaId());
        moneda.setNombre(model.getMonedaNombre());
        moneda.setCodigo(model.getMonedaCodigo());
        EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
        entidadFinanciera.setId(model.getEntidadFinancieraId());
        entidadFinanciera.setNombre(model.getEntidadFinancieraNombre());
        TipoCobro tipoCobro = new TipoCobro();
        tipoCobro.setId(model.getTipoAhorroId());
        tipoCobro.setNombre(model.getTipoAhorroNombre());
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        Ahorro entity = new Ahorro();
        entity.setId(model.getId());
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
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static AhorroModel entityToModel(Ahorro entity) {
        AhorroModel model = new AhorroModel();
        model.setId(entity.getId());
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
        model.setTipoAhorroId(entity.getTipoAhorroId().getId());
        model.setTipoAhorroNombre(entity.getTipoAhorroId().getNombre());
        model.setMonedaId(entity.getMonedaId().getId());
        model.setMonedaNombre(entity.getMonedaId().getNombre());
        model.setMonedaCodigo(entity.getMonedaId().getCodigo());
        model.setEntidadFinancieraId(entity.getEntidadFinancieraId().getId());
        model.setEntidadFinancieraNombre(entity.getEntidadFinancieraId().getNombre());
        model.setTipoCobroId(entity.getTipoCobroId().getId());
        model.setTipoCobroNombre(entity.getTipoCobroId().getNombre());
        model.setUsuarioId(entity.getUsuarioId().getId());
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
