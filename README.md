# agriflux-application

## Introduzione

Agriflux è un'applicazione completa progettata per la gestione e la visualizzazione dei dati agricoli. Include una dashboard basata su web per il monitoraggio di diverse metriche agricole e un sistema di elaborazione batch per la generazione e manipolazione dei dati attraverso una simulazione. L'applicazione è modulare, consentendo lo sviluppo e il deployment indipendenti dei suoi componenti principali.

## Moduli

This project is composed of the following modules:

-   **`web`**: Questo modulo contiene l'applicazione web principale, "agriflux-web". Fornisce una dashboard agli utenti per visualizzare e interagire con i dati agricoli. Utilizza Spring Boot con Thymeleaf per il rendering lato server e ChartJS per la visualizzazione dei dati.
-   **`batch`**: Il modulo "agriflux-batch" è responsabile delle attività di elaborazione batch. Questo include la generazione di dati casuali per simulare attività agricole e l'alimentazione di questi dati alla dashboard. Espone servizi REST e utilizza Spring Batch e Spring Data JPA.
-   **`shared`**: Questo modulo, "agriflux-shared", contiene classi comuni, utility e modelli di dati utilizzati sia dai moduli web che batch, promuovendo il riutilizzo del codice e la consistenza in tutta l'applicazione.

## Prerequisiti

Prima di iniziare, assicurati di aver soddisfatto i seguenti requisiti:

-   **Java Development Kit (JDK):** Versione 21 o superiore.
-   **Apache Maven:** Versione 3.6.x o superiore (per la build del progetto).

## Per Iniziare

Per ottenere una copia locale del progetto e avviarla, segui questi semplici passaggi:

1.  **Clona il repository:**
    ```bash
    git clone https://github.com/your-username/agriflux-application.git
    ```
2.  **Naviga nella directory del progetto:**
    ```bash
    cd agriflux-application
    ```
3.  **Compila il progetto:**
    Questo comando compilerà tutti i moduli e scaricherà le dipendenze necessarie.
    ```bash
    mvn clean install
    ```

## Avviare l'Applicazione

Ogni modulo (web e batch) può essere eseguito indipendentemente.

### Modulo Web (agriflux-web)

Il modulo web esegue l'applicazione dashboard principale.

1.  **Naviga nella directory del modulo web:**
    ```bash
    cd web
    ```
2.  **Avvia l'applicazione usando Maven:**
    ```bash
    mvn spring-boot:run
    ```
    In alternativa, dopo aver compilato il progetto con `mvn clean install` dalla root, puoi eseguire il JAR impacchettato:
    ```bash
    java -jar target/agriflux-web-0.0.1-SNAPSHOT.jar
    ```
L'applicazione web sarà tipicamente disponibile all'indirizzo `http://localhost:8080`.
La console del database H2 per il modulo web può essere accessibile all'indirizzo `http://localhost:8080/h2-console` (URL DataSource: `jdbc:h2:mem:webDb`, Utente: `web`, Password: `password`).
I suoi endpoint REST saranno disponibili sotto `http://localhost:8080/`.

### Modulo Batch (agriflux-batch)

Il modulo batch esegue processi in background ed espone servizi REST per la generazione di dati.

1.  **Naviga nella directory del modulo batch:**
    ```bash
    cd batch
    ```
2.  **Avvia l'applicazione usando Maven:**
    ```bash
    mvn spring-boot:run
    ```
    In alternativa, dopo aver compilato il progetto con `mvn clean install` dalla root, puoi eseguire il JAR impacchettato:
    ```bash
    java -jar target/agriflux-batch-0.0.1-SNAPSHOT.jar
    ```
L'applicazione batch sarà in esecuzione sulla porta 8081 (configurata in `batch/src/main/resources/application.properties`) per evitare conflitti con il modulo web.
La console del database H2 per il modulo batch può essere accessibile all'indirizzo `http://localhost:8081/h2-console` (URL DataSource: `jdbc:h2:mem:batchDb`, Utente: `batch`, Password: `password`).
I suoi endpoint REST (ad esempio, per innescare la generazione di dati) saranno disponibili sotto `http://localhost:8081/`.

## Esecuzione dei Test

Per eseguire i test automatizzati per tutti i moduli, naviga nella directory root del progetto (`agriflux-application`) e usa il seguente comando Maven:

```bash
mvn test
```

This will execute unit and integration tests in the `web`, `batch`, and `shared` modules.
Questo eseguirà i test unitari e di integrazione nei moduli `web`, `batch` e `shared`.

## Costruito Con

-   [Spring Boot](https://spring.io/projects/spring-boot) (Versions: `web` - 3.5.3, `batch` - 3.5.3) - Framework applicativo principale.
-   [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Per l'interazione con il database.
-   [Spring Batch](https://spring.io/projects/spring-batch) - Per l'elaborazione batch nel modulo `batch`.
-   [Thymeleaf](https://www.thymeleaf.org/) - Motore di template Java lato server per il modulo `web`.
-   [ChartJS](https://www.chartjs.org/) - Per il rendering dei grafici nella dashboard web.
-   [SpringDoc OpenAPI](https://springdoc.org/) - Per la documentazione delle API.
-   [H2 Database](https://www.h2database.com/) - Database in-memory per lo sviluppo e il testing.
-   [Maven](https://maven.apache.org/) - Strumento di gestione delle dipendenze e di build.
-   [Jackson](https://github.com/FasterXML/jackson) - Per l'elaborazione JSON.
-   [ModelMapper](http://modelmapper.org/) - Per il mapping degli oggetti nel modulo `batch`.

## Contatti

MettiuhGraziano - mettiuh96@gmail.com

Project Link: ([https://github.com/MettiuhGraziano/agriflux-application.git])
