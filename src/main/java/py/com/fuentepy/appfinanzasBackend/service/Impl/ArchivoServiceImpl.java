package py.com.fuentepy.appfinanzasBackend.service.Impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;
import py.com.fuentepy.appfinanzasBackend.data.repository.ArchivoRepository;
import py.com.fuentepy.appfinanzasBackend.resource.archivo.ArchivoModel;
import py.com.fuentepy.appfinanzasBackend.service.ArchivoService;

import java.util.List;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    private static final Log LOG = LogFactory.getLog(ArchivoServiceImpl.class);

    @Autowired
    private ArchivoRepository archivoRepository;

    @Override
    @Transactional(readOnly = true)
    public Archivo findFotoPerfil(Long usuarioId) {
        Archivo archivo = null;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        List<Archivo> archivos = archivoRepository.findByUsuarioIdAndTablaIdAndTablaNombre(usuario, usuarioId, "usuarios");
        if (archivos != null && !archivos.isEmpty()) {
            archivo = archivos.get(0);
        }
        return archivo;
    }

    @Override
    public boolean save(Archivo archivo) throws Exception {
        try {
            archivoRepository.save(archivo);
        } catch (Exception e) {
            throw new Exception("No se pudo guardar el Archivo! " + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean saveList(List<ArchivoModel> archivos, Long tablaId, String tablaNombre, Usuario usuario) throws Exception {
        for (ArchivoModel archivoModel : archivos) {
            Archivo archivo = new Archivo();
            archivo.setTablaId(tablaId);
            archivo.setTablaNombre(tablaNombre);
            archivo.setContentType(archivoModel.getContentType());
            archivo.setNombre(archivoModel.getNombre());
            archivo.setDato(Base64.decodeBase64(archivoModel.getDato()));
            archivo.setUsuarioId(usuario);
            try {
                archivoRepository.save(archivo);
            } catch (Exception e) {
                throw new Exception("No se pudieron guardar los Archivos! " + e.getMessage());
            }
        }
        return true;
    }

}
