package agent;

import shared.Agent;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class AgentImpl extends UnicastRemoteObject implements Agent {
    public AgentImpl() throws RemoteException {}

    @Override
    public double[] multiplyFragment(double[][] matrixA, double[] vectorB) throws RemoteException {
        System.out.println("[" + LocalDateTime.now() + "] Agent: Otrzymano fragment macierzy do obliczenia.");
        int size = matrixA.length;
        double[] result = new double[size];

        for (int i = 0; i < size; i++) {
            result[i] = 0;
            for (int j = 0; j < size; j++) {
                result[i] += matrixA[i][j] * vectorB[j];
            }
        }
        System.out.println("[" + LocalDateTime.now() + "] Agent: Wynik obliczony i zwracany do serwera.");
        return result;
    }
}
