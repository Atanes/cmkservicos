package br.com.iridiumit.cmkservicos.modelos;

public enum StatusAtendimento {
	
	AGENDADO("Agendado"),
	ELABORADO("Elaborado"),
	AGUARDANDO("Aguardando Pedido"),
	ATENDIMENTO("Em atendimento"), 
	CANCELADO("Cancelado"),
	VALIDADO("Validado"),
	FINALIZADO("Finalizado");
	
	private String descricao;
	
	StatusAtendimento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
