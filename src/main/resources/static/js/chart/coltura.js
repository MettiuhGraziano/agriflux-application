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

			// Popola la Select con le chiavi della mappa
			Object.keys(data).forEach(prodotto => {
				const option = document.createElement("option");
				option.value = prodotto;
				option.textContent = prodotto;
				select.appendChild(option);
			});
			
			// Evento cambio selezione
			select.addEventListener("change", function() {
				const prodottoSelezionato = this.value;
				const colturaData = data[prodottoSelezionato];

				const labels = colturaData.dataRaccoltoList;
				const values = colturaData.prezzoKgList;
				

				const ctx = document.getElementById("colturaLineChart").getContext("2d");
				
				// Se esiste già un grafico, viene distrutto prima di crearne uno nuovo
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
								title: { display: true, text: 'Prezzo €/kg' }
							},
							x: {
								title: { display: true, text: 'Data Raccolto' },
								beginAtZero: false
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
			//GENERO COLORI RANDOM X OGNI COLTURA
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
