package agent;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AgentServer {
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.err.println("Brak identyfikatora agenta. Uruchom: java agent.AgentServer <id>");
                return;
            }
            String agentName = "Agent" + args[0];
            AgentImpl agent = new AgentImpl();
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(agentName, agent);
            System.out.println(agentName+" ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
