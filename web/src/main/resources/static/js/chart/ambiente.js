/**
 * @file ambiente.js
 * @description Questo script gestisce la scheda "Ambiente" nell'applicazione.
 * Recupera e visualizza dati ambientali, incluso un grafico a linee dinamico
 * che mostra l'andamento di vari parametri ambientali.
 *
 * Operazioni:
 * - Recupera il contenuto HTML iniziale per la scheda ambiente.
 * - Inizializza un DataTable per visualizzare i dati ambientali tabulari.
 * - Recupera un elenco di parametri ambientali disponibili per popolare un menu a tendina.
 * - Genera e aggiorna dinamicamente un grafico a linee basato sul parametro selezionato.
 * - Il grafico a linee mostra l'andamento del parametro selezionato nel tempo,
 * con tooltip che mostrano valori specifici, date e variazioni percentuali.
 * - Fornisce la funzionalità per scaricare il grafico a linee generato come PDF.
 */

let ambienteLoaded = false;

document.getElementById('ambiente-tab').addEventListener('click', function() {
	if (!ambienteLoaded) {
		fetch('/ambiente')
			.then(response => response.text())
			.then(html => {
				document.getElementById('tab-ambiente').innerHTML = html;
			})
			.catch(error => {
				console.error('Errore nel caricamento dei dati ambientali:', error);
			});

		setTimeout(() => {
			if (!$.fn.DataTable.isDataTable('#ambiente-datatable')) {
				new DataTable('#ambiente-datatable', {
					responsive: true,
					language: {
						entries: {
							_: 'dati ambientali',
							1: 'ambiente'
						}
					},
					pageLength: 5,
					lengthMenu: [[5, 10, 15], [5, 10, 15]]
				});
			}
			
			const collapseAmbienteLineChart = document.getElementById('collapseAmbienteLineChart');
			let ambienteLineChartInstance;
			
			collapseAmbienteLineChart.addEventListener('shown.bs.collapse', function() {
				if (!ambienteLineChartInstance) {
					ambienteLineChartDinamico(ambienteLineChartInstance);
					downloadAmbienteLineChart();
				}
			});
			
		}, 1000);
	}
});

//LINECHART AMBIENTE
function ambienteLineChartDinamico(ambienteLineChartInstance) {

	fetch("/getListaParametriAmbientali")
		.then(res => res.json())
		.then(data => {
			const select = document.getElementById("parameterSelect");

			data.forEach(parametro => {
				const option = document.createElement("option");
				option.value = parametro;
				option.textContent = parametro;
				select.appendChild(option);
			});

			select.addEventListener("change", function() {
				const parametroSelezionato = this.value;

				fetch("/getVariazioneValoriParametriAmbiente")
					.then(res => res.json())
					.then(dataMap => {
						const dtoList = dataMap[parametroSelezionato];
						
						const dataDinamico = [];
						
						dtoList.forEach(dto => {
							dataDinamico.push({
								x: dto.dataRilevazione, y: dto.valoreParametro, variazione: dto.variazionePercentuale
							})
						});
						
						const ctx = document.getElementById("ambienteLineChart").getContext("2d");

						if (ambienteLineChartInstance) {
							ambienteLineChartInstance.destroy();
						}

						ambienteLineChartInstance = new Chart(ctx, {
							type: "line",
							data: {
								datasets: [{
									label: `Andamento ${parametroSelezionato}`,
									data: dataDinamico,
									fill: false,
									borderColor: generaColoreRandom()
								}]
							},
							options: {
								responsive: true,
								maintainAspectRatio: false,
								scales: {
									y: {
										beginAtZero: false,
										title: { display: true, text: 'Valore' },
										ticks: {
											callback: function(value) {
												var unitaMisura;
												if (parametroSelezionato == 'TEMPERATURA') {
													unitaMisura = '°C';
												} else if (parametroSelezionato == 'UMIDITA') {
													unitaMisura = '%';
												} else if (parametroSelezionato == 'PRECIPITAZIONI') {
													unitaMisura = 'ml';
												} else if (parametroSelezionato == 'IRRAGGIAMENTO') {
													unitaMisura = 'kWh/m²';
												} else if (parametroSelezionato == 'OMBREGGIAMENTO') {
													unitaMisura = 'Fsh';
												}
												return `${value} ${unitaMisura}`;
											}
										}
									},
									x: {
										type: 'time',
										time: {
											unit: 'day',
											tooltipFormat: 'yyyy-MM-dd',
											displayFormats: {
												day: 'yyyy-MM-dd'
											},
											beginAtZero: true,
											title: { display: true, text: 'Data Rilevazione' }
										}
									}
								}, plugins: {
									tooltip: {
										callbacks: {
											title: function() {
												return `Andamento ${parametroSelezionato}`;
											},
											label: function(context) {
												const dataRilevazione = context.raw.x;
												const value = context.raw.y;
												const variazionePercentuale = context.raw.variazione;
												
												var messaggioVariazionePositiva;
												if(variazionePercentuale > 0) {
													messaggioVariazionePositiva = `Variazione rispetto all'anno precedente: +${variazionePercentuale} %`
												} else {
													messaggioVariazionePositiva = `Variazione rispetto all'anno precedente: ${variazionePercentuale} %`
												}
												
												var unitaMisura;
												if (parametroSelezionato == 'TEMPERATURA') {
													unitaMisura = '°C';
												} else if (parametroSelezionato == 'UMIDITA') {
													unitaMisura = '%';
												} else if (parametroSelezionato == 'PRECIPITAZIONI') {
													unitaMisura = 'ml';
												} else if (parametroSelezionato == 'IRRAGGIAMENTO') {
													unitaMisura = 'kWh/m²';
												} else if (parametroSelezionato == 'OMBREGGIAMENTO') {
													unitaMisura = 'Fsh';
												}
												
												return [
													`Data rilevazione: ${dataRilevazione}`,
													`Valore: ${value} ${unitaMisura}`,
													messaggioVariazionePositiva
												];
											}
										}
									}
								}
							}
						});
					});
			});
		});
}

function downloadAmbienteLineChart() {
	document.getElementById("downloadAmbienteLineChart").addEventListener("click", async () => {
		const { jsPDF } = window.jspdf;

		const pdf = new jsPDF({
			orientation: 'landscape',
			unit: 'px',
			format: 'a4'
		});

		const canvas = document.getElementById("ambienteLineChart");
		const imageData = canvas.toDataURL("image/png", 1.0);

		const width = pdf.internal.pageSize.getWidth();
		const height = canvas.height * (width / canvas.width);

		pdf.text("Andamento Parametri Ambientali", 40, 30);
		pdf.addImage(imageData, 'PNG', 40, 50, width - 80, height);

		pdf.save("andamento_parametri_ambientali.pdf");
	});
}