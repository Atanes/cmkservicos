package br.com.iridiumit.cmkservicos.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.iridiumit.cmkservicos.modelos.Usuario;
import br.com.iridiumit.cmkservicos.repository.Clientes;
import br.com.iridiumit.cmkservicos.repository.Usuarios;
import br.com.iridiumit.cmkservicos.utils.GenerateCSVReport;
import br.com.iridiumit.cmkservicos.utils.GenerateExcelReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@RequestMapping("/relatorio")
public class RelatorioController {
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Usuarios usuarios;
	
	@GetMapping("/clientes")
	public void clientes(HttpServletResponse response) throws JRException, IOException {
		imprimir("Clientes", clientes.findAll(), response);
	}

	@GetMapping("/usuarios")
	public void usuarios(HttpServletResponse response) throws JRException, IOException {
		imprimir("Usuarios", usuarios.findAll(), response);
	}
	
	
	public void imprimir(String rel, List<?> lista, HttpServletResponse response) throws JRException, IOException {
		
		//parametros = parametros == null ? parametros = new HashMap<>() : parametros;
		
		// Pega o arquivo .jasper localizado em resources
		InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/" + rel + ".jasper");
		
		// Cria o objeto JaperReport com o Stream do arquivo jasper
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		// Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no caso uma conexão ao banco de dados
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(lista));

		// Configura a respota para o tipo PDF
		response.setContentType("application/pdf");
		// Define que o arquivo pode ser visualizado no navegador e também nome final do arquivo
		// para fazer download do relatório troque 'inline' por 'attachment'
		response.setHeader("Content-Disposition", "inline; filename=" + rel + ".pdf");

		// Faz a exportação do relatório para o HttpServletResponse
		final OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}
	
	@GetMapping(value = "/usuarios_Excel")
	public ResponseEntity<InputStreamResource> excelCustomersReport() throws IOException {
		List<Usuario> users = (List<Usuario>) usuarios.findAll();
		ByteArrayInputStream in = GenerateExcelReport.usersToExcel(users);
		// return IO ByteArray(in);
		HttpHeaders headers = new HttpHeaders();
		// set filename in header
		headers.add("Content-Disposition", "attachment; filename=users.xlsx");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}
	
	@GetMapping(value = "/usuarios_CSV")
	public void csvUsers(HttpServletResponse response) throws IOException {
		List<Usuario> users = (List<Usuario>) usuarios.findAll();
		GenerateCSVReport.writeUsers(response.getWriter(), users);
		response.setHeader("Content-Disposition", "attachment; filename=AllUsersCSVReport.csv");
	}
	
}