package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.MesConverter;
import py.com.fuentepy.appfinanzasBackend.entity.Mes;
import py.com.fuentepy.appfinanzasBackend.model.MesModel;
import py.com.fuentepy.appfinanzasBackend.repository.MesRepository;
import py.com.fuentepy.appfinanzasBackend.service.MesService;

import java.util.List;

@Service
public class MesServiceImpl implements MesService {

    @Autowired
    private MesRepository mesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Mes> findAll() {
        return mesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Mes> findAll(Pageable pageable) {
        return mesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Mes findById(Integer id) {
        return mesRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public MesModel save(MesModel mesModel) {
        Mes mes = MesConverter.modelToEntity(mesModel);
        return MesConverter.entityToModel(mesRepository.save(mes));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        mesRepository.deleteById(id);
    }
}
