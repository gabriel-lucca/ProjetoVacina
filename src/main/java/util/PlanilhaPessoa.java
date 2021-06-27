package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.vo.PessoaVO;

public class PlanilhaPessoa {
	/**
	 * Gera uma planilha Excel (formato .xlsx) a partir de uma lista de pessoa
	 * 
	 * @param caminhoArquivo onde a planilha serÃ¡ salva
	 * @param pessoas       a lista de pessoas
	 * 
	 * @return uma mensagem informando ao usuario o que ocorreu
	 */
	
	public String gerarPlanilhaPessoas(String caminhoArquivo, List<PessoaVO> pessoas) {
		// Criar a planilha (Workbook)
		XSSFWorkbook planilha = new XSSFWorkbook();

		// Criar uma aba (Sheet)
		XSSFSheet aba = planilha.createSheet("Pessoas");

		int linhaAtual = 0;

		// Criar o cabeÃ§alho (header) 
		String[] nomesColunas = { "#", "Nome Pessoa", "Cpf", "Email", "Telefone", "Data de Nascimento", "Cidade", "Estado", "Endereço" };
		criarCabecalho(nomesColunas, aba, linhaAtual);

		// Preencher as linhas com os produtos
		criarLinhasPessoas(pessoas, aba, linhaAtual);

		// Salvar o arquivo gerado no disco
		return salvarNoDisco(planilha, caminhoArquivo, ".xlsx");
	}

	private void criarLinhasPessoas(List<PessoaVO> pessoas, XSSFSheet aba, int posicaoLinhaAtual) {
		for (PessoaVO p : pessoas) {
			// criar uma nova linha na planilha
			XSSFRow linhaAtual = aba.createRow(posicaoLinhaAtual);

			// Preencher as cÃ©lulas com os atributos Pessoa p
			linhaAtual.createCell(0).setCellValue(p.getIdPessoa());
			linhaAtual.createCell(1).setCellValue(p.getNome());
			linhaAtual.createCell(2).setCellValue(p.getCpf());
			linhaAtual.createCell(3).setCellValue(p.getEmail());
			linhaAtual.createCell(4).setCellValue(p.getTelefone());
			linhaAtual.createCell(5).setCellValue(java.sql.Date.valueOf(p.getDataNascimento()));
			linhaAtual.createCell(6).setCellValue(p.getCidade());
			linhaAtual.createCell(7).setCellValue(p.getEstado());
			linhaAtual.createCell(8).setCellValue(p.getEndereco());

			posicaoLinhaAtual++;
		}

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