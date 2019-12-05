package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.AhorroConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Ahorro;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.AhorroRepository;
import py.com.fuentepy.appfinanzasBackend.resource.ahorro.*;
import py.com.fuentepy.appfinanzasBackend.service.AhorroService;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AhorroServiceImpl implements AhorroService {

    private static final Log LOG = LogFactory.getLog(AhorroServiceImpl.class);

    @Autowired
    private AhorroRepository ahorroRepository;

    @Autowired
    private MovimientoService movimientoService;


    @Override
    @Transactional(readOnly = true)
    public List<AhorroModel> findAll() {
        return AhorroConverter.listEntityToListModel(ahorroRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AhorroModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return AhorroConverter.listEntityToListModel(ahorroRepository.findByUsuarioIdOrderByIdDesc(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AhorroModel> findAll(Pageable pageable) {
        return AhorroConverter.pageEntityToPageModel(pageable, ahorroRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AhorroModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return AhorroConverter.pageEntityToPageModel(pageable, ahorroRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public AhorroModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        AhorroModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Ahorro> optional = ahorroRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = AhorroConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(AhorroRequestNew request, Long usuarioId) {
        Ahorro entity = ahorroRepository.save(AhorroConverter.ahorroNewToAhorroEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(AhorroRequestUpdate request, Long usuarioId) {
        Ahorro entity = ahorroRepository.save(AhorroConverter.ahorroUpdateToAhorroEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Movimiento pagar(AhorroRequestPago request, Long usuarioId) {
        Movimiento retorno = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Ahorro> optional = ahorroRepository.findByIdAndUsuarioId(request.getId(), usuario);
        if (optional.isPresent()) {
            Ahorro entity = optional.get();
            if (entity.getEstado()) {
                entity.setCantidadCuotasPagadas(request.getNumeroCuota());
                entity.setMontoUltimoPago(request.getMontoPagado());
                entity.setMontoPagado(entity.getMontoPagado() + entity.getMontoUltimoPago());
                ahorroRepository.save(entity);
                Movimiento movimiento = new Movimiento();
                movimiento.setNumeroComprobante(request.getNumeroComprobante());
                movimiento.setFechaMovimiento(new Date());
                movimiento.setMonto(request.getMontoPagado());
                movimiento.setNumeroCuota(request.getNumeroCuota());
                movimiento.setSigno("-");
                movimiento.setDetalle("Pago: Ahorro, Cuota: " + entity.getCantidadCuotasPagadas() + " - " + entity.getEntidadFinancieraId().getNombre());
                movimiento.setTablaId(entity.getId());
                movimiento.setTablaNombre(ConstantUtil.AHORROS);
                movimiento.setMonedaId(entity.getMonedaId());
                movimiento.setUsuarioId(entity.getUsuarioId());
                retorno = movimientoService.registrarMovimiento(movimiento);
            }
        }
        return retorno;
    }

    @Override
    @Transactional
    public Movimiento cobrar(AhorroRequestCobro request, Long usuarioId) {
        Movimiento retorno = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Ahorro> optional = ahorroRepository.findByIdAndUsuarioId(request.getId(), usuario);
        if (optional.isPresent()) {
            Ahorro entity = optional.get();
            if (entity.getEstado()) {
                entity.setCantidadCobro(request.getMontoCobrado());
                entity.setEstado(false);
                entity.setFechaFin(new Date());
                ahorroRepository.save(entity);
                Movimiento movimiento = new Movimiento();
                movimiento.setNumeroComprobante(request.getNumeroComprobante());
                movimiento.setFechaMovimiento(new Date());
                movimiento.setMonto(request.getMontoCobrado());
                movimiento.setSigno("+");
                movimiento.setDetalle("Cobro: Ahorro, Nro: " + entity.getId() + " - " + entity.getEntidadFinancieraId().getNombre());
                movimiento.setTablaId(entity.getId());
                movimiento.setTablaNombre(ConstantUtil.AHORROS);
                movimiento.setMonedaId(entity.getMonedaId());
                movimiento.setUsuarioId(entity.getUsuarioId());
                retorno = movimientoService.registrarMovimiento(movimiento);
            }
        }
        return retorno;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByTenantName(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return ahorroRepository.countByUsuarioId(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ahorro> findByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return ahorroRepository.findByUsuarioIdRangoFecha(usuario, fechaInicio, fechaFin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ahorro> findByUsuarioAndEstado(Long usuarioId, boolean estado) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return ahorroRepository.findByUsuarioIdAndEstado(usuario, estado);
    }

    @Override
    public List<AhorroMovimientoModel> findByUsuarioAndAhorroId(Long usuarioId, Long ahorroId) {
        return AhorroConverter.listMovimientosToListAhorroMovimientoModel(movimientoService.findByUsuarioIdAndTablaIdAndTablaNombre(usuarioId, ahorroId, ConstantUtil.AHORROS));
    }

    @Override
    @Transactional
    public void delete(Long usuarioId, Long AhorroId) throws Exception {
        try {
            movimientoService.deleteMovimientos(AhorroId, ConstantUtil.AHORROS, usuarioId);
            ahorroRepository.deleteById(AhorroId);
        } catch (Exception e) {
            throw new Exception("No se pudo eliminar el Ahorro! " + e.getMessage());
        }
    }

}
