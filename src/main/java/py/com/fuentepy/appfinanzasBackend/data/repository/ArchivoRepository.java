package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    List<Archivo> findByUsuarioIdAndTablaIdAndTablaNombre(Usuario usuario, Long tablaId, String tablaNombre);

    Archivo findByUsuarioIdAndTablaIdAndTablaNombreAndNombre(Usuario usuario, Long tablaId, String tablaNombre, String nombreArchivo);

    @Query(value = "select u.id from Archivo u where u.usuarioId = :usuario and u.tablaId = :tablaId and u.tablaNombre = :tablaNombre")
    List<Long> listArchivoIdByUsuarioIdAndTablaIdAndTablaNombre(Usuario usuario, Long tablaId, String tablaNombre);
}
