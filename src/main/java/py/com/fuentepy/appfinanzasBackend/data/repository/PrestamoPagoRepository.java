package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.PrestamoPago;

@Repository
public interface PrestamoPagoRepository extends JpaRepository<PrestamoPago, Long> {
}
