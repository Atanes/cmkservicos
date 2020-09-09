var CMKService = CMKService || {};

CMKService.PesquisaRapidaCliente = (function() {
	
    class PesquisaRapidaCliente {
        constructor() {
            this.pesquisaRapidaClientesModal = $('#pesquisaRapidaClientes');
            this.nomeInput = $('#nomeClienteModal');
            this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-clientes-btn');
            this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaClientes');
            this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-cliente').html();
            this.template = Handlebars.compile(this.htmlTabelaPesquisa);
            this.mensagemErro = $('.js-mensagem-erro');
        }
        iniciar() {
            this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
            this.pesquisaRapidaClientesModal.on('shown.bs.modal', onModalShow.bind(this));
        }
    }
	
	
	function onModalShow() {
		this.nomeInput.focus();
	}
	
	function onPesquisaRapidaClicado(event) {
		event.preventDefault();
		
		$.ajax({
			url: this.pesquisaRapidaClientesModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.nomeInput.val()
			}, 
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});
	}
	
	function onPesquisaConcluida(resultado) {
		this.mensagemErro.addClass('hidden');
		
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);
		
		var tabelaClientePesquisaRapida = new CMKService.TabelaClientePesquisaRapida(this.pesquisaRapidaClientesModal);
		tabelaClientePesquisaRapida.iniciar();
	} 
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('d-none');
	}
	
	return PesquisaRapidaCliente;
	
}());

CMKService.TabelaClientePesquisaRapida = (function() {
	
    class TabelaClientePesquisaRapida {
        constructor(modal) {
            this.modalCliente = modal;
            this.cliente = $('.js-cliente-pesquisa-rapida');
        }
        iniciar() {
            this.cliente.on('click', onClienteSelecionado.bind(this));
        }
    }
	
	
	function onClienteSelecionado(evento) {
		this.modalCliente.modal('hide');
		
		var clienteSelecionado = $(evento.currentTarget);
		$('#nomeCliente').val(clienteSelecionado.data('nome'));
		$('#codigoCliente').val(clienteSelecionado.data('codigo'));
	}
	
	return TabelaClientePesquisaRapida;
	
}());

$(function() {
	var pesquisaRapidaCliente = new CMKService.PesquisaRapidaCliente();
	pesquisaRapidaCliente.iniciar();
});