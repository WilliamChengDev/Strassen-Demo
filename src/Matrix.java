package src;

import java.util.Random;

/**
 * A matrix object, works with a 2D int array in the background.
 */
public class Matrix {
        private int[][] matrix; // 2D array representation

        /**
         * if passed an integer, a matrix will be randomly generated with that number
         * size.
         * 
         * @param size
         */
        public Matrix(int size) {
                this.matrix = this.generateMatrix(size);
        }

        /**
         * directly pass a 2D array of integer to create matrix
         * 
         * @param matrix
         */
        public Matrix(int[][] matrix) {
                this.matrix = matrix;
        }

        /**
         * helper method that randomly generates a matrix
         * 
         * @param size
         * @return
         */
        private int[][] generateMatrix(int size) {
                int[][] ans = new int[size][size];
                Random rand = new Random();
                for (int r = 0; r < ans.length; r++) {
                        for (int c = 0; c < ans[0].length; c++) {
                                ans[r][c] = rand.nextInt(101);
                        }
                }
                return ans;
        }

        /**
         * multiplies two matrices, called private strassenMultiply
         * 
         * @param B
         * @return
         */
        public Matrix mutltiply(Matrix B) {
                int[][] result = strassenMultiply(B.getMatrix().length * B.getMatrix().length, this.matrix,
                                B.getMatrix());
                return new Matrix(result);
        }

        /**
         * returns the 2D integer array of the matrix object
         * 
         * @return
         */
        public int[][] getMatrix() {
                return this.matrix;
        }

        /**
         * the strassen's algorithm to multiply to matrices
         * 
         * @param size
         * @param A
         * @param B
         * @return
         */
        private int[][] strassenMultiply(int size, int[][] A, int[][] B) {
                // base case n == 1
                if (size == 1) {
                        int[][] ans = new int[1][1];
                        ans[0][0] = A[0][0] * B[0][0];
                        return ans;
                }
                /* partiion matrix M into 4 matrices of n/2*n/2 */
                int[][][] aPartition = partition(A);
                int[][][] bPartition = partition(B);
                int[][] A11 = aPartition[0];
                int[][] A12 = aPartition[1];
                int[][] A21 = aPartition[2];
                int[][] A22 = aPartition[3];
                int[][] B11 = bPartition[0];
                int[][] B12 = bPartition[1];
                int[][] B21 = bPartition[2];
                int[][] B22 = bPartition[3];

                /* calculate the 7 matrix multiplications */
                int[][] P1 = strassenMultiply(size / 4, A11, subtract(B12, B22));
                int[][] P2 = strassenMultiply(size / 4, add(A11, A12), B22);
                int[][] P3 = strassenMultiply(size / 4, add(A21, A22), B11);
                int[][] P4 = strassenMultiply(size / 4, A22, subtract(B21, B11));
                int[][] P5 = strassenMultiply(size / 4, add(A11, A22),
                                add(B11, B22));
                int[][] P6 = strassenMultiply(size / 4, subtract(A12, A22),
                                add(B21, B22));
                int[][] P7 = strassenMultiply(size / 4, subtract(A11, A21),
                                add(B11, B12));
                // calculating partitions
                int[][] C11 = add(subtract(add(P5, P4), P2), P6);
                int[][] C12 = add(P1, P2);
                int[][] C21 = add(P3, P4);
                int[][] C22 = subtract(subtract(add(P1, P5), P3), P7);
                // merge partitions
                return merge(C11, C12, C21, C22);
        }

        /**
         * helper method for strassen's, merges the four partitioned matrices to get the
         * multiplied matrix
         * 
         * @param C11
         * @param C12
         * @param C21
         * @param C22
         * @return
         */
        public int[][] merge(int[][] C11, int[][] C12, int[][] C21, int[][] C22) {
                int[][] ans = new int[C11.length * 2][C11.length * 2];
                for (int r = 0; r < ans.length; r++) {
                        for (int c = 0; c < ans[0].length; c++) {
                                if (r < ans.length / 2) {
                                        if (c < ans.length / 2) {
                                                ans[r][c] = C11[r][c];
                                        } else {
                                                ans[r][c] = C12[r][c - ans.length / 2];
                                        }
                                } else {
                                        if (c < ans.length / 2) {
                                                ans[r][c] = C21[r - ans.length / 2][c];
                                        } else {
                                                ans[r][c] = C22[r - ans.length / 2][c - ans.length / 2];
                                        }
                                }
                        }
                }
                return ans;
        }

        /**
         * adds two matrices
         * 
         * @param B
         * @return
         */
        public Matrix add(Matrix B) {
                int[][] result = add(this.matrix, B.getMatrix());
                return new Matrix(result);
        }

