package server;


import shared.Agent;
import shared.MatrixDeterminant;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MatrixDeterminantImpl extends UnicastRemoteObject implements MatrixDeterminant {
    public MatrixDeterminantImpl() throws RemoteException {}

    @Override
    public double calculateDeterminant(double[][] matrix) throws RemoteException {
        System.out.println("[" + LocalDateTime.now() + "] Serwer: Otrzymano zadanie obliczenia wyznacznika.");

        try {
            Registry registry = LocateRegistry.getRegistry();
            Agent agent0 = (Agent) registry.lookup("Agent0");
            Agent agent1 = (Agent) registry.lookup("Agent1");

            ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<Double> mainDiagonalFuture = executor.submit(() -> agent0.calculatePartialDeterminant(matrix, true));
            Future<Double> antiDiagonalFuture = executor.submit(() -> agent1.calculatePartialDeterminant(matrix, false));

            double mainDiagonalSum = mainDiagonalFuture.get();
            double antiDiagonalSum = antiDiagonalFuture.get();

            executor.shutdown();

            double determinant = mainDiagonalSum - antiDiagonalSum;

            System.out.println("[" + LocalDateTime.now() + "] Serwer: Wyznacznik obliczony: " + determinant);
            return determinant;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Błąd podczas obliczania wyznacznika.");
        }
    }
}