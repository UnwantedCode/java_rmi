package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            OddEvenSorterImpl server = new OddEvenSorterImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("OddEvenSorter", server);
            System.out.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

