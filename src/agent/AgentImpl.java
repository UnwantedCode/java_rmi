package agent;

import shared.Agent;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class AgentImpl extends UnicastRemoteObject implements Agent {
    public AgentImpl() throws RemoteException {}

    @Override
    public double[] addFragment(double[] rowA, double[] rowB) throws RemoteException {
        System.out.println("[" + LocalDateTime.now() + "] Agent: Otrzymano fragment macierzy do dodania.");

        int size = rowA.length;
        double[] result = new double[size];

        for (int i = 0; i < size; i++) {
            result[i] = rowA[i] + rowB[i];
        }

        System.out.println("[" + LocalDateTime.now() + "] Agent: Wynik obliczony i zwracany do serwera.");
        return result;
    }
}
