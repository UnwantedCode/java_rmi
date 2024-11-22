package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Agent extends Remote {
    double[] addFragment(double[] rowA, double[] rowB) throws RemoteException;
}
