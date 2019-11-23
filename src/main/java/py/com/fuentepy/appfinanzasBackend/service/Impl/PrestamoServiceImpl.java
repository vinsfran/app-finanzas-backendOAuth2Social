package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.PrestamoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.PrestamoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.prestamo.*;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;
import py.com.fuentepy.appfinanzasBackend.service.PrestamoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private static final Log LOG = LogFactory.getLog(PrestamoServiceImpl.class);

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private MovimientoService movimientoService;

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoModel> findAll() {
        return PrestamoConverter.listEntitytoListModel(prestamoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return PrestamoConverter.listEntitytoListModel(prestamoRepository.findByUsuarioIdOrderByIdDesc(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrestamoModel> findAll(Pageable pageable) {
        return PrestamoConverter.pageEntitytoPageModel(pageable, prestamoRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrestamoModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return PrestamoConverter.pageEntitytoPageModel(pageable, prestamoRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public PrestamoModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        PrestamoModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Prestamo> optional = prestamoRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = PrestamoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(PrestamoRequestNew request, Long usuarioId) {
        boolean retorno = false;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Prestamo entity = prestamoRepository.saveAndFlush(PrestamoConverter.prestamoNewToPrestamoEntity(request, usuarioId));
        if (entity != null) {
            Movimiento movimiento = new Movimiento();
            movimiento.setNumeroComprobante(entity.getNumeroComprobante());
            movimiento.setFechaMovimiento(new Date());
            movimiento.setMonto(entity.getMontoPrestamo());
            movimiento.setSigno("+");
            movimiento.setDetalle("Cobro: Prestamo: " + entity.getDestinoPrestamo() + " - " + entity.getEntidadFinancieraId().getNombre());
            movimiento.setTablaId(entity.getId());
            movimiento.setTablaNombre(ConstantUtil.PRESTAMOS);
            movimiento.setMonedaId(entity.getMonedaId());
            movimiento.setUsuarioId(entity.getUsuarioId());
            movimientoService.registrarMovimiento(movimiento, request.getArchivoModels());
            retorno = true;
        }
        return retorno;
    }

    @Override
    @Transactional
    public boolean update(PrestamoRequestUpdate request, Long usuarioId) {
        Prestamo entity = prestamoRepository.save(PrestamoConverter.prestamoUpdateToPrestamoEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean pagar(PrestamoRequestPago request, Long usuarioId) {
        boolean retorno = false;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Prestamo> optional = prestamoRepository.findByIdAndUsuarioId(request.getId(), usuario);
        if (optional.isPresent()) {
            Prestamo entity = optional.get();
            if (entity.getEstado()) {
                entity.setCantidadCuotasPagadas(request.getNumeroCuota());
                entity.setMontoUltimoPago(request.getMontoPagado());
                entity.setMontoPagado(entity.getMontoPagado() + entity.getMontoUltimoPago());
                prestamoRepository.save(entity);
                Movimiento movimiento = new Movimiento();
                movimiento.setNumeroComprobante(request.getNumeroComprobante());
                movimiento.setFechaMovimiento(new Date());
                movimiento.setMonto(request.getMontoPagado());
                movimiento.setNumeroCuota(request.getNumeroCuota());
                movimiento.setSigno("-");
                movimiento.setDetalle("Pago: Prestamo: " + entity.getDestinoPrestamo() + ", Cuota: " + entity.getCantidadCuotasPagadas() + " - " + entity.getEntidadFinancieraId().getNombre());
                movimiento.setTablaId(entity.getId());
                movimiento.setTablaNombre(ConstantUtil.PRESTAMOS);
                movimiento.setMonedaId(entity.getMonedaId());
                movimiento.setUsuarioId(entity.getUsuarioId());
                movimientoService.registrarMovimiento(movimiento, request.getArchivoModels());
                retorno = true;
            }
        }
        return retorno;
    }

    @Override
    @Transactional
    public PrestamoModel save(PrestamoModel prestamoModel) {
        Prestamo entity = PrestamoConverter.modelToEntity(prestamoModel);
        return PrestamoConverter.entityToModel(prestamoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByTenantName(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return prestamoRepository.countByUsuarioId(usuario);
    }

    @Override
    public List<Prestamo> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return prestamoRepository.findByUsuarioIdRangoFecha(usuario, fechaInicio, fechaFin);
    }

    @Override
    public List<PrestamoMovimientoModel> findByUsuarioAndPrestamoId(Long usuarioId, Long prestamoId) {
        return PrestamoConverter.listMovimientosToListPrestamoMovimientoModel(movimientoService.findByUsuarioIdAndTablaIdAndTablaNombre(usuarioId, prestamoId, ConstantUtil.PRESTAMOS));
    }

    @Override
    @Transactional
    public void delete(Long usuarioId, Long PrestamoId) throws Exception {
        try {
            movimientoService.deleteMovimientos(PrestamoId, ConstantUtil.PRESTAMOS, usuarioId);
            prestamoRepository.deleteById(PrestamoId);
        } catch (Exception e) {
            throw new Exception("No se pudo eliminar el Prestamo! " + e.getMessage());
        }
    }
}
