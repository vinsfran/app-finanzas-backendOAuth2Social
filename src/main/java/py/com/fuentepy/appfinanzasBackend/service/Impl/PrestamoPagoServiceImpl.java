package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.PrestamoPagoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.data.entity.PrestamoPago;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.model.PrestamoPagoModel;
import py.com.fuentepy.appfinanzasBackend.data.repository.PrestamoPagoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.PrestamoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.service.PrestamoPagoService;

import java.util.List;
import java.util.Optional;

@Service
public class PrestamoPagoServiceImpl implements PrestamoPagoService {

    private static final Log LOG = LogFactory.getLog(PrestamoPagoServiceImpl.class);

    @Autowired
    private PrestamoPagoRepository prestamoPagoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoPagoModel> findAll() {
        return PrestamoPagoConverter.listEntitytoListModel(prestamoPagoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrestamoPagoModel> findAll(Pageable pageable) {
        return PrestamoPagoConverter.pageEntitytoPageModel(pageable, prestamoPagoRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrestamoPagoModel> findByPrestamoId(Long prestamoId, Pageable pageable) {
        LOG.info("prestamoId: " + prestamoId);
        Prestamo prestamo = new Prestamo();
        prestamo.setId(prestamoId);
        return PrestamoPagoConverter.pageEntitytoPageModel(pageable, prestamoPagoRepository.findByPrestamoId(prestamo, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public PrestamoPagoModel findById(Long id) {
        PrestamoPagoModel prestamoPagoModel = null;
        Optional<PrestamoPago> optional = prestamoPagoRepository.findById(id);
        if (optional.isPresent()) {
            prestamoPagoModel = PrestamoPagoConverter.entityToModel(optional.get());
        }
        return prestamoPagoModel;
    }

    @Override
    @Transactional
    public PrestamoPagoModel save(PrestamoPagoModel prestamoPagoModel) {
//        PrestamoPagoModel newPrestamoPagoModel;
        Usuario usuario = usuarioRepository.findById2(prestamoPagoModel.getUsuarioId());
        Optional<Prestamo> optional = prestamoRepository.findById(prestamoPagoModel.getPrestamoId());
        Prestamo prestamo = null;
        if (optional.isPresent()) {
            prestamo = optional.get();
            prestamo.setMontoPagado(prestamo.getMontoPagado() + prestamoPagoModel.getMontoPagado());
            prestamo.setCantidadCuotasPagadas(prestamo.getCantidadCuotasPagadas() + 1);
        }
        PrestamoPago prestamoPago = PrestamoPagoConverter.modelToEntity(prestamoPagoModel);
        prestamoPago.setUsuarioId(usuario);
        prestamoPago.setPrestamoId(prestamo);
        prestamoPago.setNumeroCuota(prestamo.getCantidadCuotasPagadas());
        return PrestamoPagoConverter.entityToModel(prestamoPagoRepository.save(prestamoPago));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        prestamoPagoRepository.deleteById(id);
    }
}
