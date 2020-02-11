package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Dispositivo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    List<Dispositivo> findByUsuarioId(Usuario usuario);
}
