package client;

import shared.MatrixDeterminant;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();

            MatrixDeterminant server = (MatrixDeterminant) registry.lookup("MatrixDeterminant");

            double[][] matrix = {
                    {3, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}
            };
            for (double[] row : matrix) {
                for (double val : row) {
                    System.out.printf("%.2f ", val);
                }
                System.out.println();
            }
            System.out.println("[" + LocalDateTime.now() + "] Klient: Wysyłanie macierzy do serwera...");
            double determinant = server.calculateDeterminant(matrix);

            System.out.println("[" + LocalDateTime.now() + "] Klient: Wyznacznik odebrany od serwera: " + determinant);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

