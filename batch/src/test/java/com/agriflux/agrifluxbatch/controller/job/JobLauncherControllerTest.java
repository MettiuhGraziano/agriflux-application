package com.agriflux.agrifluxbatch.controller.job;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobLauncherController.class)
public class JobLauncherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobLauncher jobLauncher;

    @MockBean
    private JobRegistry jobRegistry;

    @MockBean
    private JobRepository jobRepository;

    @MockBean
    private Job mockJob; // Mock the Job object itself

    @Test
    void simulationJob_launchesJobSuccessfully() throws Exception {
        // Arrange
        when(jobRegistry.getJob("simulationJob")).thenReturn(mockJob);
        // We don't need to mock jobLauncher.run to return anything specific for a void controller method,
        // just verify it's called.

        // Act & Assert
        mockMvc.perform(post("/simulationJob.html"))
                .andExpect(status().isOk()); // Or whatever status void controller methods return by default

        verify(jobRegistry, times(2)).getJob("simulationJob"); // Called twice: once in logger, once in run
        verify(jobLauncher).run(eq(mockJob), any(JobParameters.class));
    }

    @Test
    void simulationJob_handlesExceptionFromJobRegistry() throws Exception {
        when(jobRegistry.getJob("simulationJob")).thenThrow(new org.springframework.batch.core.launch.NoSuchJobException("Test Exception"));

        mockMvc.perform(post("/simulationJob.html"))
                .andExpect(status().isInternalServerError()); // Default exception handling behavior

        verify(jobRegistry, times(1)).getJob("simulationJob"); // Called once in logger before exception
        verify(jobLauncher, never()).run(any(), any());
    }


    @Test
    void isJobExecuted_whenJobInstancesExist_returnsTrue() throws Exception {
        // Arrange
        JobInstance mockJobInstance = mock(JobInstance.class); // Simple mock
        List<JobInstance> jobInstances = Collections.singletonList(mockJobInstance);
        when(jobRepository.findJobInstancesByName("simulationJob", 0, 1)).thenReturn(jobInstances);

        // Act & Assert
        mockMvc.perform(get("/isJobExecuted"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(true)));

        verify(jobRepository).findJobInstancesByName("simulationJob", 0, 1);
    }

    @Test
    void isJobExecuted_whenNoJobInstancesExist_returnsFalse() throws Exception {
        // Arrange
        List<JobInstance> jobInstances = Collections.emptyList();
        when(jobRepository.findJobInstancesByName("simulationJob", 0, 1)).thenReturn(jobInstances);

        // Act & Assert
        mockMvc.perform(get("/isJobExecuted"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(false)));

        verify(jobRepository).findJobInstancesByName("simulationJob", 0, 1);
    }

    @Test
    void isJobExecuted_whenRepositoryReturnsNull_returnsFalse() throws Exception {
        // Arrange
        when(jobRepository.findJobInstancesByName("simulationJob", 0, 1)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/isJobExecuted"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(false)));

        verify(jobRepository).findJobInstancesByName("simulationJob", 0, 1);
    }
}
