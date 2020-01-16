package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.PrestamoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.*;
import py.com.fuentepy.appfinanzasBackend.data.repository.PrestamoCuoteraRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.PrestamoPagoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.PrestamoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.prestamo.*;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;
import py.com.fuentepy.appfinanzasBackend.service.PrestamoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;
import py.com.fuentepy.appfinanzasBackend.util.DateUtil;

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

    @Autowired
    private PrestamoCuoteraRepository prestamoCuoteraRepository;

    @Autowired
    private PrestamoPagoRepository prestamoPagoRepository;

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
    public Movimiento create(PrestamoRequestNew request, Long usuarioId) {

        //SACAR REGISTRO DE MOVIMIENTOS
        Movimiento retorno = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Prestamo prestamo = prestamoRepository.saveAndFlush(PrestamoConverter.prestamoNewToPrestamoEntity(request, usuarioId));
        if (prestamo != null) {

            int totDias = 30;
            for (int i = 0; i < request.getCantidadCuotas(); i++) {
                PrestamoCuotera prestamoCuotera = new PrestamoCuotera();
                prestamoCuotera.setPrestamoId(prestamo);
                prestamoCuotera.setNumeroCuota(i + 1);
                prestamoCuotera.setMontoCuota(prestamo.getMontoCuota());
                prestamoCuotera.setSaldoCuota(prestamo.getMontoCuota());
                prestamoCuotera.setFechaVencimiento(DateUtil.sumarDiasAFecha(prestamo.getFechaDesembolso(), totDias));
                prestamoCuotera.setEstadoCuota(ConstantUtil.CUOTA_PENDIENTE);
                prestamoCuotera.setUsuarioId(prestamo.getUsuarioId());
                totDias = totDias + 30;
                prestamoCuoteraRepository.save(prestamoCuotera);
            }

            Movimiento movimiento = new Movimiento();
            movimiento.setNumeroComprobante(prestamo.getNumeroComprobante());
            movimiento.setFechaMovimiento(new Date());
            movimiento.setMonto(prestamo.getMontoPrestamo());
            movimiento.setSigno("+");
            movimiento.setDetalle("Cobro: Prestamo: " + prestamo.getDestinoPrestamo() + " - " + prestamo.getEntidadFinancieraId().getNombre());
            movimiento.setTablaId(prestamo.getId());
            movimiento.setTablaNombre(ConstantUtil.PRESTAMOS);
            movimiento.setMonedaId(prestamo.getMonedaId());
            movimiento.setUsuarioId(prestamo.getUsuarioId());
            retorno = movimientoService.registrarMovimiento(movimiento);
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
    public Movimiento pagar(PrestamoRequestPago request, Long usuarioId) {
        Movimiento retorno = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Prestamo> optional = prestamoRepository.findByIdAndUsuarioId(request.getId(), usuario);
        if (optional.isPresent()) {
            Prestamo prestamo = optional.get();
            if (prestamo.getEstado()) {
                prestamo.setCantidadCuotasPagadas(request.getNumeroCuota());
                prestamo.setMontoUltimoPago(request.getMontoPagado());
                prestamo.setMontoPagado(prestamo.getMontoPagado() + prestamo.getMontoUltimoPago());
                prestamoRepository.save(prestamo);
                Movimiento movimiento = new Movimiento();
                movimiento.setNumeroComprobante(request.getNumeroComprobante());
                movimiento.setFechaMovimiento(new Date());
                movimiento.setMonto(request.getMontoPagado());
                movimiento.setNumeroCuota(request.getNumeroCuota());
                movimiento.setSigno("-");
                movimiento.setDetalle("Pago: Prestamo: " + prestamo.getDestinoPrestamo() + ", Cuota: " + prestamo.getCantidadCuotasPagadas() + " - " + prestamo.getEntidadFinancieraId().getNombre());
                movimiento.setTablaId(prestamo.getId());
                movimiento.setTablaNombre(ConstantUtil.PRESTAMOS);
                movimiento.setMonedaId(prestamo.getMonedaId());
                movimiento.setUsuarioId(prestamo.getUsuarioId());
                retorno = movimientoService.registrarMovimiento(movimiento);

                List<PrestamoCuotera> prestamoCuoteraList = prestamoCuoteraRepository.findByPrestamoIdAndUsuarioIdAndEstadoCuota(prestamo, usuario, ConstantUtil.CUOTA_PENDIENTE);
                Double montoPago = request.getMontoPagado();
                boolean salir = true;
                LOG.info(prestamoCuoteraList.size());
                int i = 0;
                while (salir) {
                    PrestamoCuotera prestamoCuotera = prestamoCuoteraList.get(i);
                    LOG.info(prestamoCuotera.getNumeroCuota());
                    PrestamoPago prestamoPago = new PrestamoPago();
                    if (montoPago > prestamoCuotera.getSaldoCuota() || montoPago.equals(prestamoCuotera.getSaldoCuota())) {
                        montoPago = montoPago - prestamoCuotera.getSaldoCuota();
                        prestamoCuotera.setSaldoCuota(prestamoCuotera.getSaldoCuota());
                        prestamoCuotera.setEstadoCuota(ConstantUtil.CUOTA_PAGADA);
                    } else {
                        prestamoCuotera.setSaldoCuota(prestamoCuotera.getSaldoCuota() - montoPago);
                        salir = false;
                    }
                    i++;
                    prestamoPago.setMontoPago(prestamoCuotera.getSaldoCuota());
                    prestamoPago.setNumeroCuota(prestamoCuotera.getNumeroCuota());
                    prestamoPago.setPrestamoId(prestamo);
                    prestamoPago.setMovimientoId(retorno);
                    prestamoPago.setUsuarioId(usuario);
                    prestamoPago.setFechaPago(retorno.getFechaMovimiento());
                    prestamoPagoRepository.save(prestamoPago);
                    prestamoCuoteraRepository.save(prestamoCuotera);

                }
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
    @Transactional(readOnly = true)
    public List<Prestamo> findByUsuarioAndEstado(Long usuarioId, boolean estado) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return prestamoRepository.findByUsuarioIdAndEstado(usuario, estado);
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
