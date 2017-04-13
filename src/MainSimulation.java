
public class MainSimulation {

    public static void main(String[] args) {
        SimulationManager sMan;
        sMan = new SimulationManager();         //creaza un obiect de tipul managerului de simulare
        Thread principalThread = new Thread(sMan);
        principalThread.start();         //porneste threadul acestuia
    }
}
