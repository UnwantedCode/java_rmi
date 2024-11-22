package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OddEvenSorter extends Remote {
      int[] sortArray(int[] array) throws RemoteException ;
}
