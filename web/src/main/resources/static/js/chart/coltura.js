/**
 * @file coltura.js
 * @description Questo script gestisce la scheda "Colture" nell'applicazione.
 * Recupera e visualizza dati relativi a varie colture, utilizzando DataTables e
 * diversi tipi di grafici (linea, a barre, a torta, linea/barre combinato) per la visualizzazione.
 *
 * Operazioni:
 * - Recupera il contenuto HTML iniziale per la scheda delle colture.
 * - Inizializza un DataTable per visualizzare i dati tabellari delle colture.
 * - Genera dinamicamente un grafico a linee per mostrare l'andamento del prezzo di una coltura selezionata nel tempo.
 * - Crea un grafico a barre che illustra il conteggio totale dei diversi tipi di ortaggi coltivati.
 * - Genera un grafico a torta che rappresenta la distribuzione delle famiglie di colture.
 * - Implementa un grafico combinato a linee e a barre per visualizzare il consumo medio vs effettivo di acqua
 * per diverse colture in un anno selezionato, con evidenziazione interattiva.
 * - Fornisce la funzionalità per scaricare i grafici generati come PDF.
 * - Include una funzione di utilità per generare colori casuali per gli elementi del grafico.
 */

let coltureLoaded = false;

document.getElementById('colture-tab').addEventListener('click', function() {
	if (!coltureLoaded) {
		fetch('/coltura')
			.then(response => response.text())
			.then(html => {
				document.getElementById('tab-colture').innerHTML = html;
			})
			.catch(error => {
				console.error('Errore nel caricamento delle colture:', error);
			});

		setTimeout(() => {
			if (!$.fn.DataTable.isDataTable('#coltura-datatable')) {
				new DataTable('#coltura-datatable', {
					responsive: true,
					ordering: true,
					language: {
						entries: {
							_: 'colture',
							1: 'coltura'
						}
					},
					pageLength: 5,
					lengthMenu: [[5, 10, 15], [5, 10, 15]]
				});
			}

			const collapseColturaLineChart = document.getElementById('collapseColturaLineChart');
			let colturaLineChartInstance = null;

			collapseColturaLineChart.addEventListener('shown.bs.collapse', function() {
				if (!colturaLineChartInstance) {
					colturaLineChartDinamica(colturaLineChartInstance);
					downloadColturaLineChart();
				}
			});

			const collapseColturaCharts = document.getElementById('collapseColturaCharts');
			let colturaBarChartInstance = null;
			let colturaPieChartInstance = null;

			collapseColturaCharts.addEventListener('shown.bs.collapse', function() {
				if (!colturaBarChartInstance) {
					colturaBarChart(colturaBarChartInstance);
				}

				if (!colturaPieChartInstance) {
					colturaPieChart(colturaPieChartInstance);
					downloadStoricoColtivazioniChart()
				}
			});
			
			const collapseColturaLineBarChart = document.getElementById('collapseColturaLineBarChart');
			let colturaLineBarChartInstance = null;
			
			collapseColturaLineBarChart.addEventListener('shown.bs.collapse', function() {
				if (!colturaLineBarChartInstance) {
					colturaLineBarChart(colturaLineBarChartInstance);
					downloadColturaLineBarChart();
				}
			});
			
		}, 1000);
	}
});

function generaColoreRandom() {
	var r = Math.floor(Math.random() * 255);
	var g = Math.floor(Math.random() * 255);
	var b = Math.floor(Math.random() * 255);
	return "rgb(" + r + "," + g + "," + b + ")";
}

