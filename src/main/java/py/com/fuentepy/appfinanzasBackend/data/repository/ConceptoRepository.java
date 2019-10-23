package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Concepto;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;

@Repository
public interface ConceptoRepository extends JpaRepository<Concepto, Integer> {

    List<Concepto> findByUsuarioId(Usuario usuario);

    Page<Concepto> findByUsuarioId(Usuario usuario, Pageable pageable);

}
