package com.agriflux.agrifluxbatch.service.azienda;

import com.agriflux.agrifluxbatch.entity.Azienda;
import com.agriflux.agrifluxbatch.repository.DatiAziendaRepository;
import com.agriflux.agrifluxshared.dto.azienda.AziendaDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DatiAziendaServiceImplTest {

    @Mock
    private DatiAziendaRepository datiAziendaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DatiAziendaServiceImpl datiAziendaService;

    @Test
    void findAzienda_whenAziendaExists_returnsAziendaDTO() {
        // Arrange
        Azienda mockAziendaEntity = new Azienda();
        mockAziendaEntity.setIdAzienda(1L); // Entity has ID
        mockAziendaEntity.setRagioneSociale("Test Azienda"); // Corrected from setNome

        AziendaDTO expectedAziendaDTO = new AziendaDTO();
        // AziendaDTO does not have idAzienda field based on shared module DTO
        expectedAziendaDTO.setRagioneSociale("Test Azienda"); // Corrected from setNome

        when(datiAziendaRepository.findAziendaByIdAzienda(1L)).thenReturn(mockAziendaEntity);
        when(modelMapper.map(mockAziendaEntity, AziendaDTO.class)).thenReturn(expectedAziendaDTO);

        // Act
        AziendaDTO actualAziendaDTO = datiAziendaService.findAzienda();

        // Assert
        assertNotNull(actualAziendaDTO);
        assertEquals(expectedAziendaDTO, actualAziendaDTO);
        assertEquals(expectedAziendaDTO.getRagioneSociale(), actualAziendaDTO.getRagioneSociale()); // Corrected from getNome

        verify(datiAziendaRepository).findAziendaByIdAzienda(1L);
        verify(modelMapper).map(mockAziendaEntity, AziendaDTO.class);
    }

    @Test
    void findAzienda_whenAziendaDoesNotExist_returnsNull() {
        // Arrange
        when(datiAziendaRepository.findAziendaByIdAzienda(1L)).thenReturn(null);

        // Act
        AziendaDTO actualAziendaDTO = datiAziendaService.findAzienda();

        // Assert
        assertNull(actualAziendaDTO);

        verify(datiAziendaRepository).findAziendaByIdAzienda(1L);
        verify(modelMapper, never()).map(any(), eq(AziendaDTO.class));
    }
}
