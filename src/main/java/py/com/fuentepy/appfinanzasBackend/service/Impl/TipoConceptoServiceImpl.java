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
import py.com.fuentepy.appfinanzasBackend.service.TipoConceptoService;

import java.util.List;

@Service
public class TipoConceptoServiceImpl implements TipoConceptoService {

    @Autowired
    private TipoConceptoRepository tipoConceptoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoConcepto> findAll() {
        return (List<TipoConcepto>) tipoConceptoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoConcepto> findAll(Pageable pageable) {
        return tipoConceptoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoConcepto findById(Integer id) {
        return tipoConceptoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public TipoConceptoModel save(TipoConceptoModel tipoConceptoModel) {
        TipoConcepto tipoConcepto = TipoConceptoConverter.modelToEntity(tipoConceptoModel);
        return TipoConceptoConverter.entityToModel(tipoConceptoRepository.save(tipoConcepto));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        tipoConceptoRepository.deleteById(id);
    }
}
