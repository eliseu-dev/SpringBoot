package curso.springboot.repository;

import curso.springboot.model.Profissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ProfissaoRepository extends JpaRepository<Profissao, Long> {

}
