public class Main {
        public static void main(String[] args) {
                Matrix A = new Matrix(2);
                Matrix B = new Matrix(2);
                System.out.println("A: \n" + A.toString());
                System.out.println("B: \n" + B.toString());
                Matrix C = A.mutltiply(B);
                System.out.println("Strassen's: \n" + C.toString());

                Matrix D = A.slowMultiply(B);
                System.out.println("Regular: \n" + D.toString());
        }
}