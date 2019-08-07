package py.com.fuentepy.appfinanzasBackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.entity.TipoAhorro;

@Repository
public interface TipoAhorroRepository extends JpaRepository<TipoAhorro, Integer> {

    Page<TipoAhorro> findAllByOrderByIdAsc(Pageable pageable);

    Page<TipoAhorro> findAll(Pageable pageable);
}
