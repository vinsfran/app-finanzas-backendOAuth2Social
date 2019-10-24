package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.MonedaConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Moneda;
import py.com.fuentepy.appfinanzasBackend.resource.moneda.MonedaModel;
import py.com.fuentepy.appfinanzasBackend.data.repository.MonedaRepository;
import py.com.fuentepy.appfinanzasBackend.resource.moneda.MonedaRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.moneda.MonedaRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.MonedaService;

import java.util.List;
import java.util.Optional;

@Service
public class MonedaServiceImpl implements MonedaService {

    @Autowired
    private MonedaRepository monedaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MonedaModel> findAll() {
        return MonedaConverter.listEntitytoListModel(monedaRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MonedaModel> findAll(Pageable pageable) {
        return MonedaConverter.pageEntitytoPageModel(pageable, monedaRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public MonedaModel findById(Integer id) {
        MonedaModel model = null;
        Optional<Moneda> optional = monedaRepository.findById(id);
        if (optional.isPresent()) {
            model = MonedaConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(MonedaRequestNew request) {
        Moneda entity = monedaRepository.save(MonedaConverter.monedaRequestNewToMonedaEntity(request));
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
    public boolean update(MonedaRequestUpdate request) {
        Moneda entity = monedaRepository.save(MonedaConverter.monedaRequestUpdateToMonedaEntity(request));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        monedaRepository.deleteById(id);
    }
}
