/**
 * @file terreno.js
 * @description Questo script gestisce la scheda "Terreno" nell'applicazione.
 * Recupera e visualizza dati relativi alle parcelle di terreno e alla loro storia di coltivazione,
 * utilizzando principalmente DataTables e un grafico a barre orizzontali dinamico per la rotazione delle colture.
 *
 * Operazioni:
 * - Recupera il contenuto HTML iniziale per la scheda terreno.
 * - Inizializza due DataTables: uno per i dettagli delle parcelle di terreno e un altro per
 * i dati del rilievo morfologico.
 * - Popola una datalist con le parcelle di terreno disponibili.
 * - Genera dinamicamente un grafico a barre orizzontali per visualizzare la storia di rotazione delle colture
 * per una parcella di terreno selezionata.
 * - Il grafico a barre mostra i periodi di coltivazione per diverse colture sulla parcella scelta nel tempo.
 * - L'altezza del grafico viene regolata dinamicamente in base al numero di colture mostrate.
 * - Integra la funzionalità di zoom e pan (plugin ChartZoom) per l'asse temporale del grafico.
 * - Fornisce la funzionalità per scaricare il grafico di rotazione delle colture generato come PDF.
 * - Include una funzione di utilità per generare colori casuali per gli elementi del grafico.
 */

let terrenoLoaded = false;

