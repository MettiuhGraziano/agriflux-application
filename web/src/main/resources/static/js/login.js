/**
 * @file login.js
 * @description Questo script gestisce un carosello di immagini di sfondo per la pagina di login.
 * Scorre un elenco predefinito di immagini, aggiornando lo sfondo
 * di uno specifico elemento div a intervalli regolari.
 *
 * Operazioni:
 * - Definisce un array di URL di immagini per il carosello.
 * - Implementa una funzione per cambiare l'immagine di sfondo del div '.bg-carousel'.
 * - Imposta un'immagine di sfondo iniziale al caricamento della pagina.
 * - Utilizza `setInterval` per cambiare automaticamente l'immagine di sfondo ogni 4.5 secondi,
 * creando un effetto slideshow.
 */

const images = [
	'/images/carosello1.png',
	'/images/carosello2.png',
	'/images/carosello3.png',
	'/images/carosello4.jpg',
	'/images/carosello5.jpg',
	'/images/carosello6.jpg'
];

let index = 0;
const bgDiv = document.querySelector('.bg-carousel');

function changeBg() {
	bgDiv.style.backgroundImage = `url('${images[index]}')`;
	index = (index + 1) % images.length;
}

changeBg(); // Mostra il primo sfondo
setInterval(changeBg, 4500); // Cambia ogni 4.5 secondi