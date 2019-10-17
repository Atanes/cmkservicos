package br.com.iridiumit.cmkservicos.relatorio;

import java.util.List;

import br.com.iridiumit.cmkservicos.modelos.Equipamento;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EquipamentoREL {
	
	private String pathToReportPackage; // Caminho para o package onde estão armazenados os relatorios Jarper
	
	//Recupera os caminhos para que a classe possa encontrar os relatórios
	public EquipamentoREL() {
		this.pathToReportPackage = "src/main/resources/relatorios/";
	}
	
	
	//Imprime/gera uma lista de Clientes
	public void imprimir(List<Equipamento> equipamentos) throws Exception	
	{
		
		JasperReport report = JasperCompileManager.compileReport(this.getPathToReportPackage() + "Equipamnetos.jrxml");
		
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(equipamentos));
 
		JasperExportManager.exportReportToPdfFile(print, getPathToReportPackage() + "Relatorio_de_Equipamentos.pdf");
		
	}
 
	public String getPathToReportPackage() {
		return this.pathToReportPackage;
	}

}
