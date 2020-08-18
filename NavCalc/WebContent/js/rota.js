/**
 * 
 */
//var rota = document.getElementById("rota");
var linhas = 5;
/*
//function rotas(){
//	rota.innerHTML = "Para cada ponto, digite o nome do fixo ou uma coordenada no formato 000000(N/S)0000000(W/E):<br>";
//	for (i = 0; i < linhas; i++){
		if(i<10){
			rota.innerHTML += '<label>Fixo 0' + (i+1) + ': </label><input type="text" id="fixo'+i+'" name="fixo'+i+'" placeholder="nome ou coordenada"><br>';
		}else{
			rota.innerHTML += '<label>Fixo ' + (i+1) + ': </label><input type="text" id="fixo'+i+'" name="fixo'+i+'" placeholder="nome ou coordenada"><br>';
		}
	}
	rota.innerHTML += '<button onclick="add()">Adicionar</button>';
}

rotas();
*/
function add() {
	var valores = [];
	// recupera os valores já digitados
	for(var i = 0; i < linhas; i++){
		valores.push(document.getElementById('fixo'+i).value);
	}
	console.log(valores);
	// acrescenta uma linha
	if (i<10){
		document.getElementById("rota").innerHTML += '<label>Fixo 0' + (linhas+1) + ':</label><input type="text" id="fixo'+linhas+'" name="fixo'+linhas+'" placeholder="nome ou coordenada"><br>';
	}else{
		document.getElementById("rota").innerHTML += '<label>Fixo ' + (linhas+1) + ':</label><input type="text" id="fixo'+linhas+'" name="fixo'+linhas+'" placeholder="nome ou coordenada"><br>';
	}
	// insere novamente os valores anteriores
	for(var i = 0; i < linhas; i++){
		document.getElementById('fixo'+i).value = valores[i];
	}
	linhas++;
}