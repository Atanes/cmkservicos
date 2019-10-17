function preencher_cpf(el) {
	document.getElementById('senha').value = el.value.replace(/\D/g, '');
	document.getElementById('login').value = el.value.replace(/\D/g, '');
}
