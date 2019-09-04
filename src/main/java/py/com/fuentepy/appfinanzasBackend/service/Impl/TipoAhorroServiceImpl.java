package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoAhorro;
import py.com.fuentepy.appfinanzasBackend.data.repository.TipoAhorroRepository;
import py.com.fuentepy.appfinanzasBackend.service.TipoAhorroService;

import java.util.List;

@Service
public class TipoAhorroServiceImpl implements TipoAhorroService {

    @Autowired
    private TipoAhorroRepository tipoAhorroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoAhorro> findAll() {
        return (List<TipoAhorro>) tipoAhorroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoAhorro> findAll(Pageable pageable) {
        return tipoAhorroRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoAhorro findById(Integer id) {
        return tipoAhorroRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public TipoAhorro save(TipoAhorro tipoAhorro) {
        return tipoAhorroRepository.save(tipoAhorro);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        tipoAhorroRepository.deleteById(id);
    }
}
