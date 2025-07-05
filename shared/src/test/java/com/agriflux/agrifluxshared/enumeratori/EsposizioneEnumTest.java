package com.agriflux.agrifluxshared.enumeratori;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EsposizioneEnumTest {

    @Test
    void testGetEsposizione() {
        assertEquals("Nord", EsposizioneEnum.N.getEsposizione());
        assertEquals("Sud", EsposizioneEnum.S.getEsposizione());
        assertEquals("Est", EsposizioneEnum.E.getEsposizione());
        assertEquals("Ovest", EsposizioneEnum.O.getEsposizione());
    }

    @Test
    void testEnumValues() {
        // Ensure all expected enum values are present
        EsposizioneEnum[] values = EsposizioneEnum.values();
        assertEquals(4, values.length);
        // Check specific value presence byvalueOf
        assertEquals(EsposizioneEnum.N, EsposizioneEnum.valueOf("N"));
        assertEquals(EsposizioneEnum.S, EsposizioneEnum.valueOf("S"));
        assertEquals(EsposizioneEnum.E, EsposizioneEnum.valueOf("E"));
        assertEquals(EsposizioneEnum.O, EsposizioneEnum.valueOf("O"));
    }
}
