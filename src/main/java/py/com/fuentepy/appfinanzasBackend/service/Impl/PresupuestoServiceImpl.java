package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.PresupuestoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Presupuesto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.model.PresupuestoModel;
import py.com.fuentepy.appfinanzasBackend.data.repository.PresupuestoRepository;
import py.com.fuentepy.appfinanzasBackend.service.PresupuestoService;

import java.util.List;
import java.util.Optional;

@Service
public class PresupuestoServiceImpl implements PresupuestoService {

    private static final Log LOG = LogFactory.getLog(PresupuestoServiceImpl.class);

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PresupuestoModel> findAll() {
        return PresupuestoConverter.listEntitytoListModel(presupuestoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PresupuestoModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return PresupuestoConverter.listEntitytoListModel(presupuestoRepository.findByUsuarioId(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PresupuestoModel> findAll(Pageable pageable) {
        return PresupuestoConverter.pageEntitytoPageModel(pageable, presupuestoRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PresupuestoModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return PresupuestoConverter.pageEntitytoPageModel(pageable, presupuestoRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public PresupuestoModel findById(Long id) {
        PresupuestoModel model = null;
        Optional<Presupuesto> optional = presupuestoRepository.findById(id);
        if (optional.isPresent()) {
            model = PresupuestoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public PresupuestoModel save(PresupuestoModel presupuestoModel) {
        Presupuesto entity = PresupuestoConverter.modelToEntity(presupuestoModel);
        return PresupuestoConverter.entityToModel(presupuestoRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        presupuestoRepository.deleteById(id);
    }
}
