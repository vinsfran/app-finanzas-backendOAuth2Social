package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.EntidadFinancieraConverter;
import py.com.fuentepy.appfinanzasBackend.entity.EntidadFinanciera;
import py.com.fuentepy.appfinanzasBackend.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.model.EntidadFinancieraModel;
import py.com.fuentepy.appfinanzasBackend.repository.EntidadFinancieraRepository;
import py.com.fuentepy.appfinanzasBackend.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.service.EntidadFinancieraService;

import java.util.List;
import java.util.Optional;

@Service
public class EntidadFinancieraServiceImpl implements EntidadFinancieraService {

    private static final Log LOG = LogFactory.getLog(EntidadFinancieraServiceImpl.class);

    @Autowired
    private EntidadFinancieraRepository entidadFinancieraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EntidadFinancieraModel> findAll() {
        return EntidadFinancieraConverter.listEntitytoListModel(entidadFinancieraRepository.findAll());
    }

    @Override
    public List<EntidadFinancieraModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return EntidadFinancieraConverter.listEntitytoListModel(entidadFinancieraRepository.findByUsuarioId(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntidadFinancieraModel> findAll(Pageable pageable) {
        return EntidadFinancieraConverter.pageEntitytoPageModel(pageable, entidadFinancieraRepository.findAll(pageable));
    }

    @Override
    public Page<EntidadFinancieraModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return EntidadFinancieraConverter.pageEntitytoPageModel(pageable, entidadFinancieraRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public EntidadFinancieraModel findById(Integer id) {
        EntidadFinancieraModel model = null;
        Optional<EntidadFinanciera> optional = entidadFinancieraRepository.findById(id);
        if (optional.isPresent()) {
            model = EntidadFinancieraConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public EntidadFinancieraModel save(EntidadFinancieraModel entidadFinancieraModel) {
        EntidadFinanciera entity = EntidadFinancieraConverter.modelToEntity(entidadFinancieraModel);
        return EntidadFinancieraConverter.entityToModel(entidadFinancieraRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        entidadFinancieraRepository.deleteById(id);
    }
}
