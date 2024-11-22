package client;

import shared.MatrixMultiplier;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            MatrixMultiplier server = (MatrixMultiplier) registry.lookup("MatrixMultiplier");

            double[][] matrixA = {
                    {1, 2},
                    {3, 4}
            };
            double[][] matrixB = {
                    {5, 6},
                    {7, 8}
            };

            System.out.println("[" + LocalDateTime.now() + "] Klient: Wysyłanie zadań do serwera...");
            double[][] result = server.multiply(matrixA, matrixB);
            System.out.println("[" + LocalDateTime.now() + "] Klient: Wynik odebrany od serwera:");
            System.out.println("Wynik mnożenia macierzy:");
            for (double[] row : result) {
                for (double val : row) {
                    System.out.printf("%.2f ", val);
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}

