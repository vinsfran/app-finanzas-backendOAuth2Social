package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Ahorro;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AhorroRepository extends JpaRepository<Ahorro, Long> {

    List<Ahorro> findByUsuarioId(Usuario usuario);

    Optional<Ahorro> findByIdAndUsuarioId(Long id, Usuario usuario);

    Page<Ahorro> findByUsuarioId(Usuario usuario, Pageable pageable);

    Long countByUsuarioId(Usuario usuario);

    @Query(value = "select u from Ahorro u where u.usuarioId = :usuario and u.fechaInicio BETWEEN :startDate and :endDate")
    List<Ahorro> findByUsuarioIdRangoFecha(@Param("usuario") Usuario usuario, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Ahorro> findByUsuarioIdAndEstado(Usuario usuario, boolean estado);

}
