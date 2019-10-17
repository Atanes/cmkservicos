package br.com.iridiumit.cmkservicos.modelos;

public enum TipoAtendimento {
		
	EMERGENCIAL("Emergencial"),
	CORRETIVAPROGRAMADA("Corretiva Programada"),
	DIARIODEOBRAS("Diário de Obras"),
	PREVENTIVA("Preventiva");
	
	private String descricao;
	
	TipoAtendimento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
