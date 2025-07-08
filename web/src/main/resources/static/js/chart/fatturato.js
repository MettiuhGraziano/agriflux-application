/**
 * @file fatturato.js
 * @description Questo script gestisce la scheda "Fatturato" nell'applicazione.
 * Recupera e visualizza dati relativi al fatturato, incluso un grafico multi-linea
 * che confronta ricavi e spese per le parcelle di terreno selezionate nel tempo.
 *
 * Operazioni:
 * - Recupera il contenuto HTML iniziale per la scheda fatturato.
 * - Inizializza un DataTable per visualizzare i dati tabulari del fatturato.
 * - Popola una datalist con le parcelle di terreno ("terreni") disponibili.
 * - Genera dinamicamente un grafico multi-linea alla selezione dell'utente di una parcella di terreno
 * e un'azione di ricerca.
 * - Il grafico multi-linea visualizza l'andamento dei "Ricavi" e delle "Spese"
 * nel tempo per la parcella di terreno scelta.
 * - Fornisce la funzionalità per scaricare il grafico multi-linea generato come PDF.
 */

let fatturatoLoaded = false;

document.getElementById('fatturato-tab').addEventListener('click', function() {
	if (!fatturatoLoaded) {
		fetch('/fatturato')
			.then(response => response.text())
			.then(html => {
				document.getElementById('tab-fatturato').innerHTML = html;
			})
			.catch(error => {
				console.error('Errore nel caricamento del fatturato:', error);
			});

		setTimeout(() => {
			new DataTable('#fatturato-datatable', {
				responsive: true,
				ordering: true,
				language: {
					entries: {
						_: 'dati fatturato',
						1: 'fatturato'
					}
				},
				pageLength: 5,
				lengthMenu: [[5, 10, 15], [5, 10, 15]]
			});
			
			const collapseMultilineChartTerreno = document.getElementById('collapseMultilineChartTerreno');
			let multilineFatturatoChartInstance;

			collapseMultilineChartTerreno.addEventListener('shown.bs.collapse', function() {
				if (!multilineFatturatoChartInstance) {
					multilineFatturatoChart(multilineFatturatoChartInstance);
					downloadFatturatoLineChart();
				}
			});
			
		}, 1000);
	}
});

//MULTILINE CHART FATTURATO
function multilineFatturatoChart(multilineFatturatoChartInstance) {

	fetch("/findFatturatoRicaviSpese")
		.then(res => res.json())
		.then(data => {

			const terreniDatalistOptions = document.getElementById("terreniDatalistOptions");
			Object.keys(data).forEach(prodotto => {
				const option = document.createElement("option");
				option.value = prodotto;
				option.textContent = prodotto;
				terreniDatalistOptions.appendChild(option);
			});

			document.getElementById("terreniDataList").addEventListener("change", function() {
				const terrenoSelezionato = this.value;
				const dtoList = data[terrenoSelezionato]
				const pulsanteRicerca = document.getElementById("ricercaParticella");

				pulsanteRicerca.removeAttribute("disabled");
				pulsanteRicerca.addEventListener("click", function() {

					if (multilineFatturatoChartInstance) {
						multilineFatturatoChartInstance.destroy();
					}

					const labels = [];
					const ricavi = [];
					const spese = [];

					dtoList.forEach(dto => {
						labels.push(dto.annoRiferimento);
						ricavi.push(dto.ricaviVendita);
						spese.push(dto.speseGenerali);
					});

					const ctxLine = document.getElementById("fatturatoMultilineChart").getContext("2d");

					multilineFatturatoChartInstance = new Chart(ctxLine, {
						type: 'line',
						data: {
							labels: labels,
							datasets: [{
								label: `Ricavi`,
								data: ricavi,
								borderColor: 'rgb(173, 255, 47)',
								backgroundColor: 'rgb(173, 255, 47)',
								borderWidth: 2
							}, {
								label: `Spese`,
								data: spese,
								borderColor: 'rgb(220, 20, 60)',
								backgroundColor: 'rgb(220, 20, 60, 0.4)',
								borderWidth: 2,
								fill: true
							}]
						}, options: {
							responsive: true,
							maintainAspectRatio: false,
							scales: {
								y: {
									beginAtZero: true,
									title: { display: true, text: 'Valore' },
									ticks: {
										callback: function(value) {
											return value + ' €';
										}
									}
								},
								x: {
									title: { display: true, text: 'Anno Riferimento' },
									beginAtZero: false
								}
							}, plugins: {
								tooltip: {
									callbacks: {
										label: function(context) {
											const label = context.dataset.label;
											const value = context.formattedValue;
											return [
												`${label}: ${value} €`
											];
										}
									}
								}
							}, animations: {
								tension: {
									duration: 5000,
									easing: 'linear',
									from: 1,
									to: 0,
									loop: true
								}
							}
						}
					});
				});
			});
		});
}

function downloadFatturatoLineChart() {
	document.getElementById("downloadFatturatoLineChart").addEventListener("click", async () => {
		const { jsPDF } = window.jspdf;

		const pdf = new jsPDF({
			orientation: 'landscape',
			unit: 'px',
			format: 'a4'
		});

		const canvas = document.getElementById("fatturatoMultilineChart");
		const imageData = canvas.toDataURL("image/png", 1.0);

		const width = pdf.internal.pageSize.getWidth();
		const height = canvas.height * (width / canvas.width);

		pdf.text("Andamento Prezzo Ricavi/Spese", 40, 30);
		pdf.addImage(imageData, 'PNG', 40, 50, width - 80, height);

		pdf.save("andamento_ricavi_spese.pdf");
	});
}
