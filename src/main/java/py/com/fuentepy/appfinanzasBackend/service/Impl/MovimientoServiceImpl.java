package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.converter.MovimientoConverter;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.*;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;
import py.com.fuentepy.appfinanzasBackend.resource.movimiento.MovimientoModel;
import py.com.fuentepy.appfinanzasBackend.service.MovimientoService;
import py.com.fuentepy.appfinanzasBackend.util.ConstantUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private static final Log LOG = LogFactory.getLog(MovimientoServiceImpl.class);

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArchivoServiceImpl archivoService;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoModel> findAll() {
        return MovimientoConverter.listEntitytoListModel(movimientoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoModel> findByUsuarioId(Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return MovimientoConverter.listEntitytoListModel(movimientoRepository.findByUsuarioId(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoModel> findAll(Pageable pageable) {
        return MovimientoConverter.pageEntitytoPageModel(pageable, movimientoRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoModel> findByUsuarioId(Long usuarioId, Pageable pageable) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return MovimientoConverter.pageEntitytoPageModel(pageable, movimientoRepository.findByUsuarioId(usuario, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoModel findByIdAndUsuarioId(Long id, Long usuarioId) {
        MovimientoModel model = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Optional<Movimiento> optional = movimientoRepository.findByIdAndUsuarioId(id, usuario);
        if (optional.isPresent()) {
            model = MovimientoConverter.entityToModel(optional.get());
        }
        return model;
    }

    @Override
    @Transactional
    public Movimiento registrarMovimiento(Movimiento movimiento, List<ArchivoModel> archivoModels) {
        movimiento = movimientoRepository.saveAndFlush(movimiento);
        if (archivoModels != null && !archivoModels.isEmpty()) {
            try {
                archivoService.saveList(archivoModels, movimiento.getId(), ConstantUtil.MOVIMIENTOS, movimiento.getUsuarioId().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return movimiento;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        movimientoRepository.deleteById(id);
    }

    @Override
    public List<Movimiento> movimientosByUsuarioAndRangoFecha(Long usuarioId, Date fechaInicio, Date fechaFin) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return movimientoRepository.findByUsuarioIdRangoFecha(usuario, fechaInicio, fechaFin);
    }

    @Override
    public List<Movimiento> findByUsuarioIdAndTablaIdAndTablaNombre(Long usuarioId, Long tablaId, String tablaNombre) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return movimientoRepository.findByUsuarioIdAndTablaIdAndTablaNombre(usuario, tablaId, tablaNombre);
    }
}
