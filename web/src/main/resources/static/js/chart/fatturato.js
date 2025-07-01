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
					// Se esiste già un grafico, viene distrutto prima di crearne uno nuovo
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
							plugins: {
								scales: {
									y: {
										beginAtZero: true
									}
								}, tooltip: {
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
