/**
 * @file produzione.js
 * @description Questo script gestisce la scheda "Produzione" nell'applicazione.
 * Recupera e visualizza vari aspetti dei dati di produzione agricola utilizzando DataTables
 * e diversi tipi di grafici (linee multiple, radar, a dispersione, a barre orizzontali).
 *
 * Operazioni:
 * - Recupera il contenuto HTML iniziale per la scheda di produzione.
 * - Inizializza un DataTable per visualizzare i dati tabellari di produzione.
 * - Genera un grafico a linee multiple per visualizzare le quantità di un prodotto selezionato
 * (raccolto, venduto, marcio, altri usi) su diversi anni di riferimento.
 * - Crea un grafico radar per visualizzare il ricavo annuale per il prodotto selezionato.
 * - Implementa un grafico a dispersione per mostrare la distribuzione della produzione del raccolto nel tempo,
 * filtrabile per data. Cliccando su un punto dati si aggiorna una tabella di dettaglio.
 * - Genera un grafico a barre orizzontali che illustra la durata media delle diverse
 * fasi di coltivazione (semina, germinazione, ecc.) per vari prodotti in un anno selezionato.
 * - Fornisce la funzionalità per scaricare i grafici generati come PDF.
 * - Include una funzione di utilità per generare colori casuali per gli elementi del grafico.
 */

let produzioneLoaded = false;

