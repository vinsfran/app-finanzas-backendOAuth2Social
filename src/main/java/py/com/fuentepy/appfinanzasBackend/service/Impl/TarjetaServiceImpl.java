package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.TarjetaConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Tarjeta;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.model.TarjetaModel;
import py.com.fuentepy.appfinanzasBackend.data.repository.TarjetaRepository;
import py.com.fuentepy.appfinanzasBackend.service.TarjetaService;

import java.util.List;
import java.util.Optional;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    private static final Log LOG = LogFactory.getLog(TarjetaServiceImpl.class);

    @Autowired
    private TarjetaRepository TarjetaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TarjetaModel> findAll() {
        return TarjetaConverter.listEntitytoListModel(TarjetaRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TarjetaModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TarjetaConverter.listEntitytoListModel(TarjetaRepository.findByUsuarioId(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TarjetaModel> findAll(Pageable pageable) {
        return TarjetaConverter.pageEntitytoPageModel(pageable, TarjetaRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TarjetaModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TarjetaConverter.pageEntitytoPageModel(pageable, TarjetaRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public TarjetaModel findById(Long id) {
        TarjetaModel model = null;
        Optional<Tarjeta> optional = TarjetaRepository.findById(id);
        if (optional.isPresent()) {
            model = TarjetaConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public TarjetaModel save(TarjetaModel TarjetaModel) {
        Tarjeta entity = TarjetaConverter.modelToEntity(TarjetaModel);
        return TarjetaConverter.entityToModel(TarjetaRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TarjetaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByTenantName(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TarjetaRepository.countByUsuarioId(usuario);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Tarjeta> findByUsuarioIdLista(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TarjetaRepository.findByUsuarioId(usuario);
    }

}
