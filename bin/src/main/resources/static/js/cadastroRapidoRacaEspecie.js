$(function() {
	
	var modal = $('#modalCadastroRapidoRacaEspecie');
	var botaoSalvar = modal.find('.js-modal-cadastro-raca_especie-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = '/raca_especie/incluirRaca';
	var inputNomeRaca = $('#nomeRaca');
	var inputNomeEspecie = $('#especie');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-raca_especie');
	
	var comboEspecie = $('#comboespecie');
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click', onBotaoSalvarClick);
	
	function onModalShow() {
		inputNomeRaca.focus();
		inputNomeEspecie.val(comboEspecie.val());
	}
	
	function onModalClose() {
		inputNomeRaca.val('');
		inputNomeEspecie.val('');
		containerMensagemErro.addClass('d-none');
		form.find('.js-label-alert').removeClass('text-danger');
		form.find('.js-input-alert').removeClass('border-danger');
	}
	
	function onBotaoSalvarClick() {
		var nomeRaca = inputNomeRaca.val().trim();
		var nomeEspecie = inputNomeEspecie.val().trim();
		
		var obj = { raca: nomeRaca, especie: nomeEspecie };
		
		$.ajax({
			type : 'GET',
			url: url,
			
			crossDomain : true,
	        data : ({
	            raca : nomeRaca ,
	            especie : nomeEspecie
	        }),
	        contentType : "application/json; charset=utf-8",
	        dataType : "json",
	        
			error: onErroSalvandoRacaEspecie,
			success: onRacaEspecieSalvo
		});
	}
	
	function onErroSalvandoRacaEspecie(obj) {
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('d-none');
		containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		form.find('.js-label-alert').addClass('text-danger');
		form.find('.js-input-alert').addClass('border-danger');
		inputNomeRaca.focus();
	}
	
	function onRacaEspecieSalvo(raca) {
		var comboRaca = $('#comboraca');
		comboRaca.append('<option value=' + raca.id + '>' + raca.nome + '</option>');
		comboRaca.val(raca.id);
		modal.modal('hide');
	}
	
});