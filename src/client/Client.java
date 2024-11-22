package client;

import shared.MaxFinder;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();

            MaxFinder server = (MaxFinder) registry.lookup("MaxFinder");

            int[] array = {6, 3, 8, 2, 7, 5};

            System.out.println("[" + LocalDateTime.now() + "] Klient: Wysyłanie tablicy do serwera...");
            int max = server.findMax(array);

            System.out.println("[" + LocalDateTime.now() + "] Klient: Maksimum tablicy: " + max);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

