package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Agent extends Remote {
    double[] multiplyFragment(double[][] matrixA, double[] vectorB) throws RemoteException;
}
