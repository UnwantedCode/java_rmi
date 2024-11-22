package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatrixMultiplier extends Remote {
    double[][] multiply(double[][] matrixA, double[][] matrixB) throws RemoteException;
}
