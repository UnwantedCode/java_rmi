package server;


import shared.Agent;
import shared.OddEvenSorter;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Arrays;

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
            while (!isSorted) {
                isSorted = true;

                System.out.println("[" + LocalDateTime.now() + "] Serwer: Rozpoczyna fazę parzystą.");
                int[] newArray = agentEven.sortPhase(array, true);
                if (!Arrays.equals(array, newArray)) {
                    isSorted = false;
                }
                array = newArray;

                System.out.println("[" + LocalDateTime.now() + "] Serwer: Rozpoczyna fazę nieparzystą.");
                newArray = agentOdd.sortPhase(array, false);
                if (!Arrays.equals(array, newArray)) {
                    isSorted = false;
                }
                array = newArray;
            }

            System.out.println("[" + LocalDateTime.now() + "] Serwer: Tablica posortowana: " + Arrays.toString(array));
            return array;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Błąd podczas sortowania tablicy.");
        }
    }
}