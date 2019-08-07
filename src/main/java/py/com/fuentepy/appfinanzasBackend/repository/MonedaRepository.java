package py.com.fuentepy.appfinanzasBackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.entity.Moneda;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda, Integer> {

    Page<Moneda> findAllByOrderByIdAsc(Pageable pageable);

    Page<Moneda> findAll(Pageable pageable);


}
