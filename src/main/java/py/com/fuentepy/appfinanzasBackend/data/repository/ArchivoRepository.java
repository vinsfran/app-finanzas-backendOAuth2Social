package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Archivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    List<Archivo> findByUsuarioIdAndTablaIdAndTablaNombre(Usuario usuario, Long tablaId, String tablaNombre);
}
