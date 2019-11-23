package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Presupuesto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {

    List<Presupuesto> findByUsuarioIdOrderByIdDesc(Usuario usuario);

    Optional<Presupuesto> findByIdAndUsuarioId(Long id, Usuario usuario);

    Page<Presupuesto> findByUsuarioId(Usuario usuario, Pageable pageable);

}
