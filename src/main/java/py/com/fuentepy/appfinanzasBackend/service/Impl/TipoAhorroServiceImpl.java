package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.AhorroTipoConverter;
import py.com.fuentepy.appfinanzasBackend.converter.EntidadFinancieraConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.AhorroTipo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.TipoAhorroRepository;
import py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo.AhorroTipoModel;
import py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo.AhorroTipoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.ahorroTipo.AhorroTipoRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.TipoAhorroService;

import java.util.List;
import java.util.Optional;

@Service
public class TipoAhorroServiceImpl implements TipoAhorroService {

    @Autowired
    private TipoAhorroRepository tipoAhorroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AhorroTipo> findAll() {
        return (List<AhorroTipo>) tipoAhorroRepository.findAll();
    }

    @Override
    public List<AhorroTipoModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return AhorroTipoConverter.listEntitytoListModel(tipoAhorroRepository.findByUsuarioId(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AhorroTipo> findAll(Pageable pageable) {
        return tipoAhorroRepository.findAll(pageable);
    }

    @Override
    public Page<AhorroTipoModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return AhorroTipoConverter.pageEntitytoPageModel(pageable, tipoAhorroRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public AhorroTipoModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        AhorroTipoModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<AhorroTipo> optional = tipoAhorroRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = AhorroTipoConverter.entityToModel(optional.get());
        }
        return model;
    }


    @Override
    @Transactional
    public boolean create(AhorroTipoRequestNew request, Long usuarioId) {
        AhorroTipo entity = tipoAhorroRepository.save(AhorroTipoConverter.ahorroTipoRequestNewToAhorroTipoEntity(request, usuarioId));
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
//            movimiento.setAhorroId(entity);
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
    public boolean update(AhorroTipoRequestUpdate request, Long usuarioId) {
        AhorroTipo entity = tipoAhorroRepository.save(AhorroTipoConverter.ahorroTipoRequestUpdateToAhorroEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        tipoAhorroRepository.deleteById(id);
    }
}
