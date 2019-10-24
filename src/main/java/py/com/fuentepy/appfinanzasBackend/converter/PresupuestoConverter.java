package py.com.fuentepy.appfinanzasBackend.converter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import py.com.fuentepy.appfinanzasBackend.data.entity.Mes;
import py.com.fuentepy.appfinanzasBackend.data.entity.Moneda;
import py.com.fuentepy.appfinanzasBackend.data.entity.Presupuesto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.presupuesto.PresupuestoModel;
import py.com.fuentepy.appfinanzasBackend.resource.presupuesto.PresupuestoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.presupuesto.PresupuestoRequestUpdate;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@Component("presupuestoConverter")
public class PresupuestoConverter {

    private static final Log LOG = LogFactory.getLog(PresupuestoConverter.class);

    public static Presupuesto presupuestoNewToPresupuestoEntity(PresupuestoRequestNew request, Long usuarioId) {
        Mes mes = new Mes();
        mes.setId(request.getMesId());
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Presupuesto entity = new Presupuesto();
        entity.setFechaAlta(request.getFechaAlta());
        entity.setMonto(request.getMonto());
        entity.setAnio(request.getAnio());
        entity.setPorcentajeAlerta(request.getPorcentajeAlerta());
        entity.setMesId(mes);
        entity.setMonedaId(moneda);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Presupuesto presupuestoUpdateToPresupuestoEntity(PresupuestoRequestUpdate request, Long usuarioId) {
        Mes mes = new Mes();
        mes.setId(request.getMesId());
        Moneda moneda = new Moneda();
        moneda.setId(request.getMonedaId());
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Presupuesto entity = new Presupuesto();
        entity.setId(request.getId());
        entity.setFechaAlta(request.getFechaAlta());
        entity.setMonto(request.getMonto());
        entity.setAnio(request.getAnio());
        entity.setPorcentajeAlerta(request.getPorcentajeAlerta());
        entity.setMesId(mes);
        entity.setMonedaId(moneda);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static Presupuesto modelToEntity(PresupuestoModel model) {
        Mes mes = new Mes();
        mes.setId(model.getMesId());
        mes.setNombre(model.getMesNombre());
        mes.setNumero(model.getMesNumero());
        Moneda moneda = new Moneda();
        moneda.setId(model.getMonedaId());
        moneda.setNombre(model.getMonedaNombre());
        moneda.setCodigo(model.getMonedaCodigo());
        Usuario usuario = new Usuario();
        usuario.setId(model.getUsuarioId());
        Presupuesto entity = new Presupuesto();
        entity.setId(model.getId());
        entity.setFechaAlta(model.getFechaAlta());
        entity.setMonto(model.getMonto());
        entity.setAnio(model.getAnio());
        entity.setPorcentajeAlerta(model.getPorcentajeAlerta());
        entity.setMesId(mes);
        entity.setMonedaId(moneda);
        entity.setUsuarioId(usuario);
        return entity;
    }

    public static PresupuestoModel entityToModel(Presupuesto entity) {
        PresupuestoModel model = new PresupuestoModel();
        model.setId(entity.getId());
        model.setFechaAlta(entity.getFechaAlta());
        model.setMonto(entity.getMonto());
        model.setAnio(entity.getAnio());
        model.setPorcentajeAlerta(entity.getPorcentajeAlerta());
        model.setMesId(entity.getMesId().getId());
        model.setMesNombre(entity.getMesId().getNombre());
        model.setMesNumero(entity.getMesId().getNumero());
        model.setMonedaId(entity.getMonedaId().getId());
        model.setMonedaNombre(entity.getMonedaId().getNombre());
        model.setMonedaCodigo(entity.getMonedaId().getCodigo());
        model.setUsuarioId(entity.getUsuarioId().getId());
        return model;
    }

    public static List<PresupuestoModel> listEntitytoListModel(List<Presupuesto> listEntity) {
        List<PresupuestoModel> listModel = new ArrayList<>();
        for (Presupuesto entity : listEntity) {
            listModel.add(entityToModel(entity));
        }
        return listModel;
    }

    static List<PresupuestoModel> mapEntitiesIntoDTOs(Iterable<Presupuesto> entities) {
        List<PresupuestoModel> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(entityToModel(e)));
        return dtos;
    }

    public static Page<PresupuestoModel> pageEntitytoPageModel(Pageable pageable, Page<Presupuesto> pageEntity) {
        List<PresupuestoModel> models = mapEntitiesIntoDTOs(pageEntity.getContent());
        return new PageImpl<>(models, pageable, pageEntity.getTotalElements());
    }
}
