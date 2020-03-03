package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.Parametro;

import java.util.List;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {

    List<Parametro> findByGrupo(String grupo);
}
