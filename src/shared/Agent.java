package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Agent extends Remote {
    double calculatePartialDeterminant(double[][] matrix, boolean isMainDiagonal) throws RemoteException;
}
