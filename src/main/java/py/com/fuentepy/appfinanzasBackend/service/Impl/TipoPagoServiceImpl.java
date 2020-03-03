package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.TipoPagoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Parametro;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoCobro;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoPago;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.ParametroRepository;
import py.com.fuentepy.appfinanzasBackend.data.repository.TipoPagoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.tipoPago.TipoPagoModel;
import py.com.fuentepy.appfinanzasBackend.resource.tipoPago.TipoPagoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.tipoPago.TipoPagoRequestUpdate;
import py.com.fuentepy.appfinanzasBackend.service.TipoPagoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import java.util.List;
import java.util.Optional;

@Service
public class TipoPagoServiceImpl implements TipoPagoService {

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private ParametroRepository parametroRepository;

    @Override
    public List<TipoPagoModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TipoPagoConverter.listEntitytoListModel(tipoPagoRepository.findByUsuarioId(usuario));
    }

    @Override
    public Page<TipoPagoModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return TipoPagoConverter.pageEntitytoPageModel(pageable, tipoPagoRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public TipoPagoModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        TipoPagoModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<TipoPago> optional = tipoPagoRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = TipoPagoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public boolean create(TipoPagoRequestNew request, Long usuarioId) {
        TipoPago entity = tipoPagoRepository.save(TipoPagoConverter.tipoPagoRequestNewToTipoPagoEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(TipoPagoRequestUpdate request, Long usuarioId) {
        TipoPago entity = tipoPagoRepository.save(TipoPagoConverter.tipoPagoRequestUpdateToPagoEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tipoPagoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveDefault(Usuario usuario) {
        List<Parametro> parametroList = parametroRepository.findByGrupo(ConstantUtil.TIPOS_PAGOS_DEFAULT);
        for (Parametro parametro : parametroList) {
            TipoPago tipoPago = new TipoPago();
            tipoPago.setNombre(parametro.getDescripcion());
            tipoPago.setUsuarioId(usuario);
            tipoPagoRepository.save(tipoPago);
        }
    }
}
