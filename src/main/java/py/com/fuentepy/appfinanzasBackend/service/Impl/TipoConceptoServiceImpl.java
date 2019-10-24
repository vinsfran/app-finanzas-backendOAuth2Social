package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.TipoConceptoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoConcepto;
import py.com.fuentepy.appfinanzasBackend.data.repository.TipoConceptoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto.TipoConceptoModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto.TipoConceptoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoConcepto.TipoConceptoRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.TipoConceptoService;

import java.util.List;
import java.util.Optional;

@Service
public class TipoConceptoServiceImpl implements TipoConceptoService {

    @Autowired
    private TipoConceptoRepository tipoConceptoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoConceptoModel> findAll() {
        return TipoConceptoConverter.listEntitytoListModel(tipoConceptoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoConceptoModel> findAll(Pageable pageable) {
        return TipoConceptoConverter.pageEntitytoPageModel(pageable, tipoConceptoRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public TipoConceptoModel findById(Integer id) {
        TipoConceptoModel model = null;
        Optional<TipoConcepto> optional = tipoConceptoRepository.findById(id);
        if (optional.isPresent()) {
            model = TipoConceptoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(TipoConceptoRequestNew request) {
        TipoConcepto entity = tipoConceptoRepository.save(TipoConceptoConverter.tipoConceptoRequestNewToTipoConceptoEntity(request));
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
//            movimiento.setTipoConceptoId(entity.getTipoConceptoId());
////            movimiento.setTipoPagoId(entity.getTipoCobroId());
//            movimiento.setUsuarioId(usuario);
//            movimientoRepository.save(movimiento);
//        }
    }

    @Override
    @Transactional
    public boolean update(TipoConceptoRequestUpdate request) {
        TipoConcepto entity = tipoConceptoRepository.save(TipoConceptoConverter.tipoConceptoRequestUpdateToTipoConceptoEntity(request));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        tipoConceptoRepository.deleteById(id);
    }
}
