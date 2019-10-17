$(function() {
	
	var modal = $('#modalCadastroFotoAnimal');
	var botaoSalvar = modal.find('.js-modal-cadastro-foto_animal-salvar-btn');
	var inputFile = modal.find('custom-file-input');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-foto_animal');
	
	var nome = null;
	var tipo = null;
	var urlFoto = null;
	
	botaoSalvar.on('click', onBotaoSalvarClick);
	
	modal.on('shown.bs.modal', onModalShow);
	
	function onModalShow() {
		$(document).on('change', '.custom-file-input', function (event) {
		    $(this).next('.custom-file-label').html(event.target.files[0].name);
		    urlFoto = window.URL.createObjectURL(event.target.files[0]);
		    nome = event.target.files[0].name;
		    tipo = event.target.files[0].type;
		})
	}	

	function onBotaoSalvarClick() {
		if(nome != null){
			/*document.getElementById('foto').value = nome;
		    document.getElementById('fotoType').value = tipo;*/
		    document.getElementById('fotoAnimal').src = urlFoto;
		}
		modal.modal('hide');
	}
	
});