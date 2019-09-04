package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Prestamo;
import py.com.fuentepy.appfinanzasBackend.data.entity.PrestamoPago;

@Repository
public interface PrestamoPagoRepository extends JpaRepository<PrestamoPago, Long> {

    Page<PrestamoPago> findAll(Pageable pageable);

    //    @Query("select p from PrestamoPago p where p.prestamoId=?1")
    Page<PrestamoPago> findByPrestamoId(Prestamo prestamoId, Pageable pageable);
}
