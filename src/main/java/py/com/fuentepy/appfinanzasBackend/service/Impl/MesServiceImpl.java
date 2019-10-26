package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.MesConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Mes;
import py.com.fuentepy.appfinanzasBackend.data.repository.MesRepository;
import py.com.fuentepy.appfinanzasBackend.resource.mes.MesModel;
import py.com.fuentepy.appfinanzasBackend.resource.mes.MesRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.mes.MesRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.MesService;

import java.util.List;
import java.util.Optional;

@Service
public class MesServiceImpl implements MesService {

    @Autowired
    private MesRepository mesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MesModel> findAll() {
        return MesConverter.listEntitytoListModel(mesRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MesModel> findAll(Pageable pageable) {
        return MesConverter.pageEntitytoPageModel(pageable, mesRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public MesModel findById(Integer id) {
        MesModel model = null;
        Optional<Mes> optional = mesRepository.findById(id);
        if (optional.isPresent()) {
            model = MesConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(MesRequestNew request) {
        Mes entity = mesRepository.save(MesConverter.mesRequestNewToMesEntity(request));
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
//            movimiento.setMesId(entity.getMesId());
////            movimiento.setTipoPagoId(entity.getTipoCobroId());
//            movimiento.setUsuarioId(usuario);
//            movimientoRepository.save(movimiento);
//        }
    }

    @Override
    @Transactional
    public boolean update(MesRequestUpdate request) {
        Mes entity = mesRepository.save(MesConverter.mesRequestUpdateToMesEntity(request));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        mesRepository.deleteById(id);
    }
}
