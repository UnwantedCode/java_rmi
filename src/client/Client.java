package client;

import shared.OddEvenSorter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();

            OddEvenSorter server = (OddEvenSorter) registry.lookup("OddEvenSorter");

            int[] array = {5, 1, 4, 2, 3};

            System.out.println("[" + LocalDateTime.now() + "] Klient: Wysyłanie tablicy do serwera: " + Arrays.toString(array));
            int[] sortedArray = server.sortArray(array);

            System.out.println("[" + LocalDateTime.now() + "] Klient: Otrzymana posortowana tablica: " + Arrays.toString(sortedArray));
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

