package org.aksw.palmetto.prob;

import java.util.Arrays;
import java.util.Collection;

import org.aksw.palmetto.corpus.SlidingWindowSupportingAdapter;
import org.aksw.palmetto.subsets.SubsetDefinition;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.carrotsearch.hppc.IntArrayList;
import com.carrotsearch.hppc.IntIntOpenHashMap;
import com.carrotsearch.hppc.IntObjectOpenHashMap;

@RunWith(Parameterized.class)
@Deprecated
public class BooleanSlidingWindowProbabilitySupplierInversProbsTest implements SlidingWindowSupportingAdapter {

    private static final double DOUBLE_PRECISION_DELTA = 0.00000001;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays
                .asList(new Object[][] {
                        /*
                         * a simple test document
                         * A B C B A C C
                         */
                        // We ask for A and ¬B with a window size of 3 (4 x A C)
                        {
                                7, new int[][] { { 0, 4 }, { 1, 3 }, { 2, 5, 6 } },
                                3, 1, 2, 1.0 / 5.0 },
                        // We ask for A and ¬C with a window size of 3 (2 x A B)
                        {
                                7, new int[][] { { 0, 4 }, { 1, 3 }, { 2, 5, 6 } },
                                3, 1, 4, 0 },
                        // We ask for B and ¬A with a window size of 3 (3 x B C)
                        {
                                7, new int[][] { { 0, 4 }, { 1, 3 }, { 2, 5, 6 } },
                                3, 2, 1, 1.0 / 5.0 },
                        // We ask for B and ¬C with a window size of 3 (2 x A B)
                        {
                                7, new int[][] { { 0, 4 }, { 1, 3 }, { 2, 5, 6 } },
                                3, 2, 4, 0 },
                        // We ask for C and ¬A with a window size of 3 (3 x B C)
                        {
                                7, new int[][] { { 0, 4 }, { 1, 3 }, { 2, 5, 6 } },
                                3, 4, 1, 1.0 / 5.0 },
                        // We ask for C and ¬B with a window size of 3 (4 x C A)
                        {
                                7, new int[][] { { 0, 4 }, { 1, 3 }, { 2, 5, 6 } },
                                3, 4, 2, 1.0 / 5.0 },
                        // We ask for A and ¬B with a window size of 4 (4 x A C)
                        {
                                7, new int[][] { { 0, 4 }, { 1, 3 }, { 2, 5, 6 } },
                                4, 1, 2, 0 } });
    }

    private int histogram[][];
    private int docLength;
    private int positions[][];
    private int windowSize;
    private int wordSetDef;
    private int inversePartDef;
    private double expectedProbability;

    public BooleanSlidingWindowProbabilitySupplierInversProbsTest(int docLength, int[][] positions, int windowSize,
            int wordSetDef, int inversePartDef, double expectedProbability) {
        this.docLength = docLength;
        this.histogram = new int[][] { { docLength, 1 } };
        this.positions = positions;
        this.windowSize = windowSize;
        this.wordSetDef = wordSetDef;
        this.inversePartDef = inversePartDef;
        this.expectedProbability = expectedProbability;
    }

    @Ignore
    @Test
    public void test() {
        BooleanSlidingWindowFrequencyDeterminer determiner = new BooleanSlidingWindowFrequencyDeterminer(this,
                windowSize);
        SlidingWindowProbabilitySupplier supplier = new SlidingWindowProbabilitySupplier(determiner);
        supplier.setMinFrequency(1);

        double probabilities[] = supplier.getProbabilities(new String[][] { { "A", "B", "C" } },
                new SubsetDefinition[] { new SubsetDefinition(
                        new int[0], new int[0][0], null) })[0].probabilities;
        Assert.assertEquals(expectedProbability,
                supplier.getInverseProbability(wordSetDef, inversePartDef, probabilities), DOUBLE_PRECISION_DELTA);
    }

    @Override
    public int[][] getDocumentSizeHistogram() {
        return histogram;
    }

    @Override
    public IntObjectOpenHashMap<IntArrayList[]> requestWordPositionsInDocuments(String[] words,
            IntIntOpenHashMap docLengths) {
        IntObjectOpenHashMap<IntArrayList[]> positionsInDocuments = new IntObjectOpenHashMap<IntArrayList[]>();
        IntArrayList[] positionsInDocument = new IntArrayList[positions.length];
        for (int i = 0; i < positionsInDocument.length; ++i) {
            if (positions[i].length > 0) {
                positionsInDocument[i] = new IntArrayList();
                positionsInDocument[i].add(positions[i]);
            }
        }
        positionsInDocuments.put(0, positionsInDocument);
        docLengths.put(0, docLength);
        return positionsInDocuments;
    }
}