        /**
         * helper method to add two matrices, for hidden code only
         * 
         * @param A
         * @param B
         * @return
         */
        private int[][] add(int[][] A, int[][] B) {
                int[][] ans = new int[B.length][B.length];
                for (int r = 0; r < B.length; r++) {
                        for (int c = 0; c < B.length; c++) {
                                ans[r][c] = A[r][c] + B[r][c];
                        }
                }
                return ans;
        }

        /**
         * public subtraction operator for Matrix object
         * 
         * @param B
         * @return
         */
        public Matrix subtract(Matrix B) {
                int[][] result = subtract(this.matrix, B.getMatrix());
                return new Matrix(result);
        }

        /**
         * private helper method to subtract two matrices
         * 
         * @param A
         * @param B
         * @return
         */
        private int[][] subtract(int[][] A, int[][] B) {
                int[][] ans = new int[B.length][B.length];
                for (int r = 0; r < B.length; r++) {
                        for (int c = 0; c < B.length; c++) {
                                ans[r][c] = A[r][c] - B[r][c];
                        }
                }
                return ans;
        }

        /**
         * partitions a matrix into 4 sections, M11, M12, M21, M22
         * returns an array of sub-matrices in that order
         * 
         * @param matrix
         * @return
         */
        private int[][][] partition(int[][] matrix) {
                int[][][] ans = new int[4][matrix.length / 2][matrix.length / 2];
                int[][] M11 = new int[matrix.length / 2][matrix.length / 2];
                int[][] M12 = new int[matrix.length / 2][matrix.length / 2];
                int[][] M21 = new int[matrix.length / 2][matrix.length / 2];
                int[][] M22 = new int[matrix.length / 2][matrix.length / 2];
                for (int r = 0; r < matrix.length; r++) {
                        for (int c = 0; c < matrix[0].length; c++) {
                                if (r < matrix.length / 2) {
                                        if (c < matrix[0].length / 2) {
                                                M11[r][c] = matrix[r][c];
                                        } else {
                                                M12[r][c - matrix.length / 2] = matrix[r][c];
                                        }
                                } else {
                                        if (c < matrix[0].length / 2) {
                                                M21[r - matrix.length / 2][c] = matrix[r][c];
                                        } else {
                                                M22[r - matrix.length / 2][c - matrix.length / 2] = matrix[r][c];
                                        }
                                }
                        }
                }
                ans[0] = M11;
                ans[1] = M12;
                ans[2] = M21;
                ans[3] = M22;
                return ans;
        }

        /**
         * returns a string representation of the matrix
         */
        public String toString() {
                String ans = "";
                for (int r = 0; r < this.matrix.length; r++) {
                        ans += "[ ";
                        for (int c = 0; c < this.matrix[0].length - 1; c++) {
                                ans += this.matrix[r][c] + ", ";
                        }
                        ans += this.matrix[r][this.matrix[0].length - 1] + " ]" + "\n";
                }
                return ans;
        }

        /**
         * public method for regular block matrix multiplication without strassen's
         * 
         * @param B
         * @return
         */
        public Matrix slowMultiply(Matrix B) {
                int[][] result = blockMultiply(matrix.length * matrix.length, this.matrix, B.getMatrix());
                return new Matrix(result);
        }

        /**
         * private method for the regular block matrix multiplication, with 8 multiplies
         * and O(n^3)
         * complexity
         * 
         * @param A
         * @param B
         * @return
         */
        private int[][] blockMultiply(int size, int[][] A, int[][] B) {
                if (size == 1) {
                        int[][] ans = new int[1][1];
                        ans[0][0] = A[0][0] * B[0][0];
                        return ans;
                }
                int[][][] aPartition = partition(A);
                int[][][] bPartition = partition(B);
                int[][] A11 = aPartition[0];
                int[][] A12 = aPartition[1];
                int[][] A21 = aPartition[2];
                int[][] A22 = aPartition[3];
                int[][] B11 = bPartition[0];
                int[][] B12 = bPartition[1];
                int[][] B21 = bPartition[2];
                int[][] B22 = bPartition[3];
                int[][] C11 = add(blockMultiply(size / 4, A11, B11), blockMultiply(size / 4, A12, B21));
                int[][] C12 = add(blockMultiply(size / 4, A11, B12), blockMultiply(size / 4, A12, B22));
                int[][] C21 = add(blockMultiply(size / 4, A21, B11), blockMultiply(size / 4, A22, B21));
                int[][] C22 = add(blockMultiply(size / 4, A21, B12), blockMultiply(size / 4, A22, B22));
                return merge(C11, C12, C21, C22);
        }

}