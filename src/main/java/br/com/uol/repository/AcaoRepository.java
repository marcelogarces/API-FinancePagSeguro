package br.com.uol.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.uol.model.Acao;

@Repository
public interface AcaoRepository extends CrudRepository<Acao, Long> {

	@Query("SELECT a from Acao a where a.codigo = :codigo")
	Optional<Acao> obterAcaoPorCodigo(String codigo);	

	@Query("SELECT a from Acao a where a.acao = :nome")
	Optional<Acao> obterAcaoPorNome(String nome);	
}
