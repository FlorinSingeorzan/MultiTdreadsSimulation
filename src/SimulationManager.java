import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;

public class SimulationManager implements Runnable {
    private Random random;
    private Scheduler scheduler;
    public static SimulationFrame frame;
    private ArrayList<Client> generatedClients;
    private static int id = 0;

    public SimulationManager() {
        scheduler = new Scheduler(5);
        frame = new SimulationFrame();
    }

    private void setEffective() {
        for (HomeStore i : scheduler.getHomeStores()) {
            if (i.isOpen()) {
                for (Client j : i.coada) {
                    Consumer toSet = new Consumer();
                    toSet.setClient(j);
                    toSet.setWitchHome(i.getWitch());
                    frame.getConsumers().add(toSet);
                }
            }
        }
    }

    private void startThreds() {
        for (int i = 0; i < frame.getNrQueues(); i++) {
            if (scheduler.getHomeStores().get(i).isOpen() && !scheduler.getHomeThread().get(i).isAlive() && frame.isInvalidData() == false) {
                try {
                    scheduler.getHomeThread().get(i).start();
                } catch (Exception e) {
                }
            }
        }
    }

    private void openHomeStore() {
        for (int h = 0; h < frame.getNrQueues(); h++)
            scheduler.getHomeStores().get(h).setOpen(true);

    }

    private void generateClients() {
        int i = 100;          //numarul de clienti generati
        int lastArrival = 0;
        int forService;
        int arrival;
        int inInterval;
        if (frame.getMinServiceTime() >= frame.getMaxServiceTime() || frame.getMinInterval() >= frame.getMaxInterval() || frame.getMinInterval() == 0)
            frame.setInvalidData(true);         //seteaza invalid in cazul introducerii datelor necorespunzatoare
        while (i > 0 && frame.getIfRunning() == true) {
            Client toAdd = new Client();
            while (frame.isInvalidData() == true) {
            }
            forService = frame.getMinServiceTime() + random.nextInt(frame.getMaxServiceTime() - frame.getMinServiceTime());     //calculeaza timpul de servire random
            inInterval = random.nextInt(frame.getMaxInterval() - frame.getMinInterval()) + frame.getMinInterval();          //interval de sosire random
            arrival = lastArrival + inInterval;     //susirea efectiva dupa un interval de sosire a celor dinainte
            lastArrival = arrival;      //vechiul interval de sosire e actualizat
            id += 1;        //diferentiere clienti prin id
            if (arrival > (frame.getSimInterval() - forService))
                break;          //iesire din bucla daca au fost genereati prea multi clienti
            toAdd.setArrivalTime(arrival);          //adaugare date client
            toAdd.setIdClient(id);
            toAdd.setServiceTime(forService);
            generatedClients.add(toAdd);        //adaugare client
            i--;
        }
    }

    private void waitRun() {
        while (frame.getIfRunning() == false) {         //asteapta pana veine momentul de simulare
        }
    }

    @Override
    public synchronized void run() {
        waitRun();
        int y = 0;
        double waitingTime = 0.0;
        generatedClients = new ArrayList<>();
        random = new Random();
        openHomeStore();        //deschide casa
        generateClients();      //genereaza clienti
        int currentClient = 0;
        Collections.sort(generatedClients);
        scheduler.changeStrategy(SelectionPolicy.SHORTEST_TIME);
        while (frame.getIfRunning() == true) {
            while (generatedClients.size() > 0) {
                try {
                    scheduler.dispachClient(generatedClients.get(currentClient));       //adauga client in coada
                    generatedClients.remove(currentClient);     //eliminare clienr
                    currentClient++;
                } catch (Exception er) {
                    if (y == 0) {
                        setEffective();     //se va intrea pe ramura catch doar cand toti clienti sunt adaugati, se vor transmite pentru a fi desenati
                        y++;
                        waitingTime = scheduler.findAvgWaitingTime();     //calculeza timpul mediu de asteptare
                    }
                    break;
                }
            }
            startThreds();          //pornitr threaduri

            if (frame.getRunning().getSecondsPassed() > frame.getSimInterval()) {
                break;      //paraseste run la terminarea simularii
            }

        }
        if (y == 1) {             //pornitr threaduri
            try {               //calculeaza timpul mediu de asteptare si il pune in log
                Log logOut = new Log("logging.txt");
                logOut.logger.setLevel(Level.INFO);
                logOut.logger.info("Waiting time " + waitingTime);
            } catch (IOException e) {
            }
            y++;
        }

    }
}
