package com.agriflux.agrifluxbatch;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.agriflux.agrifluxbatch.configuration.AgrifluxJobsConfiguration;
import com.agriflux.agrifluxbatch.model.ambiente.DatiAmbienteRecord;
import com.agriflux.agrifluxbatch.model.coltura.DatiColturaRecord;
import com.agriflux.agrifluxbatch.model.fatturato.DatiFatturatoRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaFatturatoRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaMetadata;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecordReduce;
import com.agriflux.agrifluxbatch.model.produzione.DatiColturaJoinParticellaRecord;
import com.agriflux.agrifluxbatch.model.produzione.DatiProduzioneRecord;
import com.agriflux.agrifluxbatch.model.stagione.DatiStagioneRecord;
import com.agriflux.agrifluxbatch.model.terreno.DatiRilevazioneTerrenoMetadata;
import com.agriflux.agrifluxbatch.model.terreno.DatiRilevazioneTerrenoRecord;

@SpringBatchTest
@SpringBootTest(classes = { AgrifluxJobsConfiguration.class })
public class AgriFluxBatchProcessorsApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@MockBean
	private ItemReader<DatiParticellaMetadata> datiParticellaItemReader;

	@MockBean
	private ItemProcessor<DatiParticellaMetadata, DatiParticellaRecord> datiParticellaCustomItemProcessor;

	@MockBean
	private ItemWriter<DatiParticellaRecord> datiParticellaDataTableWriter;

	@MockBean
	private ItemReader<DatiParticellaRecordReduce> datiParticellaCustomItemReader;

	@MockBean
	private ItemProcessor<DatiParticellaRecordReduce, List<DatiColturaRecord>> datiColturaCustomProcessor;

	@MockBean
	private ItemWriter<List<DatiColturaRecord>> appiattisciColturaRecordListItemWriter;

	@MockBean
	private ItemReader<DatiRilevazioneTerrenoMetadata> datiRilevazioneTerrenoItemReader;

	@MockBean
	private ItemProcessor<DatiRilevazioneTerrenoMetadata, List<DatiRilevazioneTerrenoRecord>> datiRilevazioneTerrenoCustomProcessor;

	@MockBean
	private ItemWriter<List<DatiRilevazioneTerrenoRecord>> appiattisciRilevazioneTerrenoRecordListItemWriter;

	@MockBean
	private ItemReader<DatiColturaJoinParticellaRecord> datiProduzioneCustomItemReader;

	@MockBean
	private ItemProcessor<DatiColturaJoinParticellaRecord, DatiProduzioneRecord> datiProduzioneCustomProcessor;

	@MockBean
	private ItemWriter<DatiProduzioneRecord> datiProduzioneDataTableWriter;

	@MockBean
	private ItemReader<DatiStagioneRecord> datiStagioneItemReader;

	@MockBean
	private ItemProcessor<DatiStagioneRecord, List<DatiAmbienteRecord>> datiAmbienteCustomProcessor;

	@MockBean
	private ItemWriter<List<DatiAmbienteRecord>> appiattisciAmbienteRecordListItemWriter;

	@MockBean
	private ItemReader<DatiParticellaFatturatoRecord> datiParticellaFatturatoCustomItemReader;

	@MockBean
	private ItemProcessor<DatiParticellaFatturatoRecord, List<DatiFatturatoRecord>> datiFatturatoCustomProcessor;

	@MockBean
	private ItemWriter<List<DatiFatturatoRecord>> appiattisciFatturatoRecordListItemWriter;

	@Autowired
	private Step createDatiParticellaStep;

	@Test
	public void testCreateDatiParticellaStep() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep(createDatiParticellaStep.getName());

		Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

	@Autowired
	private Step createDatiColturaStep;

	@Test
	public void testCreateDatiColturaStep() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep(createDatiColturaStep.getName());

		Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

	@Autowired
	private Step createDatiRilevazioneTerrenoStep;

	@Test
	public void testCreateDatiRilevazioneTerrenoStep() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep(createDatiRilevazioneTerrenoStep.getName());

		Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

	@Autowired
	private Step createDatiProduzioneStep;

	@Test
	public void testCreateDatiProduzioneStep() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep(createDatiProduzioneStep.getName());

		Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

	@Autowired
	private Step createDatiAmbienteStep;

	@Test
	public void testCreateDatiAmbienteStep() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep(createDatiAmbienteStep.getName());

		Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

	@Autowired
	private Step createDatiFatturatoStep;

	@Test
	public void testCreateDatiFatturatoStep() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep(createDatiFatturatoStep.getName());

		Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}
}