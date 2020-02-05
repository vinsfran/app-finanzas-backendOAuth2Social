package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Mes;
import py.com.fuentepy.appfinanzasBackend.data.entity.Presupuesto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {

//    @Query(value = "SELECT p from Presupuesto p where p.usuarioId = :usuario order by p.anio desc, p.mesId.id desc")
    List<Presupuesto> findByUsuarioIdOrderByAnioDescMesIdDesc(Usuario usuario);

    Optional<Presupuesto> findByIdAndUsuarioId(Long id, Usuario usuario);

    Page<Presupuesto> findByUsuarioId(Usuario usuario, Pageable pageable);

    Presupuesto findByUsuarioIdAndAnioAndMesId(Usuario usuario, Integer anio, Mes mes);

}
