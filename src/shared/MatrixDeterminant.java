package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatrixDeterminant extends Remote {
     double calculateDeterminant(double[][] matrix) throws RemoteException;
}
