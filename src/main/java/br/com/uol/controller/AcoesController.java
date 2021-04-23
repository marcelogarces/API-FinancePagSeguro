package br.com.uol.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.uol.helper.CSVHelper;
import br.com.uol.model.Acao;
import br.com.uol.service.AcaoService;

@RestController
@RequestMapping(value = "/api/v1/pagseguro/acoes")
public class AcoesController {

	@Autowired
	private AcaoService acaoService;


	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (CSVHelper.hasCSVFormat(file)) {
			try {
				acaoService.carregarAcoes(file);
				message = "Upload das ações realizada com sucesso" + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				message = "Não foi possível fazer upload do arquivo:" + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}
		message = "Faça upload de um arquivo csv!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> inserirAcao(@RequestBody Acao acao) throws Exception {
		acaoService.inserirAcao(acao);  
		Object body = "Ação criada com sucesso!";
		return new ResponseEntity<Object>(body, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/codigo/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarAcaoPorCodigo(@PathVariable String codigo) {
		
		Optional<Acao> optionalPessoa = acaoService.obterAcaoPorCodigo(codigo);
		
		if(optionalPessoa.isPresent()) {
			return ResponseEntity.ok().body(optionalPessoa.get());
		}else {
			Object body = "Ação não encontrada!";
			return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarAcaoPorNome(@PathVariable String nome) {
		
		Optional<Acao> optionalPessoa = acaoService.obterAcaoPorNome(nome);
		
		if(optionalPessoa.isPresent()) {
			return ResponseEntity.ok().body(optionalPessoa.get());
		}else {
			Object body = "Ação não encontrada!";
			return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?>listarAcoes(){
		List<Acao> acoes = acaoService.listarAcoes();
		if(acoes!=null && !acoes.isEmpty()) {
			return ResponseEntity.ok().body(acoes);
		}else {
			Object body = "Não existem ações cadastradas!";
			return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/codigo/{codigo}",method = RequestMethod.DELETE)
	public ResponseEntity<?> removerPessoa(@PathVariable String codigo) {
		
		  Optional<Acao> optional = acaoService.obterAcaoPorCodigo(codigo);
		
		  if(optional.isPresent()) {
			  	acaoService.removerAcao(optional.get());
			  	Object body = "Ação removida com sucesso!";
				return new ResponseEntity<Object>(body, HttpStatus.OK);
		  }else {
			  Object body = "Ação não encontrada!";
			  return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
		  }
	}
}
