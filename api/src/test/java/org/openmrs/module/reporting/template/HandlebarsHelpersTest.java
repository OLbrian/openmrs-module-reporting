package org.openmrs.module.reporting.template;

import com.github.jknack.handlebars.Options;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.api.ConceptService;
import org.openmrs.messagesource.MessageSourceService;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
public class HandlebarsHelpersTest {

    @Test
    public void testMessage() throws Exception {
        String prefix = "ui.i18n.Location.";
        String uuid = "abc-123-uuid";
        String expected = "translated";
        MessageSourceService mss = mock(MessageSourceService.class);
        when(mss.getMessage(prefix + uuid)).thenReturn(expected);

        Options options = mock(Options.class);
        when(options.hash("prefix", "")).thenReturn(prefix);
        when(options.hash("suffix", "")).thenReturn("");

        HandlebarsHelpers helpers = new HandlebarsHelpers(mss, null);
        CharSequence message = helpers.message("abc-123-uuid", options);

        assertThat(message.toString(), is(expected));
    }

    @Test
    public void testConceptName() throws Exception {
        String source = "PIH";
        String code = "ClinicalVisit";
        String expected = "Clinical Visit";

        Concept concept = new Concept();
        concept.addName(new ConceptName(expected, Locale.ENGLISH));

        ConceptService conceptService = mock(ConceptService.class);
        when(conceptService.getConceptByMapping(code, source)).thenReturn(concept);

        HandlebarsHelpers helpers = new HandlebarsHelpers(null, conceptService);
        CharSequence conceptName = helpers.conceptName(source + ":" + code);

        assertThat(conceptName.toString(), is(expected));

    }

}
