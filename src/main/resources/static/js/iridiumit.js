function mostraCampo(){
	if(!document.getElementById("mparada").checked){
		document.getElementById("mparadadatahora").style.display = "none";
	}else{
		document.getElementById("mparadadatahora").style.display = "initial";
	}
}