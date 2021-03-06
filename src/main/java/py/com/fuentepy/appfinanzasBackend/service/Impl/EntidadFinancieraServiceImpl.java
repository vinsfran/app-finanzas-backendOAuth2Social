package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.EntidadFinancieraConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.EntidadFinanciera;
import py.com.fuentepy.appfinanzasBackend.data.entity.Parametro;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoCobro;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.EntidadFinancieraRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.ParametroRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.UsuarioRepository;
import py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera.EntidadFinancieraModel;
import py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera.EntidadFinancieraRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.entidadFinanciera.EntidadFinancieraRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.EntidadFinancieraService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import java.util.List;
import java.util.Optional;

@Service
public class EntidadFinancieraServiceImpl implements EntidadFinancieraService {

    private static final Log LOG = LogFactory.getLog(EntidadFinancieraServiceImpl.class);

    @Autowired
    private EntidadFinancieraRepository entidadFinancieraRepository;

    @Autowired
    private ParametroRepository parametroRepository;

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
    public EntidadFinancieraModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        EntidadFinancieraModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<EntidadFinanciera> optional = entidadFinancieraRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = EntidadFinancieraConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional(readOnly = true)
    public EntidadFinancieraModel findById(Long id) {
        EntidadFinancieraModel model = null;
        Optional<EntidadFinanciera> optional = entidadFinancieraRepository.findById(id);
        if (optional.isPresent()) {
            model = EntidadFinancieraConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(EntidadFinancieraRequestNew request, Long usuarioId) {
        EntidadFinanciera entity = entidadFinancieraRepository.save(EntidadFinancieraConverter.entidadFinancieraRequestNewToEntidadFinancieraEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(EntidadFinancieraRequestUpdate request, Long usuarioId) {
        EntidadFinanciera entity = entidadFinancieraRepository.save(EntidadFinancieraConverter.entidadFinancieraRequestUpdateToAhorroEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entidadFinancieraRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveDefault(Usuario usuario) {
        List<Parametro> parametroList = parametroRepository.findByGrupo(ConstantUtil.ENTIDADES_FINANCIERAS_DEFAULT);
        for (Parametro parametro : parametroList) {
            EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
            entidadFinanciera.setNombre(parametro.getDescripcion());
            entidadFinanciera.setUsuarioId(usuario);
            entidadFinancieraRepository.save(entidadFinanciera);
        }
    }
}
