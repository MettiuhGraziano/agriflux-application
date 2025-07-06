/**
 * @file utils.js
 * @description Questo script fornisce funzioni di utilità per l'applicazione.
 * Attualmente, il suo scopo principale è inizializzare i tooltip di Bootstrap.
 *
 * Operazioni:
 * - Si mette in ascolto dell'evento 'DOMContentLoaded' per assicurarsi che l'HTML sia completamente caricato.
 * - Seleziona tutti gli elementi con l'attributo 'data-bs-toggle="tooltip"'.
 * - Inizializza un'istanza Tooltip di Bootstrap per ogni elemento selezionato,
 * abilitando la funzionalità dei tooltip in tutta l'applicazione.
 */

//INIZIALIZZAZIONE TOOLTIP GRAFICI
document.addEventListener('DOMContentLoaded', function() {
	const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
	tooltipTriggerList.forEach(function(tooltipTriggerEl) {
		new bootstrap.Tooltip(tooltipTriggerEl);
	});
});