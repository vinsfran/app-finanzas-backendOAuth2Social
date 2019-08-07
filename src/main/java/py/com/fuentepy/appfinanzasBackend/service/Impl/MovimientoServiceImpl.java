package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.MovimientoConverter;
import py.com.fuentepy.appfinanzasBackend.entity.*;
import py.com.fuentepy.appfinanzasBackend.model.MovimientoModel;
import py.com.fuentepy.appfinanzasBackend.repository.*;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private static final Log LOG = LogFactory.getLog(MovimientoServiceImpl.class);

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private AhorroRepository ahorroRepository;

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoModel> findAll() {
        return MovimientoConverter.listEntitytoListModel(movimientoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return MovimientoConverter.listEntitytoListModel(movimientoRepository.findByUsuarioId(usuario));
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
    public MovimientoModel findById(Long id) {
        MovimientoModel model = null;
        Optional<Movimiento> optional = movimientoRepository.findById(id);
        if (optional.isPresent()) {
            model = MovimientoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public MovimientoModel save(MovimientoModel movimientoModel, String action) {

        Usuario usuario = usuarioRepository.findById2(movimientoModel.getUsuarioId());

        Prestamo prestamo = null;
        if (movimientoModel.getPrestamoId() != null && movimientoModel.getPrestamoId() != 0) {
            Optional<Prestamo> optionalPrestamo = prestamoRepository.findById(movimientoModel.getPrestamoId());
            if (optionalPrestamo.isPresent()) {
                prestamo = optionalPrestamo.get();
                if (action.equals("UPDATE")) {
                    prestamo.setMontoPagado(prestamo.getMontoPagado() - prestamo.getMontoUltimoPago());
                }
                prestamo.setMontoPagado(prestamo.getMontoPagado() + movimientoModel.getMontoPagado());
                prestamo.setMontoUltimoPago(movimientoModel.getMontoPagado());
                prestamo.setCantidadCuotasPagadas(movimientoModel.getNumeroCuota());
            }
        }

        Ahorro ahorro = null;
        if (movimientoModel.getAhorroId() != null && movimientoModel.getAhorroId() != 0) {
            Optional<Ahorro> optionalAhorro = ahorroRepository.findById(movimientoModel.getAhorroId());
            if (optionalAhorro.isPresent()) {
                ahorro = optionalAhorro.get();
                if (action.equals("UPDATE")) {
                    ahorro.setMontoPagado(ahorro.getMontoPagado() - ahorro.getMontoUltimoPago());
                }
                ahorro.setMontoPagado(ahorro.getMontoPagado() + movimientoModel.getMontoPagado());
                ahorro.setMontoUltimoPago(movimientoModel.getMontoPagado());
                ahorro.setCantidadCuotasPagadas(movimientoModel.getNumeroCuota());
            }
        }

        Tarjeta tarjeta = null;
        if (movimientoModel.getTarjetaId() != null && movimientoModel.getTarjetaId() != 0) {
            Optional<Tarjeta> optionalTarjeta = tarjetaRepository.findById(movimientoModel.getTarjetaId());
            if (optionalTarjeta.isPresent()) {
                tarjeta = optionalTarjeta.get();
                if (action.equals("UPDATE")) {
                    tarjeta.setMontoPagado(tarjeta.getMontoPagado() - tarjeta.getMontoUltimoPago());
                } else {
                    tarjeta.setMontoPagado(0L);
                }
                tarjeta.setMontoPagado(tarjeta.getMontoPagado() + movimientoModel.getMontoPagado());
                tarjeta.setMontoUltimoPago(movimientoModel.getMontoPagado());
            }
        }

        Movimiento entity = MovimientoConverter.modelToEntity(movimientoModel);
        entity.setUsuarioId(usuario);
        entity.setPrestamoId(prestamo);
        entity.setAhorroId(ahorro);
        entity.setTarjetaId(tarjeta);

        return MovimientoConverter.entityToModel(movimientoRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        movimientoRepository.deleteById(id);
    }

    @Override
    public List<Movimiento> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return movimientoRepository.findByUsuarioIdRangoFecha(usuario, fechaInicio, fechaFin);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoModel> findByPrestamoId(Long prestamoId, Pageable pageable) {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(prestamoId);
        return MovimientoConverter.pageEntitytoPageModel(pageable, movimientoRepository.findByPrestamoId(prestamo, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoModel> findByAhorroId(Long ahorroId, Pageable pageable) {
        Ahorro ahorro = new Ahorro();
        ahorro.setId(ahorroId);
        return MovimientoConverter.pageEntitytoPageModel(pageable, movimientoRepository.findByAhorroId(ahorro, pageable));
    }

    @Override
    public Page<MovimientoModel> findByTarjetaId(Long tarjetaId, Pageable pageable) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(tarjetaId);
        return MovimientoConverter.pageEntitytoPageModel(pageable, movimientoRepository.findByTarjetaId(tarjeta, pageable));
    }
}
