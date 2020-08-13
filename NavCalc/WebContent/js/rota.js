/**
 * 
 */
var rota = document.getElementById("rota");
var linhas = 5;
rotas();

function rotas(){
	rota.innerHTML = "Para cada ponto, digite o nome do fixo ou uma coordenada no formato ######(N/S)#######(W/E):<br>";
	for (i = 0; i < linhas; i++){
		rota.innerHTML += '<label>Fixo ' + (i+1) + ': </label><input type="text" name="fixo'+i+'" placeholder="nome ou coordenada"><br>';
	}
	rota.innerHTML += '<button onclick="add()">Adicionar</button>';
}

function add() {
	linhas++;
	rotas();
}