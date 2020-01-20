package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Movimiento;
import py.com.fuentepy.appfinanzasBackend.data.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.data.entity.PrestamoPago;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;

@Repository
public interface PrestamoPagoRepository extends JpaRepository<PrestamoPago, Long> {

    List<PrestamoPago> findByMovimientoIdAndUsuarioId(Movimiento movimientoId, Usuario usuarioId);

    List<PrestamoPago> findByPrestamoIdAndUsuarioId(Prestamo prestamo, Usuario usuario);
}
