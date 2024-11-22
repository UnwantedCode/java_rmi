package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MaxFinder extends Remote {
      int findMax(int[] array) throws RemoteException;
}
