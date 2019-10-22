package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.AhorroTipo;
import py.com.fuentepy.appfinanzasBackend.data.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoAhorroRepository extends JpaRepository<AhorroTipo, Integer> {

    List<AhorroTipo> findByUsuarioId(Usuario usuario);

    Optional<AhorroTipo> findByIdAndUsuarioId(Long id, Usuario usuario);

    Page<AhorroTipo> findByUsuarioId(Usuario usuario, Pageable pageable);
}
