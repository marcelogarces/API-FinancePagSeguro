package br.com.uol.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YahooFinanceClient {

	private final String ENDPOINT_YAHOO_FINANCE = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/";

	public String autoComplete(String codigo) {

		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.add("x-rapidapi-key", "0dc9dbda8emsh3054c53232j32j3afbajsn5c517191ff92");
		headers.add("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<Object> retorno = client.exchange(ENDPOINT_YAHOO_FINANCE + "auto-complete?q=" + codigo,
				HttpMethod.GET, entity, Object.class);

		LinkedHashMap<Object, Object> hashMap = (LinkedHashMap<Object, Object>) retorno.getBody();
		ArrayList<Object> quotes = (ArrayList<Object>) hashMap.get("quotes");
		LinkedHashMap<Object, Object> hashMap2 = (LinkedHashMap<Object, Object>) quotes.get(0);
		String symbol = (String) hashMap2.get(3);

		return symbol;
	}

}
