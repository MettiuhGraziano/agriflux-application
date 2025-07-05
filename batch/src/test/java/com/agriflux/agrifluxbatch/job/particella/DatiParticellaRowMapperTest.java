package com.agriflux.agrifluxbatch.job.particella;

import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecordReduce;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DatiParticellaRowMapperTest {

    @Mock
    private ResultSet mockResultSet;

    @Test
    void mapRow_success() throws SQLException {
        // Arrange
        DatiParticellaRowMapper rowMapper = new DatiParticellaRowMapper();
        long expectedIdParticella = 123L;
        String expectedAnnoInstallazione = "2020";

        when(mockResultSet.getLong("ID_PARTICELLA")).thenReturn(expectedIdParticella);
        when(mockResultSet.getString("ANNO_INSTALLAZIONE")).thenReturn(expectedAnnoInstallazione);

        // Act
        DatiParticellaRecordReduce result = rowMapper.mapRow(mockResultSet, 1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedIdParticella, result.idParticella());
        assertEquals(expectedAnnoInstallazione, result.annoInstallazione());
    }

    @Test
    void mapRow_handlesNullAnnoInstallazione() throws SQLException {
        // Arrange
        DatiParticellaRowMapper rowMapper = new DatiParticellaRowMapper();
        long expectedIdParticella = 456L;

        when(mockResultSet.getLong("ID_PARTICELLA")).thenReturn(expectedIdParticella);
        when(mockResultSet.getString("ANNO_INSTALLAZIONE")).thenReturn(null); // Test null case

        // Act
        DatiParticellaRecordReduce result = rowMapper.mapRow(mockResultSet, 1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedIdParticella, result.idParticella());
        assertEquals(null, result.annoInstallazione());
    }
}
