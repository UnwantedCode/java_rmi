package agent;

import shared.Agent;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class AgentImpl extends UnicastRemoteObject implements Agent {
    public AgentImpl() throws RemoteException {}

    @Override
    public int[] sortPhase(int[] array, boolean isEvenPhase) throws RemoteException {
        System.out.println("[" + LocalDateTime.now() + "] Agent: Rozpoczęcie fazy " + (isEvenPhase ? "even" : "odd"));

        int startIndex = isEvenPhase ? 0 : 1;

        for (int i = startIndex; i < array.length - 1; i += 2) {
            if (array[i] > array[i + 1]) {
                int temp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = temp;
            }
        }

        System.out.println("[" + LocalDateTime.now() + "] Agent: Faza zakończona. Tablica: ");
        for (int val : array) {
            System.out.print(val + " ");
        }
        System.out.println();
        return array;
    }
}
