package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.ConceptoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Concepto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.ConceptoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.ConceptoModel;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.ConceptoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.ConceptoRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.ConceptoService;

import java.util.List;
import java.util.Optional;

@Service
public class ConceptoServiceImpl implements ConceptoService {

    private static final Log LOG = LogFactory.getLog(ConceptoServiceImpl.class);

    @Autowired
    private ConceptoRepository conceptoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ConceptoModel> findAll() {
        return ConceptoConverter.listEntitytoListModel(conceptoRepository.findAll());
    }

    @Override
    public List<ConceptoModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return ConceptoConverter.listEntitytoListModel(conceptoRepository.findByUsuarioId(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConceptoModel> findAll(Pageable pageable) {
        return ConceptoConverter.pageEntitytoPageModel(pageable, conceptoRepository.findAll(pageable));
    }

    @Override
    public Page<ConceptoModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return ConceptoConverter.pageEntitytoPageModel(pageable, conceptoRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public ConceptoModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        ConceptoModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Concepto> optional = conceptoRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = ConceptoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional(readOnly = true)
    public ConceptoModel findById(Long id) {
        ConceptoModel model = null;
        Optional<Concepto> optional = conceptoRepository.findById(id);
        if (optional.isPresent()) {
            model = ConceptoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(ConceptoRequestNew request, Long usuarioId) {
        Concepto entity = conceptoRepository.save(ConceptoConverter.conceptoRequestNewToConceptoEntity(request, usuarioId));
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
//            movimiento.setNombreEntidad(entity.getConceptoId().getNombre());
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
    public boolean update(ConceptoRequestUpdate request, Long usuarioId) {
        Concepto entity = conceptoRepository.save(ConceptoConverter.conceptoRequestUpdateToAhorroEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        conceptoRepository.deleteById(id);
    }
}
