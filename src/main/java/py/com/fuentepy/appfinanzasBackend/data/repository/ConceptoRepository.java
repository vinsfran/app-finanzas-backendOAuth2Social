package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Concepto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConceptoRepository extends JpaRepository<Concepto, Long> {

    List<Concepto> findByUsuarioIdOrderByIdDesc(Usuario usuario);

    Optional<Concepto> findByIdAndUsuarioId(Long id, Usuario usuario);

    Page<Concepto> findByUsuarioId(Usuario usuario, Pageable pageable);

}
