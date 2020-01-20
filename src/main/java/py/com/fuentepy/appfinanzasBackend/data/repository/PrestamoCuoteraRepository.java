package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.data.entity.PrestamoCuotera;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;

@Repository
public interface PrestamoCuoteraRepository extends JpaRepository<PrestamoCuotera, Long> {

    List<PrestamoCuotera> findByPrestamoIdAndUsuarioId(Prestamo prestamo, Usuario usuario);

    List<PrestamoCuotera> findByPrestamoIdAndUsuarioIdAndEstadoCuotaOrderByNumeroCuota(Prestamo prestamoId, Usuario usuarioId, String cuotaPendiente);

    PrestamoCuotera findByNumeroCuotaAndPrestamoIdAndUsuarioId(Integer numeroCuota, Prestamo prestamoId, Usuario usuarioId);
}
