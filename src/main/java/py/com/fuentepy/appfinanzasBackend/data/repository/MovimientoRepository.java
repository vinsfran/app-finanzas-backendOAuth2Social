package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.*;

import java.util.Date;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByUsuarioId(Usuario usuario);

    Page<Movimiento> findByUsuarioId(Usuario usuario, Pageable pageable);

    @Query(value = "select u from Movimiento u where u.usuarioId = :usuario and u.fechaMovimiento BETWEEN :startDate and :endDate")
    List<Movimiento> findByUsuarioIdRangoFecha(@Param("usuario") Usuario usuario, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Page<Movimiento> findByPrestamoId(Prestamo prestamoId, Pageable pageable);

    Page<Movimiento> findByAhorroId(Ahorro ahorroId, Pageable pageable);

    Page<Movimiento> findByTarjetaId(Tarjeta tarjetaId, Pageable pageable);
}
