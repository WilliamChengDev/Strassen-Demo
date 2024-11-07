package tests;

import static org.junit.Assert.assertArrayEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import src.Matrix;

public class StrassenTest {

        private static final int MATRIX_SIZE = 1024; // adjust matrix size here
        private static Matrix A;
        private static Matrix B;

        @BeforeAll
        public static void createMatrices() {
                A = new Matrix(MATRIX_SIZE);
                B = new Matrix(MATRIX_SIZE);
        }

        /**
         * tests that the results of strassen's algorithm is the same as the block
         * multiplication algorithm
         */
        @Test
        public void strassenAccuracyTest() {
                Matrix expected = A.slowMultiply(B);
                Matrix actual = A.mutltiply(B);

                assertArrayEquals(expected.getMatrix(), actual.getMatrix());
        }

        /**
         * records the speed of the strassen's algorithm
         */
        @Test
        public void strassenSpeedTest() {
                A.mutltiply(B);
        }

        /**
         * records the speed of the block multiplication algorithm
         */
        @Test
        public void blockMultiplicationSpeedTest() {
                A.slowMultiply(B);
        }

}