package py.com.fuentepy.appfinanzasBackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.entity.Concepto;
import py.com.fuentepy.appfinanzasBackend.entity.Usuario;

import java.util.List;

@Repository
public interface ConceptoRepository extends JpaRepository<Concepto, Integer> {

    List<Concepto> findByUsuarioIdOrGlobalEnable(Usuario usuario, Boolean globalEnabled);

    Page<Concepto> findByUsuarioId(Usuario usuario, Pageable pageable);

    Concepto findByCodigoConcepto(String codigoConcepto);
}
