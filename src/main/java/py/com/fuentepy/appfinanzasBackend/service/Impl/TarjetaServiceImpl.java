package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.TarjetaConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Tarjeta;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaModel;
import py.com.fuentepy.appfinanzasBackend.data.repository.TarjetaRepository;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tarjeta.TarjetaRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.TarjetaService;

import java.util.List;
import java.util.Optional;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    private static final Log LOG = LogFactory.getLog(TarjetaServiceImpl.class);

    @Autowired
    private TarjetaRepository tarjetaRepository;

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

//        if(!entity.getEstado()){
//            Concepto concepto = conceptoRepository.findByCodigoConcepto("CA");
//
//            Movimiento movimiento = new Movimiento();
//            movimiento.setNumeroComprobante("");
//            movimiento.setFechaMovimiento(entity.getFechaVencimiento());
//            movimiento.setMontoPagado(entity.getMontoCapital());
//            movimiento.setNombreEntidad(entity.getEntidadFinancieraId().getNombre());
//            movimiento.setPrestamoId(null);
//            movimiento.setTarjetaId(entity);
//            movimiento.setTarjetaId(null);
//            movimiento.setNumeroCuota(entity.getCantidadCuotas());
//            movimiento.setConceptoId(concepto);
//            movimiento.setMonedaId(entity.getMonedaId());
////            movimiento.setTipoPagoId(entity.getTipoCobroId());
//            movimiento.setUsuarioId(usuario);
//            movimientoRepository.save(movimiento);
//        }
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

}
