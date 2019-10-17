/**
 * Base para as mascaras de entrada de dados do sistema usando jquery.mask
 */

$(function() {
	
	$(".fone").inputmask({
		mask : [ "(99) 9999-9999", "(99) 99999-9999", ],
		keepStatic : true
	});
	
	$('.date').mask('00/00/0000');
	$('.time').mask('00:00:00');
	$('.date_time').mask('00/00/0000 00:00:00');
	$('.cep').mask('00000-000');
	$('.phone_us').mask('(000) 000-0000');
	$('.integer').mask("#.##0", {
		reverse : true
	});
	$('.mixed').mask('AAA 000-S0S');
	$('.sonumeros').mask('0#');
	$('.cpf').mask('000.000.000-00', {
		reverse : true
	});
	$('.cnpj').mask('00.000.000/0000-00', {
		reverse : true
	});
	$('.telefone').mask('(00) 0 0000-0000');
	$('.dinheiro').mask('#.##0,00', {
		reverse : true
	});
	$('.dinheiro2').mask('R$ #.##0,00', {
		reverse : true
	});
	$('.estado').mask('AA');
	$('.rg').mask('00.000.000-A');
	$('.placaCarro').mask('AAA-0000');
	$('.horasMinutos').mask('00:00');
	$('.cartaoCredito').mask('0000 0000 0000 0000');
	$('.percent').mask('00,0%', {clearifnotmatch: true}, {reverse: true});
	$('.percent2').mask('00,0', {clearifnotmatch: true}, {reverse: true});

	$('.porcentagem').mask('P', {
		translation : {
			'P' : {
				pattern : /[\d\.,]/,
				recursive : true
			}
		},
		onKeyPress : function(val, e, field, options) {
			var old_value = $(field).data('oldValue') || '';

			val = val.trim();
			val = val.replace(',', '.');
			val = val.length > 0 ? val : '0';

			// Transformando múltiplos pontos em um único ponto
			val = val.replace(/[\.]+/, '.');

			// Verificando se o valor contém mais de uma ocorrência de ponto
			var dot_occurrences = (val.match(/\./g) || []).length > 1;

			// Verificando se o valor está de acordo com a sintaxe do float
			var is_float = /[-+]?[\d]*\.?[\d]+/.test(val);

			if (dot_occurrences || !is_float) {
				val = old_value;
			}

			// Força o valor a ficar no intervalo de 0 à 100
			val = parseFloat(val) >= 100 ? '100' : val;
			val = parseFloat(val) < 0 ? '0' : val;

			$(field).val(val).data('oldValue', val);
		}
	});
});
