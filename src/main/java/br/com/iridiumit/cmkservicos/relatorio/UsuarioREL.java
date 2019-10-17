package br.com.iridiumit.cmkservicos.relatorio;

import java.util.List;

import br.com.iridiumit.cmkservicos.modelos.Usuario;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class UsuarioREL {
	
	private String pathToReportPackage; // Caminho para o package onde estão armazenados os relatorios Jarper
	private String pathToReports; // Caminho para armazenar os relatórios gerados antes de apresentar na tela
	
	//Recupera os caminhos para que a classe possa encontrar os relatórios
	public UsuarioREL() {
		this.pathToReportPackage = "src/main/relatorios/";
		this.pathToReports = "src/main/resources/relatorios/";
	}
	
	
	//Imprime/gera uma lista de Usuários
	public void imprimir(List<Usuario> usuarios) throws Exception	
	{
		
		JasperReport report = JasperCompileManager.compileReport(this.getPathToReportPackage() + "Usuarios.jrxml");
		
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(usuarios));
 
		JasperExportManager.exportReportToPdfFile(print, this.pathToReports + "Relatorio_de_Usuarios.pdf");		
	}
 
	public String getPathToReportPackage() {
		return this.pathToReportPackage;
	}


	public String getPathToReports() {
		return pathToReports;
	}
	
}
