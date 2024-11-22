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

public class MatrixMultiplierImpl extends UnicastRemoteObject implements MatrixMultiplier {
    public MatrixMultiplierImpl() throws RemoteException {}

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) throws RemoteException {
        int size = matrixA.length;
        double[][] result = new double[size][size];

        ExecutorService executor = Executors.newFixedThreadPool(size);
        System.out.println("[" + LocalDateTime.now() + "] Serwer: Otrzymano zadanie mnożenia macierzy.");
        System.out.println("Macierz A:");
        printMatrix(matrixA);
        System.out.println("Macierz B:");
        printMatrix(matrixB);
        try {
            Registry registry = LocateRegistry.getRegistry();
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                final int row = i;
                final int agentNumber = i + 1;
                Agent agent = (Agent) registry.lookup("Agent" + agentNumber);

                System.out.println("[" + LocalDateTime.now() + "] Serwer: Wysyłanie wiersza " + row + " do Agenta" + agentNumber);

                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        double[] column = getColumn(matrixB, row);
                        double[] rowResult = agent.multiplyFragment(matrixA, column);
                        synchronized (result) {
                            result[row] = rowResult;
                        }
                        System.out.println("[" + LocalDateTime.now() + "] Serwer: Odebrano wynik od Agenta" + agentNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, executor);

                futures.add(future);
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

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

    private double[] getColumn(double[][] matrix, int column) {
        double[] col = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            col[i] = matrix[i][column];
        }
        return col;
    }
}

