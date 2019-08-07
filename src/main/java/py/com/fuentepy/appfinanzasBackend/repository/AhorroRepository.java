package py.com.fuentepy.appfinanzasBackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.entity.Ahorro;
import py.com.fuentepy.appfinanzasBackend.entity.Usuario;

import java.util.Date;
import java.util.List;

@Repository
public interface AhorroRepository extends JpaRepository<Ahorro, Long> {

    List<Ahorro> findByUsuarioId(Usuario usuario);

    Page<Ahorro> findByUsuarioId(Usuario usuario, Pageable pageable);

    Long countByUsuarioId(Usuario usuario);

    @Query(value = "select u from Ahorro u where u.usuarioId = :usuario and u.fechaInicio BETWEEN :startDate and :endDate")
    List<Ahorro> findByUsuarioIdRangoFecha(@Param("usuario") Usuario usuario, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
