package server;


import shared.Agent;
import shared.MaxFinder;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

            ExecutorService executor = Executors.newFixedThreadPool(2);

            System.out.println("[" + LocalDateTime.now() + "] Serwer: Zlecanie Agentowi 0 wyszukanie maksimum w pierwszej połowie.");
            Future<Integer> max1Future = executor.submit(() -> agent0.findMax(firstHalf));
            System.out.println("[" + LocalDateTime.now() + "] Serwer: Zlecanie Agentowi 1 wyszukanie maksimum w drugiej połowie.");
            Future<Integer> max2Future = executor.submit(() -> agent1.findMax(secondHalf));

            int max1 = max1Future.get();
            int max2 = max2Future.get();

            executor.shutdown();
            int globalMax = Math.max(max1, max2);
            System.out.println("[" + LocalDateTime.now() + "] Serwer: Globalne maksimum: " + globalMax);
            return globalMax;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Błąd podczas wyszukiwania maksimum.");
        }
    }
}