document.getElementById('produzione-tab').addEventListener('click', function() {
	if (!produzioneLoaded) {
		fetch('/produzione')
			.then(response => response.text())
			.then(html => {
				document.getElementById('tab-produzione').innerHTML = html;
			})
			.catch(error => {
				console.error('Errore nel caricamento dei dati di produzione:', error);
			});

		setTimeout(() => {
			if (!$.fn.DataTable.isDataTable('#produzione-datatable')) {
				new DataTable('#produzione-datatable', {
					responsive: true,
					language: {
						entries: {
							_: 'dati produzione',
							1: 'produzione'
						}
					},
					pageLength: 5,
					lengthMenu: [[5, 10, 15], [5, 10, 15]]
				});
			}
			
			const collapseProduzioneMultipleCharts = document.getElementById('collapseProduzioneMultipleCharts');
			let produzioneLineChartInstance;
			let produzioneRadarChartInstance;
			
			collapseProduzioneMultipleCharts.addEventListener('shown.bs.collapse', function() {
				if (!produzioneLineChartInstance && !produzioneRadarChartInstance) {
					produzioneMultipleChartLineRadar(produzioneLineChartInstance, produzioneRadarChartInstance);
					downloadProduzioneAnnualeRaccoltoChart();
				}
			});
	
			const collapseProduzioneScatterChart = document.getElementById('collapseProduzioneScatterChart');
			let produzioneScatterChartInstance;
			
			collapseProduzioneScatterChart.addEventListener('shown.bs.collapse', function() {
				if (!produzioneScatterChartInstance) {
					produzioneScatterChart(produzioneScatterChartInstance);
					downloadDistribuzioneProduzione();
				}
			});
			
			const collapseProduzioneHorizontalBarChart = document.getElementById('collapseProduzioneHorizontalBarChart');
			let produzioneHorizontalBarChartInstance;
			
			collapseProduzioneHorizontalBarChart.addEventListener('shown.bs.collapse', function() {
				if (!produzioneHorizontalBarChartInstance) {
					produzioneBarChartHorizontal(produzioneHorizontalBarChartInstance);
					downloadVariazioneTempi();
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

//MULTIPLE LINECHART COLTURE
function produzioneMultipleChartLineRadar(produzioneLineChartInstance, produzioneRadarChartInstance) {

	fetch("/findProduzioneQuantitaJoinColtura")
		.then(res => res.json())
		.then(data => {

			const datalistOptions = document.getElementById("datalistOptions");
			Object.keys(data).forEach(prodotto => {
				const option = document.createElement("option");
				option.value = prodotto;
				option.textContent = prodotto;
				datalistOptions.appendChild(option);
			});

			document.getElementById("prodottiDataList").addEventListener("change", function() {
				const prodottoSelezionato = this.value;

				const mappaAnnoRiferimento = data[prodottoSelezionato];
				const pulsanteRicerca = document.getElementById("ricercaAnno");

				pulsanteRicerca.removeAttribute("disabled");
				pulsanteRicerca.addEventListener("click", function() {

					const labelsAnnoRiferimento = Object.keys(mappaAnnoRiferimento);

					const quantitaRaccolto = [];
					const quantitaRaccoltoVenduto = [];
					const quantitaRaccoltoMarcio = [];
					const quantitaRaccoltoTerzi = [];
					const fatturatoRaccolto = [];

					Object.values(mappaAnnoRiferimento).forEach(dto => {
						quantitaRaccolto.push(dto.quantitaRaccolto);
						quantitaRaccoltoVenduto.push(dto.quantitaRaccoltoVenduto);
						quantitaRaccoltoMarcio.push(dto.quantitaRaccoltoMarcio);
						quantitaRaccoltoTerzi.push(dto.quantitaRaccoltoTerzi);
						fatturatoRaccolto.push(dto.fatturatoRaccolto);
					});

					const ctxLine = document.getElementById("produzioneMultipleLineChart").getContext("2d");

					if (produzioneLineChartInstance) {
						produzioneLineChartInstance.destroy();
					}

					//LINECHART MULTIPLO
					produzioneLineChartInstance = new Chart(ctxLine, {
						type: 'line',
						data: {
							labels: labelsAnnoRiferimento,
							datasets: [{
								label: `Quantita' di ${prodottoSelezionato} raccolto/a (KG)`,
								data: quantitaRaccolto,
								borderColor: 'rgb(0, 0, 128)',
								backgroundColor: 'rgb(0, 0, 128)',
								borderWidth: 3
							}, {
								label: `Quantita' di ${prodottoSelezionato} venduto/a (KG)`,
								data: quantitaRaccoltoVenduto,
								borderColor: 'rgb(0, 255, 0)',
								backgroundColor: 'rgb(0, 255, 0)',
								borderWidth: 3
							},
							{
								label: `Quantita' di ${prodottoSelezionato} marcio/a (KG)`,
								data: quantitaRaccoltoMarcio,
								borderColor: 'rgb(210,105,30)',
								backgroundColor: 'rgb(210,105,30)',
								borderWidth: 3
							}, {
								label: `Quantita' di ${prodottoSelezionato} utilizzato per scopi terzi (KG)`,
								data: quantitaRaccoltoTerzi,
								borderColor: 'rgb(139,0,139)',
								backgroundColor: 'rgb(139,0,139)',
								borderWidth: 3
							}]
						}, options: {
							responsive: true,
							maintainAspectRatio: false,
							scales: {
								y: {
									title: { display: true, text: 'Quantità Raccolto' }
								},
								x: {
									title: { display: true, text: 'Anno Riferimento' },
									beginAtZero: false
								}
							}
						}
					});

					//RADARCHART
					const ctxRadar = document.getElementById("produzioneRadarChart").getContext("2d");

					if (produzioneRadarChartInstance) {
						produzioneRadarChartInstance.destroy();
					}

					produzioneRadarChartInstance = new Chart(ctxRadar, {
						type: 'radar',
						data: {
							labels: labelsAnnoRiferimento,
							datasets: [{
								label: `Fatturato annuale ${prodottoSelezionato}`,
								data: fatturatoRaccolto,
								borderColor: 'rgb(233,150,122)',
								backgroundColor: 'rgb(233,150,122, 0.4)',
								borderWidth: 3
							}]
						}, options: {
							responsive: true,
							maintainAspectRatio: false, 
							plugins: {
								tooltip: {
									callbacks: {
										label: function(context) {
											const value = context.formattedValue;

											return [
												`Fatturato: ${value} €`
											];
										}
									}
								}
							}
						}
					});
				});
			});
		})
}

//SCATTER CHART
function produzioneScatterChart(produzioneScatterChartInstance) {

	fetch("/findProduzioneParticellaColturaOrtaggio")
		.then(res => res.json())
		.then(data => {

			const startDate = document.getElementById("startDate");
			const endDate = document.getElementById("endDate");

			const produzioni = Object.keys(data);

			startDate.setAttribute("min", data[produzioni[0]].dataRaccolto);
			startDate.setAttribute("max", data[produzioni[produzioni.length - 1]].dataRaccolto);
			startDate.setAttribute("value", data[produzioni[0]].dataRaccolto);

			endDate.setAttribute("min", data[produzioni[0]].dataRaccolto);
			endDate.setAttribute("max", data[produzioni[produzioni.length - 1]].dataRaccolto);
			endDate.setAttribute("value", data[produzioni[produzioni.length - 1]].dataRaccolto);

			startDate.addEventListener("change", function() {
				startDate.setAttribute("value", this.value);
			});

			endDate.addEventListener("change", function() {
				endDate.setAttribute("value", this.value);
			});

			document.getElementById("invio").addEventListener("click", function() {

				const produzioniFiltrate = [];

				const startDateSelected = document.getElementById("startDate");
				const endDateSelected = document.getElementById("endDate");

				var dateMax = new Date(new Date(endDateSelected.value).setYear(new Date(endDateSelected.value).getFullYear() + 1));
				var dateMin = new Date(new Date(startDateSelected.value).setYear(new Date(startDateSelected.value).getFullYear() - 1));

				var rangeMin = new Date(startDateSelected.value);
				var rangeMax = new Date(endDateSelected.value);

				produzioni.forEach(produzione => {
					const dataRaccolto = new Date(data[produzione].dataRaccolto);
					if ((dataRaccolto <= rangeMin && dataRaccolto >= rangeMax) ||
						(dataRaccolto >= rangeMin && dataRaccolto <= rangeMax)) {
						produzioniFiltrate.push(produzione);
					}
				});

				fetch("/findListaProdottiColtivati")
					.then(res => res.json())
					.then(prodottiColtivati => {

						const mapColore = new Map();

						prodottiColtivati.forEach(prodotto => {
							mapColore.set(prodotto, { colore: generaColoreRandom() });
						});

						const datasetDinamico = [];
						for (let prodotto of mapColore.keys()) {
							const dataDinamico = [];
							produzioni.forEach(produzione => {
								if (data[produzione].prodottoColtivato == prodotto) {
									const isoString = new Date(data[produzione].dataRaccolto).toISOString();
									const dataFormattata = isoString.split("T")[0];
									dataDinamico.push({
										x: dataFormattata,
										y: Number(produzione)
									})
								}
							});
							datasetDinamico.push({
								label: prodotto,
								data: dataDinamico,
								backgroundColor: mapColore.get(prodotto).colore,
								pointStyle: 'circle',
								pointRadius: 5,
							})
						}

						let ctxScatter = document.getElementById("produzioneScatterChart").getContext("2d");

						if (produzioneScatterChartInstance) {
							produzioneScatterChartInstance.destroy();
						}

						produzioneScatterChartInstance = new Chart(ctxScatter, {
							type: "scatter",
							data: {
								datasets: datasetDinamico
							}, options: {
								responsive: true,
								onClick: (event, pointer) => {

									//Inserisco l'evento dentro un IF per evitare errori quando non si preme sui puntini
									if (pointer.length > 0) {
										//Estraggo l'indice relativo alla posizione del dato del punto premuto nel dataset
										const elementIndex = pointer[0].index;
										//Estraggo l'indice relativo alla posizione del dataset
										const datasetIndex = pointer[0].datasetIndex;
										//Estraggo il valore dell'oggetto puntato
										const point = produzioneScatterChartInstance.data.datasets[datasetIndex].data[elementIndex];

										aggiornaTabella(data[point.y]);
									}
								},
								maintainAspectRatio: false,
								scales: {
									x: {
										type: 'time',
										time: {
											unit: 'day',
											tooltipFormat: 'yyyy-MM-dd',
											displayFormats: {
												day: 'yyyy-MM-dd'
											}
										},
										min: dateMin.toISOString(),
										max: dateMax.toISOString(),
										title: {
											display: true,
											text: 'Data Raccolto'
										}
									},
									y: {
										beginAtZero: true,
										title: {
											display: true,
											text: 'Codice Produzione'
										}
									}
								}, plugins: {
									legend: {
										position: 'top',
									}, tooltip: {
										callbacks: {
											label: function(context) {
												const dataRaccolto = context.raw.x;
												const codiceProduzione = context.raw.y;
												const prodottoColtivato = context.dataset.label;

												return [
													`Prodotto Coltivato: ${prodottoColtivato}`,
													`Data Raccolto: ${dataRaccolto}`,
													`Codice Produzione: #${codiceProduzione}`
												];
											}
										}
									}
								}
							}
						});
					})
			});
		})
}

function aggiornaTabella(dto) {
	const tabellaUpdate = document.getElementById("produzione-chart-datatable");
	const bodyTabella = tabellaUpdate.querySelector("tbody");

	bodyTabella.innerHTML = '';

	const prodottoRow = document.createElement("tr");
	prodottoRow.innerHTML = `<td class="text-center">Prodotto Coltivato</td>
			    <td class="text-center">${dto.prodottoColtivato}</td>`;
	bodyTabella.appendChild(prodottoRow);

	const morfologiaRow = document.createElement("tr");
	morfologiaRow.innerHTML = `<td class="text-center">Codice Particella</td>
						    <td class="text-center">${dto.idParticella}</td>`;
	bodyTabella.appendChild(morfologiaRow);

	const estensioneRow = document.createElement("tr");
	estensioneRow.innerHTML = `<td class="text-center">Estensione Terreno</td>
						    <td class="text-center">${dto.estensioneTerreno} m²</td>`;
	bodyTabella.appendChild(estensioneRow);

	const pendenzaRow = document.createElement("tr");
	pendenzaRow.innerHTML = `<td class="text-center">Pendenza</td>
						    <td class="text-center">${dto.pendenza} %</td>`;
	bodyTabella.appendChild(pendenzaRow);

	const esposizione = document.createElement("tr");
	esposizione.innerHTML = `<td class="text-center">Esposizione</td>
						    <td class="text-center">${dto.esposizione}</td>`;
	bodyTabella.appendChild(esposizione);

	const litologiaRow = document.createElement("tr");
	litologiaRow.innerHTML = `<td class="text-center">Litologia</td>
						    <td class="text-center">${dto.litologia}</td>`;
	bodyTabella.appendChild(litologiaRow);
}

//HORIZONTAL BARCHART
function produzioneBarChartHorizontal(produzioneHorizontalBarChartInstance) {

	fetch("/findProduzioneJoinColturaTempi")
		.then(res => res.json())
		.then(data => {

			const annoRiferimentoSelect = document.getElementById("annoRiferimentoSelect");

			Object.keys(data).forEach(annoRiferimento => {
				const option = document.createElement("option");
				option.value = annoRiferimento;
				option.textContent = annoRiferimento;
				annoRiferimentoSelect.appendChild(option);
			});

			annoRiferimentoSelect.addEventListener("change", function() {

				const ctxBar = document.getElementById("produzioneBarChartHorizontal").getContext("2d");

				// Se esiste già un grafico, viene distrutto prima di crearne uno nuovo
				if (produzioneHorizontalBarChartInstance) {
					produzioneHorizontalBarChartInstance.destroy();
				}

				const annoSelectList = data[this.value];
				const prodottiColtivati = [];

				const medieSeminaList = [];
				const medieGerminazioneList = [];
				const medieTrapiantoList = [];
				const medieMaturazioneList = [];
				const medieRaccoltaList = [];

				annoSelectList.forEach(dto => {
					prodottiColtivati.push(dto.prodottoColtivato);
					medieSeminaList.push(dto.listMedieTempi[0]);
					medieGerminazioneList.push(dto.listMedieTempi[1]);
					medieTrapiantoList.push(dto.listMedieTempi[2]);
					medieMaturazioneList.push(dto.listMedieTempi[3]);
					medieRaccoltaList.push(dto.listMedieTempi[4]);
				});
				
				produzioneHorizontalBarChartInstance = new Chart(ctxBar, {
					type: 'bar',
					data: {
						labels: prodottiColtivati,
						datasets: [{
							label: 'Semina',
							data: medieSeminaList,
							borderColor: 'rgb(33, 102, 172)',
							backgroundColor: 'rgb(33, 102, 172, 0.2)',
							borderWidth: 2
						}, {
							label: 'Germinazione',
							data: medieGerminazioneList,
							borderColor: 'rgb(67, 147, 195)',
							backgroundColor: 'rgb(67, 147, 195, 0.2)',
							borderWidth: 2
						}, {
							label: 'Trapianto',
							data: medieTrapiantoList,
							borderColor: 'rgb(146, 197, 222)',
							backgroundColor: 'rgb(146, 197, 222, 0.2)',
							borderWidth: 2
						}, {
							label: 'Maturazione',
							data: medieMaturazioneList,
							borderColor: 'rgb(244, 165, 130)',
							backgroundColor: 'rgb(244, 165, 130, 0.2)',
							borderWidth: 2
						}, {
							label: 'Raccolta',
							data: medieRaccoltaList,
							borderColor: 'rgb(215, 48, 39)',
							backgroundColor: 'rgb(215, 48, 39, 0.2)',
							borderWidth: 2
						}]
					}, options: {
						indexAxis: 'y',
						responsive: true,
						maintainAspectRatio: false,
						scales: {
							x: {
								stacked: true,
								beginAtZero: true,
								title: { display: true, text: 'Giorni Semina -> Raccolta' }
							},
							y: {
								stacked: true,
								title: { display: true, text: 'Prodotti' }
							}
						}, plugins: {
							legend: {
								position: 'right',
							}, tooltip: {
								callbacks: {
									label: function(context) {
										const giorni = context.formattedValue;
										const tempo = context.dataset.label;
										
										return [
											`Giorni ${tempo}: ${giorni}`
										];
									}
								}
							}
						}
					}
				});
			});
		})
}

function downloadProduzioneAnnualeRaccoltoChart() {
	document.getElementById("downloadProduzioneAnnualeRaccolto").addEventListener("click", async () => {
		const { jsPDF } = window.jspdf;

		const pdf = new jsPDF({
			orientation: 'landscape',
			unit: 'px',
			format: 'a4'
		});

		const canvas1 = document.getElementById("produzioneMultipleLineChart");
		const imageData1 = canvas1.toDataURL("image/png", 1.0);

		const width1 = pdf.internal.pageSize.getWidth();
		const height1 = canvas1.height * (width1 / canvas1.width) / 2;

		pdf.text("Produzione Annuale", 40, 30);
		pdf.addImage(imageData1, 'PNG', 40, 50, width1 - 80, height1);
		
		const canvas2 = document.getElementById("produzioneRadarChart");
		const imageData2 = canvas2.toDataURL("image/png", 1.0);

		const width2 = pdf.internal.pageSize.getWidth();
		const height2 = canvas2.height * (width2 / canvas2.width) / 2;
	
		pdf.addPage();
		pdf.text("Fatturato Annuale", 40, 30);
		pdf.addImage(imageData2, 'PNG', 40, 50, width2 - 80, height2);

		pdf.save("produzione_annuale_raccolto.pdf");
	});
}

function downloadDistribuzioneProduzione() {
	document.getElementById("downloadDistribuzioneProduzione").addEventListener("click", async () => {
		const { jsPDF } = window.jspdf;

		const pdf = new jsPDF({
			orientation: 'landscape',
			unit: 'px',
			format: 'a4'
		});

		const canvas = document.getElementById("produzioneScatterChart");
		const imageData = canvas.toDataURL("image/png", 1.0);

		const width = pdf.internal.pageSize.getWidth();
		const height = canvas.height * (width / canvas.width) / 2;

		pdf.text("Distribuzione Produzione Prodotti", 40, 30);
		pdf.addImage(imageData, 'PNG', 40, 50, width - 80, height);

		pdf.save("distribuzione_produzione.pdf");
	});
}

function downloadVariazioneTempi() {
	document.getElementById("downloadVariazioneTempi").addEventListener("click", async () => {
		const { jsPDF } = window.jspdf;

		const pdf = new jsPDF({
			orientation: 'landscape',
			unit: 'px',
			format: 'a4'
		});

		const canvas = document.getElementById("produzioneBarChartHorizontal");
		const imageData = canvas.toDataURL("image/png", 1.0);

		const width = pdf.internal.pageSize.getWidth();
		const height = canvas.height * (width / canvas.width);

		pdf.text("Variazioni tempi di coltura", 40, 30);
		pdf.addImage(imageData, 'PNG', 40, 50, width - 80, height);

		pdf.save("variazione_tempi.pdf");
	});
}
