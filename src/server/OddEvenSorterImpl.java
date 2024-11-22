package server;


import shared.Agent;
import shared.OddEvenSorter;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OddEvenSorterImpl extends UnicastRemoteObject implements OddEvenSorter {
    public OddEvenSorterImpl() throws RemoteException {}

    @Override
    public int[] sortArray(int[] array) throws RemoteException {
        System.out.println("[" + LocalDateTime.now() + "] Serwer: Otrzymano tablicę do posortowania: " + Arrays.toString(array));

        try {
            Registry registry = LocateRegistry.getRegistry();
            Agent agentEven = (Agent) registry.lookup("Agent0");
            Agent agentOdd = (Agent) registry.lookup("Agent1");

            boolean isSorted = false;
            ExecutorService executor = Executors.newFixedThreadPool(2);
            while (!isSorted) {
                isSorted = true;
                System.out.println("[" + LocalDateTime.now() + "] Serwer: Rozpoczyna fazę parzystą.");
                int[] evenPhaseArray = Arrays.copyOf(array, array.length);
                Future<int[]> evenPhaseFuture = executor.submit(() -> agentEven.sortPhase(evenPhaseArray, true));
                int[] evenSortedArray = evenPhaseFuture.get();

                System.out.println("[" + LocalDateTime.now() + "] Serwer: Rozpoczyna fazę nieparzystą.");
                int[] oddPhaseArray = Arrays.copyOf(evenSortedArray, evenSortedArray.length);
                Future<int[]> oddPhaseFuture = executor.submit(() -> agentOdd.sortPhase(oddPhaseArray, false));
                int[] oddSortedArray = oddPhaseFuture.get();

                if (!Arrays.equals(array, oddSortedArray)) {
                    isSorted = false;
                }
                array = Arrays.copyOf(oddSortedArray, oddSortedArray.length);

                System.out.println("[" + LocalDateTime.now() + "] Serwer: Aktualny stan tablicy: " + Arrays.toString(array));
            }

            executor.shutdown();
            System.out.println("[" + LocalDateTime.now() + "] Serwer: Tablica posortowana: " + Arrays.toString(array));
            return array;
        }  catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Błąd podczas sortowania tablicy.");
        }
    }
}