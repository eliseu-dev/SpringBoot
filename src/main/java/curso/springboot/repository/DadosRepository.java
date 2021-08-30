package curso.springboot.repository;

import curso.springboot.model.Dados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DadosRepository extends JpaRepository<Dados, Long> {
    @Query("select d from Dados d where d.pessoa.id = ?1")
    public List<Dados> listaDados(Long pessoaid);
}