//LINECHART COLTURE
function colturaLineChartDinamica(colturaLineChartInstance) {

	fetch("/findPrezziAndDateRaccoltoColtura")
		.then(res => res.json())
		.then(data => {
			const select = document.getElementById("productSelect");

			Object.keys(data).forEach(prodotto => {
				const option = document.createElement("option");
				option.value = prodotto;
				option.textContent = prodotto;
				select.appendChild(option);
			});
			
			select.addEventListener("change", function() {
				const prodottoSelezionato = this.value;
				const colturaData = data[prodottoSelezionato];

				const labels = colturaData.dataRaccoltoList;
				const values = colturaData.prezzoKgList;
				
				const ctx = document.getElementById("colturaLineChart").getContext("2d");
				
				if (colturaLineChartInstance) {
					colturaLineChartInstance.destroy();
				}

				colturaLineChartInstance = new Chart(ctx, {
					type: "line",
					data: {
						labels: labels,
						datasets: [{
							label: `Prezzo ${prodottoSelezionato}`,
							data: values,
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
								title: { display: true, text: 'Prezzo' }
							},
							x: {
								title: { display: true, text: 'Data Raccolto' },
								beginAtZero: false
							}
						}, plugins: {
							tooltip: {
								callbacks: {
									title: function(tooltipItems) {
										return `Andamento Prezzo ${prodottoSelezionato}`;
									},
									label: function(context) {
										const dataRaccolto = context.label;
										const value = context.parsed.y;

										return [
											`Data Raccolto: ${dataRaccolto}`,
											`Prezzo: ${value} €/Kg`
										];
									}
								}
							}
						}
					}
				});
			});
		});
}

//BARCHART COLTURE
function colturaBarChart(colturaBarChartInstance) {

	const ctx = document.getElementById('colturaBarChart').getContext('2d');

	fetch('/countOrtaggioColtura')
		.then(response => response.json())
		.then(data => {
			colturaBarChartInstance = new Chart(ctx, {
				type: 'bar',
				data: {
					labels: Object.keys(data),
					datasets: [{
						label: 'Colture Totali',
						data: Object.values(data),
						borderWidth: 2
					}]
				},
				options: {
					responsive: true,
					maintainAspectRatio: false,
					scales: {
						y: {
							beginAtZero: true
						}
					}
				}
			})
		});
}

//PIECHART FAMIGLIE COLTURE
function colturaPieChart(colturaPieChartInstance) {
	const ctx = document.getElementById('colturaPieChart').getContext('2d');

	fetch('/countFamigliaOrtaggioColtura')
		.then(response => response.json())
		.then(data => {

			const colorArray = [];
			Object.keys(data).forEach(prodotto => {
				colorArray.push(generaColoreRandom());
			});

			colturaPieChartInstance = new Chart(ctx, {
				type: 'pie',
				data: {
					labels: Object.keys(data),
					datasets: [{
						label: 'Colture Totali',
						data: Object.values(data),
						hoverOffset: 5,
						backgroundColor: colorArray
					}]
				},
				options: {
					responsive: true,
					maintainAspectRatio: false,
					animateRotate: true,
					animateScale: true
				}
			})
		});
}

