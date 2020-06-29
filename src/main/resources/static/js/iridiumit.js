function mostraDataHoraInicioFim(){
	if(!document.getElementById("mparada").checked){
		document.getElementById("mparadadatahora").style.display = "none";
		document.getElementById("mliberadadatahora").style.display = "none";
	}else{
		document.getElementById("mparadadatahora").style.display = "initial";
		document.getElementById("mliberadadatahora").style.display = "initial";
	}
}

function mostraDataHoraParada(){
	if(!document.getElementById("mparada").checked){
		document.getElementById("mparadadatahora").style.display = "none";
	}else{
		document.getElementById("mparadadatahora").style.display = "initial";
	}
}

function equipApto(){
	if(document.getElementById("apto").checked){
		document.getElementById("aptocomresalvas").checked = false;
		document.getElementById("naoapto").checked = false;
		document.getElementById("mostraNaoApto").style.display = "none";
		document.getElementById("mostraResalvas").style.display = "none";
	}
}

function equipNaoApto(){
	if(document.getElementById("naoapto").checked){
		document.getElementById("aptocomresalvas").checked = false;
		document.getElementById("apto").checked = false;
		document.getElementById("mostraNaoApto").style.display = "initial";
		document.getElementById("mostraResalvas").style.display = "none";
	}else{
		document.getElementById("mostraNaoApto").style.display = "none";
	}
}

function equipAptoResalvas(){
	if(document.getElementById("aptocomresalvas").checked){
		document.getElementById("apto").checked = false;
		document.getElementById("naoapto").checked = false;
		document.getElementById("mostraResalvas").style.display = "initial";
		document.getElementById("mostraNaoApto").style.display = "none";
	}else{
		document.getElementById("mostraResalvas").style.display = "none";
	}
}