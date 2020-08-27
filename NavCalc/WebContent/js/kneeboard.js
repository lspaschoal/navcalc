document.getElementById('btnAcionamento').style.visibility = "hidden";
document.getElementById('btnTaxi').style.visibility = "hidden";
document.getElementById('btnDecolagem').style.visibility = "hidden";

function validaAutonomia() {
	var autonomia_total = getMinutos(document.getElementById('idAutonomiaInicial').value);
	var autonomia_necessaria = getMinutos(document.getElementById('idTempoTotal').innerHTML) + 30;
	if (autonomia_total < autonomia_necessaria) {
		alert('Para realizar esse voo é necessária uma autonomia mínima de ' + (Math.floor(autonomia_necessaria / 60)) + ":" + (autonomia_necessaria % 60));
		document.getElementById('btnAcionamento').style.visibility = "hidden";
	} else {
		document.getElementById('btnAcionamento').style.visibility = "visible";
		document.getElementById('idAutonomiaDigitada').innerHTML = '<span id="idAutonomiaInicial" class="tempo" style="font-size: 25px;">' + document.getElementById('idAutonomiaInicial').value +'</span>'
	}
}

function doisdigitos(numero) {
	if (numero < 10) {
		return '0' + numero;
	} else {
		return '' + numero;
	}
}

function getMinutos(horas_minutos) {
	var horas = parseInt(horas_minutos.substring(0, 2));
	var minutos = parseInt(horas_minutos.substring(3));
	return horas * 60 + minutos;
}

function setAcionamento() {
	var acionamento = new Date();
	document.getElementById('idDateAcionamento').value = Math.abs(acionamento);
	document.getElementById('idHrAcionamento').innerHTML = doisdigitos(acionamento.getUTCHours()) + ':' + doisdigitos(acionamento.getMinutes());
	document.getElementById('btnAcionamento').style.visibility = "hidden";
	document.getElementById('btnTaxi').style.visibility = "visible";
}

function setTaxi() {
	var taxi = new Date();
	document.getElementById('idDateTaxi').value = Math.abs(taxi);
	document.getElementById('idHrTaxi').innerHTML = doisdigitos(taxi.getUTCHours()) + ':' + doisdigitos(taxi.getMinutes());
	document.getElementById('btnTaxi').style.visibility = "hidden";
	document.getElementById('btnDecolagem').style.visibility = "visible";
}

function setDecolagem() {
	// pega a data e hora atual
	var decolagem = new Date();
	// grava o valor da hora atual na idDateDecolagem
	document.getElementById('idDateDecolagem').value = Math.abs(decolagem);
	// registra o horário da decolagem na label idHrDecolagem
	document.getElementById('idHrDecolagem').innerHTML = doisdigitos(decolagem.getUTCHours()) + ':' + doisdigitos(decolagem.getMinutes());
	// desabilita o botão de decolagem
	document.getElementById('btnDecolagem').style.visibility = "hidden";
	// atualiza a primeira linha
	var estimado = new Date(Math.abs(decolagem) + getMinutos(document.getElementById('idEET0').innerHTML) * 60000);
	document.getElementById('idDateETA0').value = Math.abs(estimado);
	document.getElementById('idETA0').innerHTML = doisdigitos(estimado.getUTCHours()) + ':' + doisdigitos(estimado.getMinutes());
	var gasto = Math.floor((parseInt(document.getElementById('idDateETA0').value) - parseInt(document.getElementById('idDateAcionamento').value)) / 60000);
	var autonomia = getMinutos(document.getElementById('idAutonomiaInicial').innerHTML) - gasto;
	document.getElementById('idAutonomia0').innerHTML = doisdigitos(parseInt(Math.floor(autonomia / 60))) + ':' + doisdigitos(parseInt(autonomia % 60));
	// atualiza as demais linhas da tabela
	for (var i = 1; i < parseInt(document.getElementById('nTrechos').value); i++) {
		// atualiza os estimados de sobrevoo
		estimado = new Date(parseInt(document.getElementById('idDateETA' + (i - 1)).value) + getMinutos(document.getElementById('idEET' + i).innerHTML) * 60000);
		document.getElementById('idDateETA' + i).value = Math.abs(estimado);
		document.getElementById('idETA' + i).innerHTML = doisdigitos(estimado.getUTCHours()) + ':' + doisdigitos(estimado.getMinutes());
		// atualiza os estimados de atonomia
		gasto = Math.floor((parseInt(document.getElementById('idDateETA' + i).value) - parseInt(document.getElementById('idDateAcionamento').value)) / 60000);
		autonomia = getMinutos(document.getElementById('idAutonomiaInicial').innerHTML) - gasto;
		document.getElementById('idAutonomia' + i).innerHTML = doisdigitos(Math.floor(autonomia / 60)) + ':' + doisdigitos(autonomia % 60);
	}
	// habilita o botão para gravar o horário de sobrevoo do primeiro ponto
	document.getElementById('idReal0').innerHTML = '<button type="button" style="text-align: center; width: 29px; padding: 0" onclick="sobrevoo(0)"><img src="images/icons/cronometro.png" style="margin: 0;"></button>';
}

