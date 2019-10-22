package br.com.iridiumit.cmkservicos.modelos;

public enum TipoAtendimento {
		
	EMERGENCIAL("Emergencial"),
	CORRETIVA_PROGRAMADA("Corretiva Programada"),
	DIARIO_DE_OBRAS("Diário de Obras"),
	PREVENTIVA("Preventiva");
	
	private String descricao;
	
	TipoAtendimento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
