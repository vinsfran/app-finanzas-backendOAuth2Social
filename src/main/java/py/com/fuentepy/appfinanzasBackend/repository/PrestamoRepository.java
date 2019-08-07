package py.com.fuentepy.appfinanzasBackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.entity.Usuario;

import java.util.Date;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByUsuarioId(Usuario usuario);

    Page<Prestamo> findByUsuarioId(Usuario usuario, Pageable pageable);

    Long countByUsuarioId(Usuario usuario);

    @Query(value = "select u from Prestamo u where u.usuarioId = :usuario and u.fechaVencimiento BETWEEN :startDate and :endDate")
    List<Prestamo> findByUsuarioIdRangoFecha(@Param("usuario") Usuario usuario, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