function sobrevoo(linha) {
	// horário real de sobrevoo
	var hora_real = new Date();
	document.getElementById('idDateReal' + linha).value = Math.abs(hora_real);
	document.getElementById('idReal' + linha).innerHTML = doisdigitos(hora_real.getUTCHours()) + ':' + doisdigitos(hora_real.getMinutes());
	// autonomia remanescente
	var gasto = Math.floor((Math.abs(hora_real) - parseInt(document.getElementById('idDateAcionamento').value)) / 60000);
	var autonomia = getMinutos(document.getElementById('idAutonomiaInicial').innerHTML) - gasto;
	document.getElementById('idAutonomia'+linha).innerHTML = doisdigitos(parseInt(Math.floor(autonomia / 60))) + ':' + doisdigitos(parseInt(autonomia % 60));
	if (linha < parseInt(document.getElementById('nTrechos').value)-1) {
		// atualiza a linha seguinte
		// estimado
		var novo_estimado = new Date(Math.abs(hora_real) + getMinutos(document.getElementById('idEET' + (linha + 1)).innerHTML) * 60000);
		document.getElementById('idDateETA' + (linha + 1)).value = Math.abs(novo_estimado);
		document.getElementById('idETA' + (linha + 1)).innerHTML = doisdigitos(novo_estimado.getUTCHours()) + ':' + doisdigitos(novo_estimado.getMinutes());
		// combustivel
		gasto = Math.floor((parseInt(document.getElementById('idDateETA' + (linha+1)).value) - parseInt(document.getElementById('idDateAcionamento').value)) / 60000);
		autonomia = getMinutos(document.getElementById('idAutonomiaInicial').innerHTML) - gasto;
		document.getElementById('idAutonomia' + (linha+1)).innerHTML = doisdigitos(Math.floor(autonomia / 60)) + ':' + doisdigitos(autonomia % 60);
		for (var i = (linha + 2); i < parseInt(document.getElementById('nTrechos').value); i++) {
			// estimado
			novo_estimado = new Date(parseInt(document.getElementById('idDateETA' + (i - 1)).value) + getMinutos(document.getElementById('idEET' + i).innerHTML) * 60000);
			document.getElementById('idDateETA' + i).value = Math.abs(novo_estimado);
			document.getElementById('idETA' + i).innerHTML = doisdigitos(novo_estimado.getUTCHours()) + ':' + doisdigitos(novo_estimado.getMinutes());
			// autonomia
			gasto = Math.floor((parseInt(document.getElementById('idDateETA' + i).value) - parseInt(document.getElementById('idDateAcionamento').value)) / 60000);
			autonomia = getMinutos(document.getElementById('idAutonomiaInicial').innerHTML) - gasto;
			document.getElementById('idAutonomia' + i).innerHTML = doisdigitos(Math.floor(autonomia / 60)) + ':' + doisdigitos(autonomia % 60);
		}
		// habilita o botão para gravar o horário de sobrevoo do próximo ponto
		document.getElementById('idReal' + (linha + 1)).innerHTML = '<button type="button" style="text-align: center; width: 29px; padding: 0" onclick="sobrevoo(' + (linha + 1) + ')"><img src="images/icons/cronometro.png" style="margin: 0;"></button>';
		//atualiza o tempo total
		var tempototal = document.getElementById('idDateETA'+((document.getElementById('nTrechos').value)-1)).value - document.getElementById('idDateDecolagem').value;
		tempototal = Math.floor(tempototal/60000);
		document.getElementById('idTempoTotal').innerHTML = doisdigitos(parseInt(Math.floor(tempototal / 60))) + ':' + doisdigitos(parseInt(tempototal % 60));
	}else{
		var tempototal = parseInt(document.getElementById('idDateReal'+((document.getElementById('nTrechos').value)-1)).value) - document.getElementById('idDateDecolagem').value;
		tempototal = Math.floor(tempototal/60000);
		document.getElementById('idTempoTotal').innerHTML = doisdigitos(parseInt(Math.floor(tempototal / 60))) + ':' + doisdigitos(parseInt(tempototal % 60));
	}
}
