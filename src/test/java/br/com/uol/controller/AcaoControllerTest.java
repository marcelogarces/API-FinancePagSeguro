package br.com.uol.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.uol.model.Acao;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class AcaoControllerTest {
	
	
	 @Autowired
	 private WebApplicationContext wac;

	 @Autowired
	 protected MockMvc mockMvc;
	
	 @InjectMocks
	 private AcoesController acoesControllerMock;
	 
	 @Mock
	 private Acao acao;
	 
	 
	 @Test
	 @Order(1)
	 public void inserirAcaoSucessoTest() throws Exception {
		 
		 

		 mockMvc.perform(
	                post("/api/v1/pagseguro/acoes")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(getPayloadRequestSucesso()))
	                .andExpect(content().string("Ação criada com sucesso!"))
	                .andExpect(status().isCreated());
	  }
	 
	 
	 @Test
	 @Order(2)
	 public void consultarAcaoPorCodigoTest() throws Exception {
		ResultActions response = mockMvc.perform(
	                get(("/api/v1/pagseguro/acoes/codigo/BGH3"))
	                        .contentType(MediaType.APPLICATION_JSON));
	               
	                 response.andExpect(status().isOk())        
	                .andExpect(content().string(containsString("BGH3 S.A")));
	 }
	 
	 @Test
	 @Order(3)
	 public void consultarAcaoNaoExistenteTest() throws Exception {
		ResultActions response = mockMvc.perform(
	                get(("/api/v1/pagseguro/acoes/codigo/BGH9"))
	                        .contentType(MediaType.APPLICATION_JSON));
	                 response.andExpect(status().isNotFound())        
	                 .andExpect(content().string("Ação não encontrada!"));
	 }
	
	 @Test
	 @Order(4)
	 public void consultarAcaoPorNomeTest() throws Exception {
		ResultActions response = mockMvc.perform(
	                get(("/api/v1/pagseguro/acoes/nome/BGH3 S.A"))
	                        .contentType(MediaType.APPLICATION_JSON));
	               
	                 response.andExpect(status().isOk())        
	                .andExpect(content().string(containsString("BGH3 S.A")));
	 }
	 

	 
	 @Test
	 @Order(5)
	 public void deletarAcaoTest() throws Exception {
		ResultActions response = mockMvc.perform(
	                delete(("/api/v1/pagseguro/acoes/codigo/BGH3"))
	                        .contentType(MediaType.APPLICATION_JSON));
	                 response.andExpect(status().isOk())        
	                 .andExpect(content().string("Ação removida com sucesso!"));
	 }
	 
	 
	 
	 private String getPayloadRequestSucesso() {
		 return "{\r\n" + 
			 		"  \"acao\": \"BGH3 S.A\",\r\n" + 
			 		"  \"codigo\": \"BGH3\",\r\n" + 
			 		"  \"participacao\": 433.5,\r\n" + 
			 		"  \"quantidadeTeorica\": 333.4\r\n" + 
			 		"}";
	 }
	 
	 @BeforeEach
	 public void setUp() {
	    mockMvc = MockMvcBuilders
	        .webAppContextSetup(wac)
	        .addFilter((request, response, chain) -> {
	          response.setCharacterEncoding("UTF-8");
	          chain.doFilter(request, response);
	        }, "/*")
	        .build();
	    
 
	    
	  }

}
