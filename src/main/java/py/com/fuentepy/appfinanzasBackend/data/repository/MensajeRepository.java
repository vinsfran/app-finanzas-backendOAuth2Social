package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Mensaje;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.Date;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Date> {

    List<Mensaje> findByUsuarioId(Usuario usuario);
}
