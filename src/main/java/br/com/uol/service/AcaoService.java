package br.com.uol.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.uol.helper.CSVHelper;
import br.com.uol.model.Acao;
import br.com.uol.repository.AcaoRepository;

@Service
public class AcaoService {

	@Autowired
	private AcaoRepository acaoRepository;

	public void carregarAcoes(MultipartFile file) {
		try {
			List<Acao> acoes = CSVHelper.csvToAcoes(file.getInputStream());
			acaoRepository.saveAll(acoes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Acao> listarAcoes() {
		return (List<Acao>) acaoRepository.findAll();
	}

	public Optional<Acao> obterAcaoPorCodigo(String codigo) {

		return acaoRepository.obterAcaoPorCodigo(codigo);
	}

	public Optional<Acao> obterAcaoPorNome(String nome) {

		return acaoRepository.obterAcaoPorNome(nome);
	}

	public void inserirAcao(Acao acao) throws Exception {
		

		

		Optional<Acao> optional = acaoRepository.obterAcaoPorCodigo(acao.getCodigo());
		if (!optional.isPresent()) {
			acaoRepository.save(acao);
		} else {
			// throw new BusinessException("CPF já cadastrado!");
		}
	}

	public void removerAcao(Acao acao) {
		acaoRepository.delete(acao);
	}

}
