$(function() {
	
	var modal = $('#modalCadastroRapidoContato');
	var botaoSalvar = modal.find('.js-modal-cadastro-contato-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	
	var inputNomeContato = $('#nomeContato');
	var inputEmailContato = $('#emailContato');
	var inputFoneContato =  $('#foneContato');
	var inputDepartContato = $('#departContato');
	var idCliente = $('#idCliente');
	
	var url = '/contatos/incluirContato';
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-contato');
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click', onBotaoSalvarClick);
	
	function onModalShow() {
		inputNomeContato.focus();
	}
	
	function onModalClose() {
		inputNomeContato.val('');
		inputEmailContato.val('');
		inputFoneContato.val('');
		inputDepartContato.val('');
		containerMensagemErro.addClass('d-none');
		form.find('.js-label-alert').removeClass('text-danger');
		form.find('.js-input-alert').removeClass('border-danger');
	}
	
	function onBotaoSalvarClick() {
		var nomeContato = inputNomeContato.val().trim();
		var emailContato = inputEmailContato.val().trim();
		var foneContato = inputFoneContato.val().trim();
		var departContato = inputDepartContato.val().trim();
		var Cliente = idCliente.val().trim();
		var contato = { id: Cliente, nome: nomeContato, email: emailContato,
				fone: foneContato, depart: departContato};
		
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		
		if(nomeContato == ''){
			inputNomeContato.focus();
			containerMensagemErro.removeClass('d-none');
			containerMensagemErro.html('<span>Campo nome é obrigatório!</span>');
			form.find('.js-label-nome-alert').addClass('text-danger');
			form.find('.js-input-nome-alert').addClass('border-danger');
		    return false;
		}else{
			form.find('.js-label-nome-alert').removeClass('text-danger');
			form.find('.js-input-nome-alert').removeClass('border-danger');
		}
		if (!filter.test(emailContato)) {
			inputEmailContato.focus();
			containerMensagemErro.removeClass('d-none');
			containerMensagemErro.html('<span>Por favor, informe um email válido!</span>');
			form.find('.js-label-email-alert').addClass('text-danger');
			form.find('.js-input-email-alert').addClass('border-danger');
		    return false;
		}else{
			form.find('.js-label-email-alert').removeClass('text-danger');
			form.find('.js-input-email-alert').removeClass('border-danger');
		}
		
		if(foneContato == ''){
			inputFoneContato.focus();
			containerMensagemErro.removeClass('d-none');
			containerMensagemErro.html('<span>Campo telefone é obrigatório!</span>');
			form.find('.js-label-fone-alert').addClass('text-danger');
			form.find('.js-input-fone-alert').addClass('border-danger');
		    return false;
		}else{
			form.find('.js-label-fone-alert').removeClass('text-danger');
			form.find('.js-input-fone-alert').removeClass('border-danger');
		}
		
		if(departContato == ''){
			inputDepartContato.focus();
			containerMensagemErro.removeClass('d-none');
			containerMensagemErro.html('<span>Campo departamento é obrigatório!</span>');
			form.find('.js-label-depart-alert').addClass('text-danger');
			form.find('.js-input-depart-alert').addClass('border-danger');
		    return false;
		}else{
			form.find('.js-label-depart-alert').removeClass('text-danger');
			form.find('.js-input-depart-alert').removeClass('border-danger');
		}
		
		$.ajax({
			type : 'GET',
			url: url,
	        data : contato,
	        
			error: 	onErroSalvandoContato,
			success: onContatoSalvo
		});
	}
	
	function onErroSalvandoContato(obj) {
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('d-none');
		containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		form.find('.js-label-alert').addClass('text-danger');
		form.find('.js-input-alert').addClass('border-danger');
		inputNomeContato.focus();
	}
	
	function onContatoSalvo(nome) {
		console.log(nome)
		var comboContato = $('#cbosolicitante');
		comboContato.append('<option value="' + nome + '" selected="selected">' + nome + '</option>');
		comboContato.val(nome);
		modal.modal('hide');
	}
});

	