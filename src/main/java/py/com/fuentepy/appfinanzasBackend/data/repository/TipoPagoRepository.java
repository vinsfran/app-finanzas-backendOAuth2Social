package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoPago;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago, Long> {

    List<TipoPago> findByUsuarioId(Usuario usuario);

    Optional<TipoPago> findByIdAndUsuarioId(Long id, Usuario usuario);

    Page<TipoPago> findByUsuarioId(Usuario usuario, Pageable pageable);
}
