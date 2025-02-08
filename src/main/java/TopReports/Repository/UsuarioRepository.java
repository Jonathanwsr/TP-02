package TopReports.Repository;

import TopReports.Enity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByUserName(String userName);
}


