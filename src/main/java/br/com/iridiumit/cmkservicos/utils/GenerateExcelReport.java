package br.com.iridiumit.cmkservicos.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.iridiumit.cmkservicos.modelos.Usuario;

public class GenerateExcelReport {

	public static ByteArrayInputStream usersToExcel(List<Usuario> users) throws IOException {
		String[] COLUMNs = { "Id", "CPF", "Nome", "E-mail", "Telefone" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Usuarios");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Header Row
			Row headerRow = sheet.createRow(0);

			// Table Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowIdx = 1;
			for (Usuario user : users) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(user.getId().toString());
				row.createCell(1).setCellValue(user.getCpf());
				row.createCell(2).setCellValue(user.getNome());
				row.createCell(3).setCellValue(user.getEmail());
				row.createCell(4).setCellValue(user.getTelefone1());

			}

			// Auto-size all the above columns
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
}
