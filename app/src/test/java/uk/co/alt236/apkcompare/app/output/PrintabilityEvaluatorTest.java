package uk.co.alt236.apkcompare.app.output;

import org.junit.Before;
import org.junit.Test;
import uk.co.alt236.apkcompare.app.comparators.results.ComparisonResult;
import uk.co.alt236.apkcompare.app.comparators.results.Similarity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PrintabilityEvaluatorTest {

    private PrintabilityEvaluator cut;

    @Before
    public void setUp() {
        cut = new PrintabilityEvaluator();
    }

    @Test
    public void shouldPrintAnythingIfVerbose() {
        final boolean verbose = true;

        for (final Similarity similarity : Similarity.values()) {
            final ComparisonResult result = mock(ComparisonResult.class);
            when(result.getSimilarity()).thenReturn(similarity);

            assertTrue("Should have returned true for: " + similarity, cut.isPrintable(result, verbose));

        }
    }

    @Test
    public void shouldOnlyPrintIfDifferentWhenNotVerbose() {
        final boolean verbose = false;

        for (final Similarity similarity : Similarity.values()) {
            final ComparisonResult result = mock(ComparisonResult.class);
            when(result.getSimilarity()).thenReturn(similarity);


            if (similarity == Similarity.DIFFERENT) {
                assertTrue("Should have returned true for: " + similarity, cut.isPrintable(result, verbose));
            } else {
                assertFalse("Should have returned false for: " + similarity, cut.isPrintable(result, verbose));
            }
        }
    }
}