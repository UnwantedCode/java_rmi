package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Agent extends Remote {
    int findMax(int[] subArray) throws RemoteException;
}
