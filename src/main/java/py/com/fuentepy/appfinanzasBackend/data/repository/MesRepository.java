package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Mes;

@Repository
public interface MesRepository extends JpaRepository<Mes, Integer> {

    Page<Mes> findAllByOrderByIdAsc(Pageable pageable);

    Page<Mes> findAll(Pageable pageable);


}
