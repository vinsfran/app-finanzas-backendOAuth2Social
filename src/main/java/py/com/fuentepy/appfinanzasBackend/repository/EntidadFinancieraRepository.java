package py.com.fuentepy.appfinanzasBackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.entity.EntidadFinanciera;
import py.com.fuentepy.appfinanzasBackend.entity.Usuario;

import java.util.List;

@Repository
public interface EntidadFinancieraRepository extends JpaRepository<EntidadFinanciera, Integer> {

    List<EntidadFinanciera> findByUsuarioId(Usuario usuario);

    Page<EntidadFinanciera> findByUsuarioId(Usuario usuario, Pageable pageable);
}
