package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.EntidadFinanciera;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntidadFinancieraRepository extends JpaRepository<EntidadFinanciera, Long> {

    List<EntidadFinanciera> findByUsuarioId(Usuario usuario);

    Optional<EntidadFinanciera> findByIdAndUsuarioId(Long id, Usuario usuario);

    Page<EntidadFinanciera> findByUsuarioId(Usuario usuario, Pageable pageable);
}
