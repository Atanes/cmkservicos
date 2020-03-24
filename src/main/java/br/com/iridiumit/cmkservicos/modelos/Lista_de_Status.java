package br.com.iridiumit.cmkservicos.modelos;

public enum Lista_de_Status {
	
	AGENDADO("Agendado"),
	ELABORADO("Elaborado"),
	AGUARDANDO_PEDIDO("Aguardando Pedido"),
	ATENDIMENTO("Em atendimento"), 
	CANCELADO("Cancelado"),
	VALIDADO("Validado"),
	FINALIZADO("Finalizado");
	
	private String descricao;
	
	Lista_de_Status(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
