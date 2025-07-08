package com.agriflux.agrifluxbatch.controller.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController("api/jobLauncher")
@Tag(name = "Job Launcher Controller", description = "Client API servizi Rest esposti da Agriflux-Batch per funzionalità riguardanti i Job")
public class JobLauncherController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherController.class);
	
	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;
	private final JobRepository jobRepository;
	
	public JobLauncherController(JobLauncher jobLauncher, JobRegistry jobRegistry, JobRepository jobRepository) {
		this.jobLauncher = jobLauncher;
		this.jobRegistry = jobRegistry;
		this.jobRepository = jobRepository;
	}
	
    @PostMapping("/simulationJob.html")
    @Operation(summary = "Esecuzione Job di Simulazione", description = "Chiamata POST per l'avvio del Job di Simulazione")
    public void simulationJob() throws Exception{
    	
    	LOGGER.info("Inizio fase di lancio del job: {}", jobRegistry.getJob("simulationJob"));
    	
    	JobParameters params = new JobParametersBuilder()
    		    .addLong("time", System.currentTimeMillis())
    		    .toJobParameters();
    	
        jobLauncher.run(jobRegistry.getJob("simulationJob"), params);
    }
    
    //TODO CENSIRE METODO GET CHE RITORNA LO STATO DI ESECUZIONE DEL JOB "X"
    
    @GetMapping("/isJobExecuted")
    @Operation(summary = "Servizio di Audit per l'esecuzione del Job", description = "Restituisce true se esiste almeno una istanza di un Job a db, false altrimenti")
    public Boolean isJobExecuted() {
    	List<org.springframework.batch.core.JobInstance> jobInstancesByName = jobRepository.findJobInstancesByName("simulationJob", 0, 1);
    	
    	if (null != jobInstancesByName && !jobInstancesByName.isEmpty()) {
			return true;
		}
    	
    	return false;
    }
}