//LINEBARCHART COLTURE
function colturaLineBarChart(colturaLineBarChartInstance) {

	fetch("/findColturaConsumoIdrico")
		.then(res => res.json())
		.then(colturaData => {

			const annoRiferimentoColturaSelect = document.getElementById("annoRiferimentoColturaSelect");
			const annoRiferimentoCounter = [];
			const annoDtoMap = new Map();
			
			colturaData.forEach(colturaData => {
				const annoRiferimento = new Date(colturaData.dataRaccolto).getFullYear().toString();
				if (!annoRiferimentoCounter.includes(annoRiferimento)) {
					const option = document.createElement("option");
					option.value = annoRiferimento;
					option.textContent = annoRiferimento;
					annoRiferimentoColturaSelect.appendChild(option);
					annoRiferimentoCounter.push(annoRiferimento);

				}
				if (!annoDtoMap.has(annoRiferimento)) {
					annoDtoMap.set(annoRiferimento, []);
				}

				annoDtoMap.get(annoRiferimento).push(colturaData);
			});

			annoRiferimentoColturaSelect.addEventListener("change", function() {
				const annoSelezionato = this.value;
				const dtoList = annoDtoMap.get(annoSelezionato);

				const dateMin = dtoList[0].dataRaccolto;
				const dateMax = dtoList[dtoList.length - 1].dataRaccolto;

				const ortaggi = [];
				const consumiIdriciMedi = [];
				const dateRaccolto = [];
				const lineChartData = [];

				dtoList.forEach(coltura => {
					if (!ortaggi.includes(coltura.nome)) {
						ortaggi.push(coltura.nome);
						consumiIdriciMedi.push(coltura.consumoIdricoMedio);
					}

					lineChartData.push({
						x: coltura.dataRaccolto,
						y: coltura.consumoIdrico,
						ortaggio: coltura.nome,
						idColtura: coltura.idColtura
					});

					dateRaccolto.push(coltura.dataRaccolto);
				});

				const ctxLineBar = document.getElementById("colturaLineBarChart").getContext("2d");

				if (colturaLineBarChartInstance) {
					colturaLineBarChartInstance.destroy();
				}

				colturaLineBarChartInstance = new Chart(ctxLineBar, {
					type: 'bar',
					data: {
						datasets: [{
							label: `Consumo Idrico Medio`,
							data: consumiIdriciMedi,
							fill: false,
							backgroundColor: "rgb(0, 206, 209, 0.6)",
							borderColor: "rgb(0, 206, 209)",
							borderWidth: 2,
							yAxisID: 'y',
							xAxisID: 'x-ortaggi',
							order: 1
						}, {
							label: `Consumo Idrico`,
							data: lineChartData,
							type: 'line',
							fill: false,
							borderColor: "rgb(0, 0, 128)",
							pointBackgroundColor: "rgb(0, 0, 128)",
							pointRadius: 3,
							yAxisID: 'y',
							xAxisID: 'x-date',
							order: 0
						}]
					},
					options: {
						responsive: true,
						maintainAspectRatio: false,
						scales: {
							y: {
								beginAtZero: false,
								title: { display: true, text: 'Consumo Idrico' }
							},
							'x-ortaggi': {
								beginAtZero: false,
								title: { display: true, text: 'Ortaggi' },
								labels: ortaggi,
								grid: {
									drawOnChartArea: false
								}
							},
							'x-date': {
								id: 'x-date',
								type: 'time',
								position: 'top',
								title: {
									display: true,
									text: 'Data Raccolto'
								},
								time: {
									unit: 'day',
									tooltipFormat: 'yyyy-MM-dd',
									displayFormats: {
										day: 'yyyy-MM-dd',
										month: 'MMM',
										year: 'yyyy'
									}
								},
								min: dateMin,
								max: dateMax,
								grid: {
									drawOnChartArea: true
								}
							}
						}, plugins: {
							tooltip: {
								callbacks: {
									label: function(context) {
										let label = context.dataset.label || '';

										//Dataset Bar (Consumo Idrico Medio)
										if (context.datasetIndex === 0) {
											return [
												`Ortaggio: ${context.label}`,
												`${label}: ${context.formattedValue} m³`
											];
											//Dataset Line (Andamento Consumo Idrico)
										} else if (context.datasetIndex === 1) {
											const rawData = context.raw;
											return [
												`Coltura: #${rawData.idColtura}`,
												`Ortaggio: ${rawData.ortaggio}`,
												`Data Raccolto: ${context.label}`,
												`${label}: ${context.formattedValue} m³`
											];
										}
										return label;
									}
								}
							}
						}, onClick: (e, elements) => {
							//Se è un punto del line chart cliccato
							if (elements.length > 0 && elements[0].datasetIndex === 1) {
								const elementoClickato = elements[0];
								const dataIndex = elementoClickato.index;
								const ortaggioClickato = lineChartData[dataIndex].ortaggio;

								// Trova l'indice del bar chart corrispondente all'ortaggio cliccato
								const barChartOrtaggioIndex = ortaggi.indexOf(ortaggioClickato);

								if (barChartOrtaggioIndex !== -1) {
									//Resetta lo stile di tutte le barre
									colturaLineBarChartInstance.data.datasets[0].backgroundColor = ortaggi.map(() => "rgba(0, 206, 209, 0.6)");
									colturaLineBarChartInstance.data.datasets[0].borderColor = ortaggi.map(() => "rgb(0, 206, 209)");
									colturaLineBarChartInstance.data.datasets[0].borderWidth = 1;

									//Evidenzia la barra corrispondente
									colturaLineBarChartInstance.data.datasets[0].backgroundColor[barChartOrtaggioIndex] = "rgba(255, 99, 132, 0.8)";
									colturaLineBarChartInstance.data.datasets[0].borderColor[barChartOrtaggioIndex] = "rgb(255, 99, 132)";
									colturaLineBarChartInstance.data.datasets[0].borderWidth[barChartOrtaggioIndex] = 3;

									//Aggiorna il grafico per mostrare l'evidenziazione
									colturaLineBarChartInstance.update();
								}
							}
						}
					}
				});
			});
		});
}