document.getElementById('terreno-tab').addEventListener('click', function() {
	if (!terrenoLoaded) {
		fetch('/terreno')
			.then(response => response.text())
			.then(html => {
				document.getElementById('tab-terreno').innerHTML = html;
			})
			.catch(error => {
				console.error('Errore nel caricamento del terreno:', error);
			});

		setTimeout(() => {
			if (!$.fn.DataTable.isDataTable('#particella-datatable')) {
				new DataTable('#particella-datatable', {
					responsive: true,
					language: {
						entries: {
							_: 'dati particella',
							1: 'particella'
						}
					},
					pageLength: 5,
					lengthMenu: [[5, 10, 15], [5, 10, 15]]
				});
			}

			if (!$.fn.DataTable.isDataTable('#rilevazione-datatable')) {
				new DataTable('#rilevazione-datatable', {
					responsive: true,
					language: {
						entries: {
							_: 'dati rilevazioni morfologiche',
							1: 'rilevazione'
						}
					},
					pageLength: 5,
					lengthMenu: [[5, 10, 15], [5, 10, 15]]
				});
			}
			
			const collapseBarChartTerreno = document.getElementById('collapseBarChartTerreno');
			let terrenoBarChartInstance;
			
			collapseBarChartTerreno.addEventListener('shown.bs.collapse', function() {
				if (!terrenoBarChartInstance) {
					rotazioneColtureBarChart(terrenoBarChartInstance);
					downloadRotazioneColtureTerreno();
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

Chart.register(ChartZoom);

//ROTAZIONE COLTURE BARCHART
function rotazioneColtureBarChart(terrenoBarChartInstance) {

	fetch("/findParticellaJoinColturaTerreno")
		.then(res => res.json())
		.then(data => {

			const terreniDatalistOptions = document.getElementById("particelleDatalistOptions");
			Object.keys(data).forEach(prodotto => {
				const option = document.createElement("option");
				option.value = prodotto;
				option.textContent = prodotto;
				terreniDatalistOptions.appendChild(option);
			});

			document.getElementById("particelleDataList").addEventListener("change", function() {

				const terrenoSelezionato = this.value;
				//Inverto l'ordine della lista per avere la lista di date già ordinate
				const dtoList = data[terrenoSelezionato].slice().reverse();

				const pulsanteRicerca = document.getElementById("ricercaTerreno");

				pulsanteRicerca.removeAttribute("disabled");
				pulsanteRicerca.addEventListener("click", function() {

					if (terrenoBarChartInstance) {
						terrenoBarChartInstance.destroy();
					}

					const ctxLine = document.getElementById("rotazioneColtureBarChart").getContext("2d");

					const BAR_HEIGHT = 40;
					const PADDING_PER_BAR = 10;
					const MIN_CHART_HEIGHT = 200;
					const TOP_BOTTOM_PADDING = 100;

					var dateMin = new Date(dtoList[dtoList.length - 1].dateRilevazione[0]);
					var dateMax = new Date(dtoList[0].dateRilevazione[dtoList[0].dateRilevazione.length - 1]);

					const prodottiColtivati = [];

					const datasetMap = new Map();

					for (let dto of dtoList) {
					    const range = {
					        y: dto.prodottoColtivato,
					        x: [dto.dateRilevazione[0], dto.dateRilevazione[dto.dateRilevazione.length - 1]]
					    };

					    if (datasetMap.has(dto.prodottoColtivato)) {
					        datasetMap.get(dto.prodottoColtivato).data.push(range);
					    } else {
					        datasetMap.set(dto.prodottoColtivato, {
					            label: 'Coltura: #' + dto.idColtura,
					            data: [range],
					            borderColor: generaColoreRandom()
					        });
					    }
					}
					
					const datasetDinamico = Array.from(datasetMap.values());
					
					const calculatedCanvasHeight = Math.max(
						MIN_CHART_HEIGHT,
						(Object.keys(datasetMap).length * (BAR_HEIGHT + PADDING_PER_BAR)) + TOP_BOTTOM_PADDING);

					document.getElementById("rotazioneColtureBarChart").setAttribute("height", `${calculatedCanvasHeight}px`);
					
					terrenoBarChartInstance = new Chart(ctxLine, {
						type: 'bar',
						data: {
							labels: prodottiColtivati,
							datasets: datasetDinamico
						}, options: {
							responsive: true,
							maintainAspectRatio: false,
							indexAxis: 'y',
							scales: {
								x: {
									stacked: true,
									beginAtZero: false,
									type: 'time',
									time: {
										unit: 'year',
										tooltipFormat: 'yyyy',
										displayFormats: {
											day: 'yyyy'
										}
									},
									min: dateMin ? dateMin.toISOString() : undefined,
									max: dateMax ? dateMax.toISOString() : undefined,
									title: {
										display: true,
										text: 'Data Rilevazione Terreno'
									}
								},
								y: {
									stacked: true,
									title: {
										display: true,
										text: 'Prodotto Coltivato'
									}
								}
							},
							elements: {
								bar: {
									borderWidth: 2,
									barPercentage: 0.8,
									categoryPercentage: 0.8,
									borderSkipped: false
								}
							},
							plugins: {
								legend: {
									position: 'right'
								}, tooltip: {
									callbacks: {
										label: function(context) {
											const label = context.dataset.label || '';
											const [start, end] = context.raw.x;
											return [
												`${label}`,
												`Periodo rilevazione: dal ${start} al ${end}`
											];
										}
									}
								},
								//INTEGRAZIONE PLUGIN ZOOM/PAN							
								zoom: {
									zoom: {
										wheel: {
											enabled: true
										},
										mode: 'x',
										limits: {
											x: {
												min: dateMin ? dateMin.toISOString() : undefined,
												max: dateMax ? dateMax.toISOString() : undefined
											}
										}
									},
									pan: {
										enabled: true,
										mode: 'x',
										threshold: 10
									}
								}
							}
						}
					})
				});
			});
		})
}

function downloadRotazioneColtureTerreno() {
	document.getElementById("downloadRotazioneColtureTerreno").addEventListener("click", async () => {
		const { jsPDF } = window.jspdf;

		const pdf = new jsPDF({
			orientation: 'landscape',
			unit: 'px',
			format: 'a4'
		});

		const canvas = document.getElementById("rotazioneColtureBarChart");
		const imageData = canvas.toDataURL("image/png", 1.0);

		const width = pdf.internal.pageSize.getWidth();
		const height = canvas.height * (width / canvas.width);

		pdf.text("Rotazione Colture Terreno Storica", 40, 30);
		pdf.addImage(imageData, 'PNG', 40, 50, width - 80, height);

		pdf.save("rotazione_colture_terreno.pdf");
	});
}