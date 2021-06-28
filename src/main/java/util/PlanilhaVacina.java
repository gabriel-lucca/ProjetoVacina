package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.vo.VacinaVO;


public class PlanilhaVacina {
	/**
	 * Gera uma planilha Excel (formato .xlsx) a partir de uma lista de vacina
	 * 
	 * @param caminhoArquivo onde a planilha serÃ¡ salva
	 * @param vacinas       a lista de vacinas
	 * 
	 * @return uma mensagem informando ao usuario o que ocorreu
	 */
	
	XSSFWorkbook planilha = new XSSFWorkbook();
	XSSFSheet aba ;
	public String gerarPlanilhaVacinas(String caminhoArquivo, List<VacinaVO> vacinas) {
		// Criar a planilha (Workbook)

		// Criar uma aba (Sheet)
		aba = planilha.createSheet("Vacinas");

		int linhaAtual = 0;

		// Criar o cabeÃ§alho (header)
		String[] nomesColunas = { "#", "Nome Pesquisador Responsável", "Pais Origem", "Nome Vacina", "Data Inicio Pesquisa", "Quantidade de Doses", "Intervalo das doses" };
		criarCabecalho(nomesColunas, aba, linhaAtual);
		linhaAtual++;
		// Preencher as linhas com as vacinas
		criarLinhasVacinas(vacinas, aba, linhaAtual);

		// Salvar o arquivo gerado no disco 
		return salvarNoDisco(planilha, caminhoArquivo, ".xlsx");
	}
	
	private void criarCabecalho(String[] nomesColunas, XSSFSheet aba, int posicaoLinhaAtual) {
		Row linhaAtual = aba.createRow(posicaoLinhaAtual);

		posicaoLinhaAtual++;
		// Para mudar o estilo:
		// https://stackoverflow.com/questions/43467253/setting-style-in-apache-poi
		for (int i = 0; i < nomesColunas.length; i++) {
			Cell novaCelula = linhaAtual.createCell(i);
			novaCelula.setCellValue(nomesColunas[i]);
		}
	}

	private void criarLinhasVacinas(List<VacinaVO> vacinas, XSSFSheet aba, int posicaoLinhaAtual) {
		for (VacinaVO v : vacinas) {
			// criar uma nova linha na planilha
			XSSFRow linhaAtual = aba.createRow(posicaoLinhaAtual);

			// Preencher as celulas com os atributos Vacina v
			linhaAtual.createCell(0).setCellValue(v.getIdVacina());
			linhaAtual.createCell(1).setCellValue(v.getNomePesquisadorResponsavel());
			linhaAtual.createCell(2).setCellValue(v.getPaisOrigem());
			linhaAtual.createCell(3).setCellValue(v.getNomeVacina());
			linhaAtual.createCell(4).setCellValue(java.sql.Date.valueOf(v.getDataInicioPesquisa()));
			linhaAtual.createCell(5).setCellValue(v.getQuantidadeDoses());
			linhaAtual.createCell(6).setCellValue(v.getIntervaloDoses());

			posicaoLinhaAtual++;
		}

	}


	private String salvarNoDisco(XSSFWorkbook planilha, String caminhoArquivo, String extensao) {
		String mensagem = "";
		FileOutputStream saida = null;

		try {
			saida = new FileOutputStream(new File(caminhoArquivo + extensao));
			planilha.write(saida);
			mensagem = "Planilha gerada com sucesso!";
		} catch (FileNotFoundException e) {
			// TODO lançar exceçao de negaçao (para poder capturar as causas no controller
			// ou tela)
			mensagem = "Erro ao tentar salvar planilha em: " + caminhoArquivo + extensao;
			System.out.println("Causa: " + e.getMessage());
		} catch (IOException e) {
			mensagem = "Erro ao tentar salvar planilha em: " + caminhoArquivo + extensao;
			System.out.println("Causa: " + e.getMessage());
		} finally {
			if (saida != null) {
				try {
					saida.close();
					planilha.close();
				} catch (IOException e) {
					mensagem = "Erro ao tentar salvar planilha em: " + caminhoArquivo + extensao;
					System.out.println("Causa: " + e.getMessage());
				}
			}
		}

		return mensagem;
	}

}