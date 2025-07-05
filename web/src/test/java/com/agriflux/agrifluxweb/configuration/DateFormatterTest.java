package com.agriflux.agrifluxweb.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DateFormatterTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private DateFormatter dateFormatter;

    private final Locale testLocale = Locale.US;
    private final String testDateFormatPattern = "MM/dd/yyyy";

    @BeforeEach
    void setUp() {
        // No general stubbing here to avoid UnnecessaryStubbingException
        // Specific stubbings will be done in each test method that needs them.
    }

    @Test
    void testPrint() {
        // Specific stubbing for this test
        when(messageSource.getMessage("date.format", null, testLocale)).thenReturn(testDateFormatPattern);

        // Use a known date: 2023-03-15
        Date date = new Date(1678838400000L); // Corresponds to 2023-03-15 00:00:00 GMT
        String expectedDateString = "03/15/2023";

        // To ensure the SimpleDateFormat uses a consistent timezone for the test,
        // let's create the expected string using a SimpleDateFormat configured similarly.
        // However, DateFormatter itself uses the system default timezone when formatting.
        // For more robust testing, one might consider standardizing timezone in DateFormatter or tests.
        // For this test, we'll assume the default timezone doesn't shift the date enough to change MM/dd/yyyy.

        String actualDateString = dateFormatter.print(date, testLocale);
        assertEquals(expectedDateString, actualDateString);
    }

    @Test
    void testParseSuccess() throws ParseException {
        // Specific stubbing for this test
        when(messageSource.getMessage("date.format", null, testLocale)).thenReturn(testDateFormatPattern);

        String dateString = "03/15/2023";
        // Expected Date object. Note: Month is 0-indexed in Calendar/Date constructors if used.
        // Using SimpleDateFormat to parse the expected date is safer.
        SimpleDateFormat sdf = new SimpleDateFormat(testDateFormatPattern);
        Date expectedDate = sdf.parse(dateString);

        Date actualDate = dateFormatter.parse(dateString, testLocale);
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void testParseFailure_invalidFormat() {
        // Specific stubbing for this test
        when(messageSource.getMessage("date.format", null, testLocale)).thenReturn(testDateFormatPattern);

        String invalidDateString = "15-03-2023"; // Incorrect format according to pattern
        Exception exception = assertThrows(ParseException.class, () -> {
            dateFormatter.parse(invalidDateString, testLocale);
        });
        assertNotNull(exception);
    }

    @Test
    void testParseFailure_invalidDate() {
        // Specific stubbing for this test
        when(messageSource.getMessage("date.format", null, testLocale)).thenReturn(testDateFormatPattern);

        String invalidDateString = "02/30/2023"; // Invalid date (Feb 30)
         Exception exception = assertThrows(ParseException.class, () -> {
            dateFormatter.parse(invalidDateString, testLocale);
        });
        assertNotNull(exception);
    }

    @Test
    void testPrintWithDifferentLocale() {
        Locale italianLocale = Locale.ITALY;
        String italianDateFormatPattern = "dd/MM/yyyy";
        when(messageSource.getMessage("date.format", null, italianLocale)).thenReturn(italianDateFormatPattern);

        Date date = new Date(1678838400000L); // 2023-03-15 00:00:00 GMT
        String expectedDateString = "15/03/2023";

        String actualDateString = dateFormatter.print(date, italianLocale);
        assertEquals(expectedDateString, actualDateString);
    }

    @Test
    void testParseWithDifferentLocaleSuccess() throws ParseException {
        Locale italianLocale = Locale.ITALY;
        String italianDateFormatPattern = "dd/MM/yyyy";
        when(messageSource.getMessage("date.format", null, italianLocale)).thenReturn(italianDateFormatPattern);

        String dateString = "15/03/2023";
        SimpleDateFormat sdf = new SimpleDateFormat(italianDateFormatPattern);
        Date expectedDate = sdf.parse(dateString);

        Date actualDate = dateFormatter.parse(dateString, italianLocale);
        assertEquals(expectedDate, actualDate);
    }
}
