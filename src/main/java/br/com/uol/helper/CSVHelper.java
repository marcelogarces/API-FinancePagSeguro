package br.com.uol.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import br.com.uol.model.Acao;

public class CSVHelper {

	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Acao> csvToAcoes(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withDelimiter(';'))) {

			List<Acao> acoes = new ArrayList<Acao>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				Acao acao = new Acao();
				acao.setCodigo(csvRecord.get(0));
				acao.setAcao(csvRecord.get(1));
				acao.setQuantidadeTeorica(Double.parseDouble(csvRecord.get(2)));
				acao.setParticipacao(Double.parseDouble(csvRecord.get(3)));

				acoes.add(acao);
			}

			return acoes;
		} catch (IOException e) {
			throw new RuntimeException("falha ao converter arquivo CSV:" + e.getMessage());
		}
	}

}
