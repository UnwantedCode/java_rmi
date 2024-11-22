package server;


import shared.Agent;
import shared.MatrixDeterminant;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

public class MatrixDeterminantImpl extends UnicastRemoteObject implements MatrixDeterminant {
    public MatrixDeterminantImpl() throws RemoteException {}

    @Override
    public double calculateDeterminant(double[][] matrix) throws RemoteException {
        System.out.println("[" + LocalDateTime.now() + "] Serwer: Otrzymano zadanie obliczenia wyznacznika.");

        try {
            Registry registry = LocateRegistry.getRegistry();
            Agent agent0 = (Agent) registry.lookup("Agent0");
            Agent agent1 = (Agent) registry.lookup("Agent1");

            System.out.println("[" + LocalDateTime.now() + "] Serwer: Zlecanie obliczeń Agentowi 0 (przekątne główne).");
            double mainDiagonalSum = agent0.calculatePartialDeterminant(matrix, true);

            System.out.println("[" + LocalDateTime.now() + "] Serwer: Zlecanie obliczeń Agentowi 1 (przekątne przeciwne).");
            double antiDiagonalSum = agent1.calculatePartialDeterminant(matrix, false);

            double determinant = mainDiagonalSum - antiDiagonalSum;

            System.out.println("[" + LocalDateTime.now() + "] Serwer: Wyznacznik obliczony: " + determinant);
            return determinant;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Błąd podczas obliczania wyznacznika.");
        }
    }
}