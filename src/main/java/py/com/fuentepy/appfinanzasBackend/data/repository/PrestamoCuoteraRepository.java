package py.com.fuentepy.appfinanzasBackend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.fuentepy.appfinanzasBackend.data.entity.PrestamoCuotera;

@Repository
public interface PrestamoCuoteraRepository extends JpaRepository<PrestamoCuotera, Long> {
}
