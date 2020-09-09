Cmkservicos.NovoApontamento = (function() {
	
    class NovoApontamento {
        constructor(autocomplete) {
            this.autocomplete = autocomplete;
            this.apontamentoContainer = $('js-tabela-apontamento-container');
        }
        iniciar() {
            this.autocomplete.on('novo-apontamento', onNovoApontamento.bind(this));
        }
    }
	
	
	function onNovoApontamento(evento, item){
		var resposta =	$.ajax({
			type : 'POST',
			url: item,
	        data : {
				codigoAtendimento : item.codigo
			}
		});
		
		resposta.done(onApontamentoAdicionado.bind(this))
	}
	
	function onApontamentoAdicionado(html){
		this.apontamentoContainer.html(html);
	}
	
	return NovoApontamento;
	
}());

	