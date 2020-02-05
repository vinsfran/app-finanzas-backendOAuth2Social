package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.MovimientoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.*;
import py.com.fuentepy.appfinanzasBackend.data.repository.MovimientoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.PrestamoCuoteraRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.PrestamoPagoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.PrestamoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.movimiento.MovimientoModel;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private static final Log LOG = LogFactory.getLog(MovimientoServiceImpl.class);

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ArchivoServiceImpl archivoService;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestamoPagoRepository prestamoPagoRepository;

    @Autowired
    private PrestamoCuoteraRepository prestamoCuoteraRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoModel> findAll() {
        return MovimientoConverter.listEntitytoListModel(movimientoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoModel> findByUsuarioIdAndParentAndFechaDesdeAndFechaHasta(Long usuarioId, String parent, Date fechaInicio, Date fechaFin) {
        List<Movimiento> movimientos = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        if (parent == null || parent.isEmpty()) {
            movimientos = movimientoRepository.findByUsuarioIdAndFechaMovimientoBetweensStarDateAndEndDateOrderByIdDesc(usuario, fechaInicio, fechaFin);
        } else {
            movimientos = movimientoRepository.findByUsuarioIdAndFechaMovimientoBetweensStarDateAndEndDateAndParentOrderByIdDesc(usuario, parent, fechaInicio, fechaFin);
        }
        return MovimientoConverter.listEntitytoListModel(movimientos);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoModel> findAll(Pageable pageable) {
        return MovimientoConverter.pageEntitytoPageModel(pageable, movimientoRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return MovimientoConverter.pageEntitytoPageModel(pageable, movimientoRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        MovimientoModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Movimiento> optional = movimientoRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = MovimientoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public Movimiento registrarMovimiento(Movimiento movimiento) {
        movimiento = movimientoRepository.saveAndFlush(movimiento);
        return movimiento;
    }

    @Override
    @Transactional
    public Movimiento update(Movimiento movimiento) {
        movimiento = movimientoRepository.saveAndFlush(movimiento);
        return movimiento;
    }

    @Override
    public List<Movimiento> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return movimientoRepository.findByUsuarioIdRangoFecha(usuario, fechaInicio, fechaFin);
    }

    @Override
    public List<Movimiento> findByUsuarioIdAndTablaIdAndTablaNombre(Long usuarioId, Long tablaId, String tablaNombre) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return movimientoRepository.findByUsuarioIdAndTablaIdAndTablaNombreOrderByIdDesc(usuario, tablaId, tablaNombre);
    }

    @Override
    @Transactional
    public void deleteMovimiento(Long usuarioId, Long movimientoId) throws Exception {
        try {
            Usuario usuario = new Usuario();
            usuario.setId(usuarioId);
            Optional<Movimiento> optionalMovimiento = movimientoRepository.findByIdAndUsuarioId(movimientoId, usuario);
            if (optionalMovimiento.isPresent()) {
                Movimiento movimiento = optionalMovimiento.get();
                if (movimiento.getTablaNombre().equals(ConstantUtil.PRESTAMOS)) {
                    Long prestamoId = movimiento.getTablaId();
                    Optional<Prestamo> optionalPrestamo = prestamoRepository.findByIdAndUsuarioId(prestamoId, usuario);
                    if (optionalPrestamo.isPresent()) {
                        Prestamo prestamo = optionalPrestamo.get();
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

                        prestamo.setMontoPagado(prestamo.getMontoPagado() - movimiento.getMonto());
                        cantidaCuotasRevertir = prestamo.getCantidadCuotasPagadas() - cantidaCuotasRevertir;
                        if (cantidaCuotasRevertir < 0) {
                            cantidaCuotasRevertir = 0;
                        }
                        prestamo.setCantidadCuotasPagadas(cantidaCuotasRevertir);
                        prestamo.setSiguienteCuota(prestamo.getCantidadCuotasPagadas() + 1);
                        prestamoRepository.save(prestamo);
                    } else {
                        throw new Exception("No existe Prestamo!");
                    }
                }
                archivoService.deleteFiles(movimientoId, ConstantUtil.MOVIMIENTOS, usuarioId);
                movimientoRepository.deleteById(movimientoId);
            } else {
                throw new Exception("No existe el Movimiento!");
            }
        } catch (Exception e) {
            throw new Exception("No se pudo eliminar el Movimientos! " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteMovimientos(Long tablaId, String tablaNombre, Long usuarioId) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        List<Long> movimientosIds = movimientoRepository.listMovimientoIdByUsuarioIdAndTablaIdAndTablaNombre(usuario, tablaId, tablaNombre);
        try {
            for (Long movimientoId : movimientosIds) {
                deleteMovimiento(usuarioId, movimientoId);
            }
        } catch (Exception e) {
            throw new Exception("No se pudieron eliminar los Movimientos! " + e.getMessage());
        }
    }
}
