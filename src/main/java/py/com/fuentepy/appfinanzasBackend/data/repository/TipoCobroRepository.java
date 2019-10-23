package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoCobro;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoCobroRepository extends JpaRepository<TipoCobro, Long> {

    List<TipoCobro> findByUsuarioId(Usuario usuario);

    Optional<TipoCobro> findByIdAndUsuarioId(Long id, Usuario usuario);

    Page<TipoCobro> findByUsuarioId(Usuario usuario, Pageable pageable);
}
