package server;


import shared.Agent;
import shared.MaxFinder;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Arrays;

public class MaxFinderImpl extends UnicastRemoteObject implements MaxFinder {
    public MaxFinderImpl() throws RemoteException {}

    public int findMax(int[] array) throws RemoteException {
        System.out.println("[" + LocalDateTime.now() + "] Serwer: Otrzymano tablicę: " + Arrays.toString(array));

        int mid = array.length / 2;
        int[] firstHalf = Arrays.copyOfRange(array, 0, mid);
        int[] secondHalf = Arrays.copyOfRange(array, mid, array.length);

        try {
            Registry registry = LocateRegistry.getRegistry();
            Agent agent0 = (Agent) registry.lookup("Agent0");
            Agent agent1 = (Agent) registry.lookup("Agent1");

            System.out.println("[" + LocalDateTime.now() + "] Serwer: Zlecanie Agentowi 0 wyszukanie maksimum w pierwszej połowie.");
            int max1 = agent0.findMax(firstHalf);

            System.out.println("[" + LocalDateTime.now() + "] Serwer: Zlecanie Agentowi 1 wyszukanie maksimum w drugiej połowie.");
            int max2 = agent1.findMax(secondHalf);

            int globalMax = Math.max(max1, max2);
            System.out.println("[" + LocalDateTime.now() + "] Serwer: Globalne maksimum: " + globalMax);
            return globalMax;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Błąd podczas wyszukiwania maksimum.");
        }
    }
}