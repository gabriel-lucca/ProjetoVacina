package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.vo.AplicacaoVacinaVO;

public class PlanilhaAplicacaoVacina {
	/**
	 * Gera uma planilha Excel (formato .xlsx) a partir de uma lista de aplicacao
	 * 
	 * @param caminhoArquivo onde a planilha sera salva
	 * @param vacinas       a lista de aplicacao vacina
	 * 
	 * @return uma mensagem informando ao usuario o que ocorreu
	 */
	
	XSSFWorkbook planilha = new XSSFWorkbook();
	XSSFSheet aba ;
	
	public String gerarPlanilhaAplicacaoVacina(String caminhoArquivo, List<AplicacaoVacinaVO> aplicacao) {
		// Criar a planilha (Workbook)

		// Criar uma aba (Sheet)
		aba = planilha.createSheet("Relatório da Aplicação Vacina"); 

		int linhaAtual = 0;

		// Criar o cabeÃƒÂ§alho (header)
		String[] nomesColunas = { "Nome", "CPF", "Vacina", "Dose", "Data aplicação","ID Pessoa"};
		criarCabecalho(nomesColunas, aba, linhaAtual);
		linhaAtual++;
		// Preencher as linhas com as vacinas
		criarLinhasAplicacao(aplicacao, aba, linhaAtual);

		// Salvar o arquivo gerado no disco 
		return salvarNoDisco(planilha, caminhoArquivo, ".xlsx");
	}
	
	private void criarCabecalho(String[] nomesColunas, XSSFSheet aba, int posicaoLinhaAtual) {
		Row linhaAtual = aba.createRow(posicaoLinhaAtual);

		posicaoLinhaAtual++;
		// Para mudar o estilo:
		// https://stackoverflow.com/questions/43467253/setting-style-in-apache-poi
		
		XSSFFont headerFont = planilha.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 20);
		headerFont.setColor(IndexedColors.BLACK.index);
		
		CellStyle headerStyle = planilha.createCellStyle();
		headerStyle.setFont(headerFont);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex()); //cor de fundo
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		
		for (int i = 0; i < nomesColunas.length; i++) {
			Cell novaCelula = linhaAtual.createCell(i);
			novaCelula.setCellValue(nomesColunas[i]);
			novaCelula.setCellStyle(headerStyle);
		}
	}

	private void criarLinhasAplicacao( List<AplicacaoVacinaVO> aplicacao, XSSFSheet aba, int posicaoLinhaAtual) {
		for (AplicacaoVacinaVO a : aplicacao) {
			// criar uma nova linha na planilha
			XSSFRow linhaAtual = aba.createRow(posicaoLinhaAtual);
			
			//adicionando bordas
			XSSFCellStyle style = planilha.createCellStyle(); //criei o CellStyle
			XSSFCellStyle centro = planilha.createCellStyle(); //criei o CellStyle
			XSSFFont headerFont = planilha.createFont();
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			
			/* adding heading style */
			style.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex()); //cor de fundo
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerFont.setBold(true);

			headerFont.setFontHeightInPoints((short) 14);
			style.setFont(headerFont);
			
			
			//style.setAlignment(CellStyle.ALIGN_CENTER);
			//style.setAlignment(HorizontalAlignment.CENTER); //texto centralizado
			
            //style.setAlignment(Alignment.CENTER); //texto centralizado
            //style.setFillForegroundColor(IndexedColors.YELLOW.getIndex()); //cor de fundo
            style.setBorderBottom(BorderStyle.THIN); //borda de baixo
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); //cor borda baixo
            style.setBorderLeft(BorderStyle.THIN); //borda da esquerda
            style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); //cor borda esquerda
            style.setBorderRight(BorderStyle.THIN); //borda direita
            style.setRightBorderColor(IndexedColors.BLACK.getIndex()); //cor borda direita
            style.setBorderTop(BorderStyle.THIN); //borda de cima
            style.setTopBorderColor(IndexedColors.BLACK.getIndex()); //cor borda cima
            
            // texto centralizado data nascimento, estado, id
            centro.setAlignment(HorizontalAlignment.CENTER);
            centro.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex()); //cor de fundo
            centro.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerFont.setBold(true);

			headerFont.setFontHeightInPoints((short) 14);
			centro.setFont(headerFont);
			
			centro.setBorderBottom(BorderStyle.THIN); //borda de baixo
			centro.setBottomBorderColor(IndexedColors.BLACK.getIndex()); //cor borda baixo
			centro.setBorderLeft(BorderStyle.THIN); //borda da esquerda
			centro.setLeftBorderColor(IndexedColors.BLACK.getIndex()); //cor borda esquerda
			centro.setBorderRight(BorderStyle.THIN); //borda direita
			centro.setRightBorderColor(IndexedColors.BLACK.getIndex()); //cor borda direita
			centro.setBorderTop(BorderStyle.THIN); //borda de cima
			centro.setTopBorderColor(IndexedColors.BLACK.getIndex()); //cor borda cima
			

			// Preencher as celulas com os atributos aplicacaoVacina a
			Cell cell; 
			cell = linhaAtual.createCell(0);cell.setCellValue(a.getPessoa().getNome());;cell.setCellStyle(style);aba.autoSizeColumn((short) 0);
			cell = linhaAtual.createCell(1);cell.setCellValue(a.getPessoa().getCpf());cell.setCellStyle(style);aba.autoSizeColumn((short) 1);
			cell = linhaAtual.createCell(2);cell.setCellValue(a.getVacina().getNomeVacina());cell.setCellStyle(style);aba.autoSizeColumn((short) 2);
			cell = linhaAtual.createCell(3);cell.setCellValue(a.getVacina().getQuantidadeDoses());cell.setCellStyle(centro);aba.autoSizeColumn((short) 3);
			
			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dataAplicacao = formatador.format(a.getDataAplicacao());
			
			cell = linhaAtual.createCell(4);cell.setCellValue(dataAplicacao);cell.setCellStyle(centro);aba.autoSizeColumn((short) 4);
			cell = linhaAtual.createCell(5);cell.setCellValue(a.getPessoa().getIdPessoa());cell.setCellStyle(centro);aba.autoSizeColumn((short) 5);
			

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
			// TODO lanÃ§ar exceÃ§ao de negaÃ§ao (para poder capturar as causas no controller
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