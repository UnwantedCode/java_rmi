package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            MatrixAdderImpl server = new MatrixAdderImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("MatrixAdder", server);
            System.out.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

