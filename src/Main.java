package src;

public class Main {
        public static void main(String[] args) {
                Matrix A = new Matrix(8);
                System.out.println("A: \n" + A.toString());
                Matrix B = new Matrix(8);
                System.out.println("B: \n" + B.toString());
                Matrix result = A.mutltiply(B);

                System.out.println("Result: \n" + result.toString());
        }
}