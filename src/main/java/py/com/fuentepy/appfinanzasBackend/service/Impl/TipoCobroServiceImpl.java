package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.entity.TipoCobro;
import py.com.fuentepy.appfinanzasBackend.repository.TipoCobroRepository;
import py.com.fuentepy.appfinanzasBackend.service.TipoCobroService;

import java.util.List;

@Service
public class TipoCobroServiceImpl implements TipoCobroService {

    @Autowired
    private TipoCobroRepository tipoCobroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoCobro> findAll() {
        return (List<TipoCobro>) tipoCobroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoCobro> findAll(Pageable pageable) {
        return tipoCobroRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoCobro findById(Integer id) {
        return tipoCobroRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public TipoCobro save(TipoCobro tipoCobro) {
        return tipoCobroRepository.save(tipoCobro);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        tipoCobroRepository.deleteById(id);
    }
}