function downloadColturaLineChart() {
	document.getElementById("downloadColturaLineChart").addEventListener("click", async () => {
		const { jsPDF } = window.jspdf;

		const pdf = new jsPDF({
			orientation: 'landscape',
			unit: 'px',
			format: 'a4'
		});

		const canvas = document.getElementById("colturaLineChart");
		const imageData = canvas.toDataURL("image/png", 1.0);

		const width = pdf.internal.pageSize.getWidth();
		const height = canvas.height * (width / canvas.width);

		pdf.text("Andamento Prezzo Prodotti Coltivati", 40, 30);
		pdf.addImage(imageData, 'PNG', 40, 50, width - 80, height);

		pdf.save("andamento_prezzi_colture.pdf");
	});
}

function downloadStoricoColtivazioniChart() {
	document.getElementById("downloadNumeroColtivazioni").addEventListener("click", async () => {
		const { jsPDF } = window.jspdf;

		const pdf = new jsPDF({
			orientation: 'landscape',
			unit: 'px',
			format: 'a4'
		});

		const canvas1 = document.getElementById("colturaBarChart");
		const imageData1 = canvas1.toDataURL("image/png", 1.0);

		const width1 = pdf.internal.pageSize.getWidth();
		const height1 = canvas1.height * (width1 / canvas1.width) / 2;

		pdf.text("Numero di singoli ortaggi coltivati", 40, 30);
		pdf.addImage(imageData1, 'PNG', 40, 50, width1 - 80, height1);
		
		const canvas2 = document.getElementById("colturaPieChart");
		const imageData2 = canvas2.toDataURL("image/png", 1.0);

		const width2 = pdf.internal.pageSize.getWidth();
		const height2 = canvas2.height * (width2 / canvas2.width) / 2;
	
		pdf.addPage();
		pdf.text("Numero di famiglie coltivate", 40, 30);
		pdf.addImage(imageData2, 'PNG', 40, 50, width2 - 80, height2);

		pdf.save("storico_colture.pdf");
	});
}

function downloadColturaLineBarChart() {
	document.getElementById("downloadColturaLineBarChart").addEventListener("click", async () => {
		const { jsPDF } = window.jspdf;

		const pdf = new jsPDF({
			orientation: 'landscape',
			unit: 'px',
			format: 'a4'
		});

		const canvas = document.getElementById("colturaLineBarChart");
		const imageData = canvas.toDataURL("image/png", 1.0);

		const width = pdf.internal.pageSize.getWidth();
		const height = canvas.height * (width / canvas.width);

		pdf.text("Andamento Consumo Idrico Annuale", 40, 30);
		pdf.addImage(imageData, 'PNG', 40, 50, width - 80, height);

		pdf.save("andamento_consumo_idrico_colture.pdf");
	});
}
