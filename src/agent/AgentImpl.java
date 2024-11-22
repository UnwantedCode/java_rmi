package agent;

import shared.Agent;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class AgentImpl extends UnicastRemoteObject implements Agent {
    public AgentImpl() throws RemoteException {}

    @Override
    public double calculatePartialDeterminant(double[][] matrix, boolean isMainDiagonal) throws RemoteException {
        System.out.println("[" + LocalDateTime.now() + "] Agent: Otrzymano macierz do obliczenia częściowego wyznacznika.");

        if (matrix.length != 3 || matrix[0].length != 3) {
            throw new IllegalArgumentException("Macierz musi być rozmiaru 3x3.");
        }

        double result = 0;

        if (isMainDiagonal) {
            result = (matrix[0][0] * matrix[1][1] * matrix[2][2]) +
                    (matrix[1][0] * matrix[2][1] * matrix[0][2]) +
                    (matrix[2][0] * matrix[0][1] * matrix[1][2]);
        } else {
            result = (matrix[0][2] * matrix[1][1] * matrix[2][0]) +
                    (matrix[1][2] * matrix[2][1] * matrix[0][0]) +
                    (matrix[2][2] * matrix[0][1] * matrix[1][0]);
        }

        System.out.println("[" + LocalDateTime.now() + "] Agent: Wynik częściowy obliczony: " + result);
        return result;
    }
}
