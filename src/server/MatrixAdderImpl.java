package server;

import shared.Agent;
import shared.MatrixMultiplier;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MatrixAdderImpl extends UnicastRemoteObject implements MatrixMultiplier {
    public MatrixAdderImpl() throws RemoteException {}

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) throws RemoteException {
        int size = matrixA.length;
        double[][] result = new double[size][size];

        ExecutorService executor = Executors.newFixedThreadPool(size);
        System.out.println("[" + LocalDateTime.now() + "] Serwer: Otrzymano zadanie dodawania macierzy.");
        System.out.println("Macierz A:");
        printMatrix(matrixA);
        System.out.println("Macierz B:");
        printMatrix(matrixB);

        try {
            Registry registry = LocateRegistry.getRegistry();
            List<Future<double[]>> futures = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                final int row = i;
                Agent agent = (Agent) registry.lookup("Agent" + row);

                System.out.println("[" + LocalDateTime.now() + "] Serwer: Wysyłanie wiersza " + row + " do Agenta" + row);

                Future<double[]> future = executor.submit(() -> {
                    double[] rowResult = agent.addFragment(matrixA[row], matrixB[row]);
                    System.out.println("[" + LocalDateTime.now() + "] Serwer: Odebrano wynik od Agenta" + row);
                    return rowResult;
                });
                futures.add(future);
            }

            for (int i = 0; i < size; i++) {
                result[i] = futures.get(i).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return result;
    }

    private void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.printf("%.2f ", val);
            }
            System.out.println();
        }
    }
}
