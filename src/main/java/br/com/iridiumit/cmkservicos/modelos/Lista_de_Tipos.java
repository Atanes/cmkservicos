package br.com.iridiumit.cmkservicos.modelos;

public enum Lista_de_Tipos {
		
	EMERGENCIAL("Emergencial"),
	CORRETIVA_PROGRAMADA("Corretiva Programada"),
	DIARIO_DE_OBRAS("Diário de Obras"),
	PREVENTIVA("Preventiva");
	
	private String descricao;
	
	Lista_de_Tipos(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
