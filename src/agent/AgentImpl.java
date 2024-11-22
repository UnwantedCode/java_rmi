package agent;

import shared.Agent;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class AgentImpl extends UnicastRemoteObject implements Agent {
    public AgentImpl() throws RemoteException {}

    @Override
    public int findMax(int[] subArray) throws RemoteException {
        System.out.println("[" + LocalDateTime.now() + "] Agent: Szukanie maksimum w podtablicy: ");
        for (int val : subArray) {
            System.out.print(val + " ");
        }
        System.out.println();

        int max = Integer.MIN_VALUE;
        for (int val : subArray) {
            if (val > max) {
                max = val;
            }
        }

        System.out.println("[" + LocalDateTime.now() + "] Agent: Maksimum w podtablicy: " + max);
        return max;
    }
}
