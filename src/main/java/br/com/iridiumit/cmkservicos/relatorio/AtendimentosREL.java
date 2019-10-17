package br.com.iridiumit.cmkservicos.relatorio;

import java.util.List;

import br.com.iridiumit.cmkservicos.modelos.Atendimento;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AtendimentosREL {
	
	private String pathToReportPackage; // Caminho para o package onde estão armazenados os relatorios Jarper
	
	//Recupera os caminhos para que a classe possa encontrar os relatórios
	public AtendimentosREL() {
		this.pathToReportPackage = "src/main/resources/relatorios/";
	}
	
	
	//Imprime/gera uma lista de Atendimentos
	public void imprimir(List<Atendimento> ras) throws Exception	
	{
		
		JasperReport report = JasperCompileManager.compileReport(this.getPathToReportPackage() + "Atendimentos.jrxml");
		
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(ras));
 
		JasperExportManager.exportReportToPdfFile(print, getPathToReportPackage() + "Relatorio_de_Atendimentos.pdf");
		
	}
 
	public String getPathToReportPackage() {
		return this.pathToReportPackage;
	}

}
