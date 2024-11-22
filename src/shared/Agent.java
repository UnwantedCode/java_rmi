package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Agent extends Remote {
    int[] sortPhase(int[] array, boolean isEvenPhase) throws RemoteException;
}
