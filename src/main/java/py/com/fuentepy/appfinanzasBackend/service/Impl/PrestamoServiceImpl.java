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
import py.com.fuentepy.appfinanzasBackend.resource.movimiento.MovimientoModel;
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
    public boolean create(PrestamoRequestNew request, Long usuarioId) {
        boolean retorno = false;
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
                prestamo.setMontoMoraTotal(prestamo.getMontoMoraTotal() + request.getMontoMora());
                prestamoRepository.save(prestamo);
                Movimiento movimiento = new Movimiento();
                movimiento.setNumeroComprobante(request.getNumeroComprobante());
                movimiento.setFechaMovimiento(request.getFechaMovimiento());
                movimiento.setMonto(request.getMontoPagado());
                movimiento.setNumeroCuota(request.getNumeroCuota());
                movimiento.setSigno("-");
                movimiento.setDetalle("Pago Prestamo: " + prestamo.getDestinoPrestamo());
                movimiento.setTablaId(prestamo.getId());
                movimiento.setTablaNombre(ConstantUtil.PRESTAMOS);
                movimiento.setMonedaId(prestamo.getMonedaId());
                movimiento.setUsuarioId(prestamo.getUsuarioId());
                retorno = movimientoService.registrarMovimiento(movimiento);

                List<PrestamoCuotera> prestamoCuoteraList = prestamoCuoteraRepository.findByPrestamoIdAndUsuarioIdAndEstadoCuotaOrderByNumeroCuota(prestamo, usuario, ConstantUtil.CUOTA_PENDIENTE);
                Double montoPago = request.getMontoPagado();
                Double montoPagoReal = 0.0;
                boolean salir = false;
                PrestamoCuotera ultimoElementoRecorrido = new PrestamoCuotera();
                int cont = prestamoCuoteraList.size();
                for (int i = 0; i < prestamoCuoteraList.size(); i++) {
                    cont--;
                    PrestamoCuotera prestamoCuotera = prestamoCuoteraList.get(i);
                    PrestamoPago prestamoPago = new PrestamoPago();
                    prestamo.setFechaProxVencimiento(prestamoCuotera.getFechaVencimiento());
                    prestamo.setSaldoCuota(prestamoCuotera.getSaldoCuota());
                    prestamo.setSiguienteCuota(prestamoCuotera.getNumeroCuota());
                    if (request.getNumeroCuota().equals(prestamoCuotera.getNumeroCuota())) {
                        prestamoPago.setMontoMora(request.getMontoMora());
                    }
                    if (montoPago > prestamoCuotera.getSaldoCuota() || montoPago.equals(prestamoCuotera.getSaldoCuota())) {
                        montoPago = montoPago - prestamoCuotera.getSaldoCuota();
                        prestamoPago.setMontoPago(prestamoCuotera.getSaldoCuota());
                        prestamoCuotera.setSaldoCuota(0.0);
                        prestamoCuotera.setEstadoCuota(ConstantUtil.CUOTA_PAGADA);
                        if (montoPago == 0.0) {
                            salir = true;
                        }
                        if (0 < cont) {
                            prestamo.setFechaProxVencimiento(prestamoCuoteraList.get(i + 1).getFechaVencimiento());
                            prestamo.setSaldoCuota(prestamoCuoteraList.get(i + 1).getSaldoCuota());
                            prestamo.setSiguienteCuota(prestamoCuoteraList.get(i + 1).getNumeroCuota());
                        }
                    } else {
                        prestamoPago.setMontoPago(montoPago);
                        prestamoCuotera.setSaldoCuota(prestamoCuotera.getSaldoCuota() - montoPago);
                        salir = true;
                    }
                    prestamo.setCantidadCuotasPagadas(prestamoCuotera.getNumeroCuota());
                    montoPagoReal = montoPagoReal + prestamoPago.getMontoPago();
                    prestamoPago.setNumeroCuota(prestamoCuotera.getNumeroCuota());
                    prestamoPago.setPrestamoId(prestamo);
                    prestamoPago.setMovimientoId(retorno);
                    prestamoPago.setUsuarioId(usuario);
                    prestamoPago.setFechaPago(retorno.getFechaMovimiento());
                    prestamoPagoRepository.save(prestamoPago);
                    ultimoElementoRecorrido = prestamoCuoteraRepository.saveAndFlush(prestamoCuotera);

                    if (salir) {
                        prestamoRepository.save(prestamo);
                        break;
                    }
                }
                if (ultimoElementoRecorrido.getNumeroCuota().equals(prestamoCuoteraList.get(prestamoCuoteraList.size() - 1).getNumeroCuota()) && ultimoElementoRecorrido.getSaldoCuota().equals(0.0)) {
                    prestamo.setEstado(false);
                    prestamoRepository.save(prestamo);
                    retorno.setMonto(montoPagoReal);
                    retorno.setNumeroCuota(ultimoElementoRecorrido.getNumeroCuota());
                    retorno = movimientoService.registrarMovimiento(retorno);
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
    public void delete(Long usuarioId, Long prestamoId) throws Exception {
        try {
            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            Prestamo prestamo = new Prestamo();
            prestamo.setId(prestamoId);
            List<PrestamoPago> prestamoPagoList = prestamoPagoRepository.findByPrestamoIdAndUsuarioId(prestamo, usuario);
            for (PrestamoPago prestamoPago : prestamoPagoList) {
                prestamoPagoRepository.delete(prestamoPago);
            }
            List<PrestamoCuotera> prestamoCuoteraList = prestamoCuoteraRepository.findByPrestamoIdAndUsuarioId(prestamo, usuario);
            for (PrestamoCuotera prestamoCuotera : prestamoCuoteraList) {
                prestamoCuoteraRepository.delete(prestamoCuotera);
            }
            movimientoService.deleteMovimientos(prestamoId, ConstantUtil.PRESTAMOS, usuarioId);
            prestamoRepository.deleteById(prestamoId);
        } catch (Exception e) {
            throw new Exception("No se pudo eliminar el Prestamo! " + e.getMessage());
        }
    }

    @Override
    public void deleteMovimiento(Long usuarioId, Long prestamoId, Long movimientoId) throws Exception {
        try {
            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            Optional<Prestamo> optional = prestamoRepository.findByIdAndUsuarioId(prestamoId, usuario);
            if (optional.isPresent()) {
                Prestamo prestamo = optional.get();
                MovimientoModel movimientoModel = movimientoService.findByIdAndUsuarioId(movimientoId, usuarioId);
                Movimiento movimiento = new Movimiento();
                movimiento.setId(movimientoId);
                List<PrestamoPago> prestamoPagoList = prestamoPagoRepository.findByMovimientoIdAndUsuarioId(movimiento, usuario);
                int cantidaCuotasRevertir = 0;
                int cuotaPagoContador = 0;
                for (PrestamoPago prestamoPago : prestamoPagoList) {
                    if (!prestamoPago.getNumeroCuota().equals(cuotaPagoContador)) {
                        cuotaPagoContador = prestamoPago.getNumeroCuota();
                        cantidaCuotasRevertir++;
                    }
                    PrestamoCuotera prestamoCuotera = prestamoCuoteraRepository.findByNumeroCuotaAndPrestamoIdAndUsuarioId(prestamoPago.getNumeroCuota(), prestamo, usuario);
                    prestamoCuotera.setEstadoCuota(ConstantUtil.CUOTA_PENDIENTE);
                    prestamoCuotera.setSaldoCuota(prestamoCuotera.getSaldoCuota() + prestamoPago.getMontoPago());
                    prestamoPagoRepository.delete(prestamoPago);
                }

                prestamo.setMontoPagado(prestamo.getMontoPagado() - movimientoModel.getMonto());
                cantidaCuotasRevertir = prestamo.getCantidadCuotasPagadas() - cantidaCuotasRevertir;
                if (cantidaCuotasRevertir < 0) {
                    cantidaCuotasRevertir = 0;
                }
                prestamo.setCantidadCuotasPagadas(prestamo.getCantidadCuotasPagadas() - cantidaCuotasRevertir);
                prestamo.setSiguienteCuota(prestamo.getCantidadCuotasPagadas() + 1);
                prestamoRepository.save(prestamo);
                movimientoService.deleteMovimiento(usuarioId, movimientoId);
            } else {
                throw new Exception("No existe ahorro!");
            }
        } catch (Exception e) {
            throw new Exception("No se pudo eliminar el Movimiento! " + e.getMessage());
        }
    }
}
