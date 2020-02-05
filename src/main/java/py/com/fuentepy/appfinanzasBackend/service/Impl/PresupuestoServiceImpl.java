package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.PresupuestoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Mes;
import py.com.fuentepy.appfinanzasBackend.data.entity.Presupuesto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.PresupuestoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.presupuesto.PresupuestoModel;
import py.com.fuentepy.appfinanzasBackend.resource.presupuesto.PresupuestoRequestNew;
import py.com.fuentepy.appfinanzasBackend.resource.presupuesto.PresupuestoRequestUpdate;
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
        return PresupuestoConverter.listEntitytoListModel(presupuestoRepository.findByUsuarioIdOrderByAnioDescMesIdDesc(usuario));
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
    public PresupuestoModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        PresupuestoModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Presupuesto> optional = presupuestoRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = PresupuestoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public void create(PresupuestoRequestNew request, Long usuarioId) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Mes mes = new Mes();
        mes.setId(request.getMesId());
        try {
            Presupuesto entity = presupuestoRepository.findByUsuarioIdAndAnioAndMesId(usuario, request.getAnio(), mes);
            if (entity != null) {
                throw new Exception("Ya existe el Presupueto para " + request.getAnio() + "/" + request.getMesId());
            }
            presupuestoRepository.save(PresupuestoConverter.presupuestoNewToPresupuestoEntity(request, usuarioId));
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean update(PresupuestoRequestUpdate request, Long usuarioId) {
        Presupuesto entity = presupuestoRepository.save(PresupuestoConverter.presupuestoUpdateToPresupuestoEntity(request, usuarioId));
        if (entity != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        presupuestoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Presupuesto findByUsuarioIdAnioMes(Long usuarioId, Integer anio, Integer mesId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Mes mes = new Mes();
        mes.setId(mesId);
        return presupuestoRepository.findByUsuarioIdAndAnioAndMesId(usuario, anio, mes);
    }
}
