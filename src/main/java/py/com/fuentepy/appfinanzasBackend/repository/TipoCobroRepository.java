package py.com.fuentepy.appfinanzasBackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.entity.TipoCobro;

@Repository
public interface TipoCobroRepository extends JpaRepository<TipoCobro, Integer> {

    Page<TipoCobro> findAllByOrderByIdAsc(Pageable pageable);

    Page<TipoCobro> findAll(Pageable pageable);
}
