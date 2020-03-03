package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.TipoAhorroConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Parametro;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoAhorro;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoCobro;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.ParametroRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.TipoAhorroRepository;
import py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro.TipoAhorroModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro.TipoAhorroRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoAhorro.TipoAhorroRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.TipoAhorroService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import java.util.List;
import java.util.Optional;

@Service
public class TipoAhorroServiceImpl implements TipoAhorroService {

    @Autowired
    private TipoAhorroRepository tipoAhorroRepository;

    @Autowired
    private ParametroRepository parametroRepository;

    @Override
    public List<TipoAhorroModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TipoAhorroConverter.listEntitytoListModel(tipoAhorroRepository.findByUsuarioId(usuario));
    }

    @Override
    public Page<TipoAhorroModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TipoAhorroConverter.pageEntitytoPageModel(pageable, tipoAhorroRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public TipoAhorroModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        TipoAhorroModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<TipoAhorro> optional = tipoAhorroRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = TipoAhorroConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(TipoAhorroRequestNew request, Long usuarioId) {
        TipoAhorro entity = tipoAhorroRepository.save(TipoAhorroConverter.tipoAhorroRequestNewToTipoAhorroEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(TipoAhorroRequestUpdate request, Long usuarioId) {
        TipoAhorro entity = tipoAhorroRepository.save(TipoAhorroConverter.tipoAhorroRequestUpdateToTipoAhorroEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tipoAhorroRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveDefault(Usuario usuario) {
        List<Parametro> parametroList = parametroRepository.findByGrupo(ConstantUtil.TIPOS_AHORROS_DEFAULT);
        for (Parametro parametro : parametroList) {
            TipoAhorro tipoAhorro = new TipoAhorro();
            tipoAhorro.setNombre(parametro.getDescripcion());
            tipoAhorro.setUsuarioId(usuario);
            tipoAhorroRepository.save(tipoAhorro);
        }
    }
}
