package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("select u from Movimiento u " +
            "where u.usuarioId = :usuario " +
            "and u.fechaMovimiento BETWEEN :startDate and :endDate " +
            "order by u.id desc")
    List<Movimiento> findByUsuarioIdAndFechaMovimientoBetweensStarDateAndEndDateOrderByIdDesc(@Param("usuario") Usuario usuario, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select u from Movimiento u " +
            "where u.usuarioId = :usuario " +
            "and u.fechaMovimiento BETWEEN :startDate and :endDate " +
            "and (lower(u.numeroComprobante) LIKE lower(CONCAT('%', :parent, '%')) " +
            "or lower(u.detalle) LIKE lower(CONCAT('%', :parent, '%')) " +
            "or TO_CHAR(u.fechaMovimiento, 'YYYY-MM-DD') LIKE lower(CONCAT('%', :parent, '%')) " +
            "or CAST(u.monto AS text) LIKE lower(CONCAT('%', :parent, '%')) " +
            ") " +
            "order by u.id desc")
    List<Movimiento> findByUsuarioIdAndFechaMovimientoBetweensStarDateAndEndDateAndParentOrderByIdDesc(@Param("usuario") Usuario usuario, @Param("parent") String parent, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Optional<Movimiento> findByIdAndUsuarioId(Long id, Usuario usuario);

    Page<Movimiento> findByUsuarioId(Usuario usuario, Pageable pageable);

    @Query(value = "select u from Movimiento u where u.usuarioId = :usuario and u.fechaMovimiento BETWEEN :startDate and :endDate")
    List<Movimiento> findByUsuarioIdRangoFecha(@Param("usuario") Usuario usuario, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Movimiento> findByUsuarioIdAndTablaIdAndTablaNombreOrderByIdDesc(Usuario usuario, Long tablaId, String tablaNombre);

    @Query(value = "select u.id from Movimiento u where u.usuarioId = :usuario and u.tablaId = :tablaId and u.tablaNombre = :tablaNombre")
    List<Long> listMovimientoIdByUsuarioIdAndTablaIdAndTablaNombre(Usuario usuario, Long tablaId, String tablaNombre);

}
