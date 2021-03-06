package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import py.com.fuentepy.appfinanzasBackend.converter.ConceptoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.*;
import py.com.fuentepy.appfinanzasBackend.data.repository.ConceptoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.ParametroRepository;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.*;
import py.com.fuentepy.appfinanzasBackend.service.ConceptoService;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConceptoServiceImpl implements ConceptoService {

    private static final Log LOG = LogFactory.getLog(ConceptoServiceImpl.class);

    @Autowired
    private ConceptoRepository conceptoRepository;

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private MovimientoService movimientoService;

    @Override
    @Transactional(readOnly = true)
    public List<ConceptoModel> findAll() {
        return ConceptoConverter.listEntitytoListModel(conceptoRepository.findAll());
    }

    @Override
    public List<ConceptoModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return ConceptoConverter.listEntitytoListModel(conceptoRepository.findByUsuarioIdOrderByIdDesc(usuario));
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
    }

    @Override
    @Transactional
    public boolean update(ConceptoRequestUpdate request, Long usuarioId) {
        Concepto entity = conceptoRepository.save(ConceptoConverter.conceptoRequestUpdateToConceptoEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    public Movimiento pagar(ConceptoRequestPago request, Long usuarioId) {
        Movimiento retorno = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Concepto> optional = conceptoRepository.findByIdAndUsuarioId(request.getId(), usuario);
        if (optional.isPresent()) {
            Concepto entity = optional.get();
            if (TipoConcepto.egreso.toString().equals(entity.getTipoConcepto().name())) {
                Movimiento movimiento = new Movimiento();
                movimiento.setNumeroComprobante(request.getNumeroComprobante());
                movimiento.setFechaMovimiento(request.getFechaMovimiento());
                movimiento.setMonto(request.getMontoPagado());
                movimiento.setSigno("-");
                movimiento.setDetalle("Pago: " + entity.getNombre());
                movimiento.setComentario(request.getComentario());
                movimiento.setTablaId(entity.getId());
                movimiento.setTablaNombre(ConstantUtil.CONCEPTOS);
                movimiento.setMonedaId(entity.getMonedaId());
                movimiento.setUsuarioId(entity.getUsuarioId());
                retorno = movimientoService.registrarMovimiento(movimiento);
            }
        }
        return retorno;
    }

    @Override
    public Movimiento cobrar(ConceptoRequestCobro request, Long usuarioId) {
        Movimiento retorno = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Concepto> optional = conceptoRepository.findByIdAndUsuarioId(request.getId(), usuario);
        if (optional.isPresent()) {
            Concepto entity = optional.get();
            if (TipoConcepto.ingreso.toString().equals(entity.getTipoConcepto().name())) {
                Movimiento movimiento = new Movimiento();
                movimiento.setNumeroComprobante(request.getNumeroComprobante());
                movimiento.setFechaMovimiento(request.getFechaMovimiento());
                movimiento.setMonto(request.getMontoCobrado());
                movimiento.setSigno("+");
                movimiento.setDetalle("Cobro: " + entity.getNombre());
                movimiento.setComentario(request.getComentario());
                movimiento.setTablaId(entity.getId());
                movimiento.setTablaNombre(ConstantUtil.CONCEPTOS);
                movimiento.setMonedaId(entity.getMonedaId());
                movimiento.setUsuarioId(entity.getUsuarioId());
                retorno = movimientoService.registrarMovimiento(movimiento);
            }
        }
        return retorno;
    }

    @Override
    public List<ConceptoMovimientoModel> findByUsuarioAndConceptoId(Long usuarioId, Long conceptoId) {
        return ConceptoConverter.listMovimientosToListConceptoMovimientoModel(movimientoService.findByUsuarioIdAndTablaIdAndTablaNombre(usuarioId, conceptoId, ConstantUtil.CONCEPTOS));
    }

    @Override
    @Transactional
    public void delete(Long usuarioId, Long ConceptoId) throws Exception {
        try {
            movimientoService.deleteMovimientos(ConceptoId, ConstantUtil.CONCEPTOS, usuarioId);
            conceptoRepository.deleteById(ConceptoId);
        } catch (Exception e) {
            throw new Exception("No se pudo eliminar el Concepto! " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void saveDefault(Usuario usuario) {
        Moneda moneda = new Moneda();
        moneda.setId(1);
        List<Parametro> parametroList = parametroRepository.findByGrupo(ConstantUtil.CONCEPTOS_DEFAULT);
        for (Parametro parametro : parametroList) {
            TipoConcepto tipoConcepto = TipoConcepto.valueOf(parametro.getValor());
            Concepto concepto = new Concepto();
            concepto.setNombre(parametro.getDescripcion());
            concepto.setTipoConcepto(tipoConcepto);
            concepto.setMonedaId(moneda);
            concepto.setUsuarioId(usuario);
            conceptoRepository.save(concepto);
        }
    }
}
