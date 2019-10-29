package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.TarjetaConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Tarjeta;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.MovimientoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaModel;
import py.com.fuentepy.appfinanzasBackend.data.repository.TarjetaRepository;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestPago;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.TarjetaService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    private static final Log LOG = LogFactory.getLog(TarjetaServiceImpl.class);

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ArchivoServiceImpl archivoService;

    @Override
    @Transactional(readOnly = true)
    public List<TarjetaModel> findAll() {
        return TarjetaConverter.listEntitytoListModel(tarjetaRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TarjetaModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TarjetaConverter.listEntitytoListModel(tarjetaRepository.findByUsuarioId(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TarjetaModel> findAll(Pageable pageable) {
        return TarjetaConverter.pageEntitytoPageModel(pageable, tarjetaRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TarjetaModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TarjetaConverter.pageEntitytoPageModel(pageable, tarjetaRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public TarjetaModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        TarjetaModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Tarjeta> optional = tarjetaRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = TarjetaConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(TarjetaRequestNew request, Long usuarioId) {
        Tarjeta entity = tarjetaRepository.save(TarjetaConverter.tarjetaNewToTarjetaEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(TarjetaRequestUpdate request, Long usuarioId) {
        Tarjeta entity = tarjetaRepository.save(TarjetaConverter.tarjetaUpdateToTarjetaEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean pagar(TarjetaRequestPago request, Long usuarioId) {
        boolean retorno = false;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Tarjeta> optional = tarjetaRepository.findByIdAndUsuarioId(request.getId(), usuario);
        if (optional.isPresent()) {
            Tarjeta entity = optional.get();
            if (entity.getEstado()) {
                entity.setMontoUltimoPago(request.getMontoPagado());
                entity.setMontoDisponible(entity.getMontoDisponible() + entity.getMontoUltimoPago());
                tarjetaRepository.save(entity);
                Movimiento movimiento = new Movimiento();
                movimiento.setNumeroComprobante(request.getNumeroComprobante());
                movimiento.setFechaMovimiento(new Date());
                movimiento.setMonto(request.getMontoPagado());
                movimiento.setSigno("-");
                movimiento.setDetalle("Pago: Tarjeta: " + entity.getMarca() + " / " + entity.getNumeroTarjeta() + " - " + entity.getEntidadFinancieraId().getNombre());
                movimiento.setTablaId(entity.getId());
                movimiento.setTablaNombre("tarjetas");
                movimiento.setMonedaId(entity.getMonedaId());
                movimiento.setUsuarioId(entity.getUsuarioId());
                movimiento = movimientoRepository.saveAndFlush(movimiento);
                if (request.getArchivoModels() != null && !request.getArchivoModels().isEmpty()) {
                    guardarArchivos(request.getArchivoModels(), movimiento.getId(), usuario);
                }
                retorno = true;
            }
        }
        return retorno;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tarjetaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByTenantName(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return tarjetaRepository.countByUsuarioId(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarjeta> findByUsuarioIdLista(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return tarjetaRepository.findByUsuarioId(usuario);
    }

    private void guardarArchivos(List<ArchivoModel> archivos, Long tablaId, Usuario usuario) {
        try {
            archivoService.saveList(archivos, tablaId, "tarjetas", usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
