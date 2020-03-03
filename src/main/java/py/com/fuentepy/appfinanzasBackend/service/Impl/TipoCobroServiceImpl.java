package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.TipoCobroConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.*;
import py.com.fuentepy.appfinanzasBackend.data.repository.ParametroRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.TipoCobroRepository;
import py.com.fuentepy.appfinanzasBackend.resource.tipoCobro.TipoCobroModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoCobro.TipoCobroRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoCobro.TipoCobroRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.TipoCobroService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import java.util.List;
import java.util.Optional;

@Service
public class TipoCobroServiceImpl implements TipoCobroService {

    @Autowired
    private TipoCobroRepository tipoCobroRepository;

    @Autowired
    private ParametroRepository parametroRepository;

    @Override
    public List<TipoCobroModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TipoCobroConverter.listEntitytoListModel(tipoCobroRepository.findByUsuarioId(usuario));
    }

    @Override
    public Page<TipoCobroModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TipoCobroConverter.pageEntitytoPageModel(pageable, tipoCobroRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public TipoCobroModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        TipoCobroModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<TipoCobro> optional = tipoCobroRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = TipoCobroConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(TipoCobroRequestNew request, Long usuarioId) {
        TipoCobro entity = tipoCobroRepository.save(TipoCobroConverter.tipoCobroRequestNewToTipoCobroEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(TipoCobroRequestUpdate request, Long usuarioId) {
        TipoCobro entity = tipoCobroRepository.save(TipoCobroConverter.tipoCobroRequestUpdateToCobroEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tipoCobroRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveDefault(Usuario usuario) {
        List<Parametro> parametroList = parametroRepository.findByGrupo(ConstantUtil.TIPOS_COBROS_DEFAULT);
        for (Parametro parametro : parametroList) {
            TipoCobro tipoCobro = new TipoCobro();
            tipoCobro.setNombre(parametro.getDescripcion());
            tipoCobro.setUsuarioId(usuario);
            tipoCobroRepository.save(tipoCobro);
        }
    }
}
