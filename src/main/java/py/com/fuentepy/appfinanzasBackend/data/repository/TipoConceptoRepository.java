package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.TipoConcepto;

@Repository
public interface TipoConceptoRepository extends JpaRepository<TipoConcepto, Integer> {

    Page<TipoConcepto> findAllByOrderByIdAsc(Pageable pageable);

    Page<TipoConcepto> findAll(Pageable pageable);

}
