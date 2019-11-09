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
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.ConceptoRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.MovimientoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;
import py.com.fuentepy.appfinanzasBackend.resource.concepto.*;
import py.com.fuentepy.appfinanzasBackend.service.ConceptoService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConceptoServiceImpl implements ConceptoService {

    private static final Log LOG = LogFactory.getLog(ConceptoServiceImpl.class);

    @Autowired
    private ConceptoRepository conceptoRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ArchivoServiceImpl archivoService;

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
    public boolean pagar(ConceptoRequestPago request, Long usuarioId) {
        boolean retorno = false;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Concepto> optional = conceptoRepository.findByIdAndUsuarioId(request.getId(), usuario);
        if (optional.isPresent()) {
            Concepto entity = optional.get();
            Movimiento movimiento = new Movimiento();
            movimiento.setNumeroComprobante(request.getNumeroComprobante());
            movimiento.setFechaMovimiento(new Date());
            movimiento.setMonto(request.getMontoPagado());
            movimiento.setSigno("-");
            movimiento.setDetalle("Pago: " + entity.getNombre());
            movimiento.setTablaId(entity.getId());
            movimiento.setTablaNombre("conceptos");
            movimiento.setMonedaId(entity.getMonedaId());
            movimiento.setUsuarioId(entity.getUsuarioId());
            movimiento = movimientoRepository.saveAndFlush(movimiento);
            if (request.getArchivoModels() != null && !request.getArchivoModels().isEmpty()) {
                guardarArchivos(request.getArchivoModels(), movimiento.getId(), usuario);
            }
            retorno = true;
        }
        return retorno;
    }

    @Override
    public boolean cobrar(ConceptoRequestCobro request, Long usuarioId) {
        boolean retorno = false;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Concepto> optional = conceptoRepository.findByIdAndUsuarioId(request.getId(), usuario);
        if (optional.isPresent()) {
            Concepto entity = optional.get();
            Movimiento movimiento = new Movimiento();
            movimiento.setNumeroComprobante(request.getNumeroComprobante());
            movimiento.setFechaMovimiento(new Date());
            movimiento.setMonto(request.getMontoCobrado());
            movimiento.setSigno("+");
            movimiento.setDetalle("Cobro: " + entity.getNombre());
            movimiento.setTablaId(entity.getId());
            movimiento.setTablaNombre("conceptos");
            movimiento.setMonedaId(entity.getMonedaId());
            movimiento.setUsuarioId(entity.getUsuarioId());
            movimiento = movimientoRepository.saveAndFlush(movimiento);
            if (request.getArchivoModels() != null && !request.getArchivoModels().isEmpty()) {
                guardarArchivos(request.getArchivoModels(), movimiento.getId(), usuario);
            }
            retorno = true;
        }
        return retorno;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        conceptoRepository.deleteById(id);
    }

    private void guardarArchivos(List<ArchivoModel> archivos, Long tablaId, Usuario usuario) {
        try {
            archivoService.saveList(archivos, tablaId, "movimientos", usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
