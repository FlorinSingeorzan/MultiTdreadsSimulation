import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;

public class HomeStore implements Runnable {

    public BlockingQueue<Client> coada;        //queue de clienti publica
    private int witch;          //numarul cozii
    private boolean open = false;

    public HomeStore() {
        this.coada = new LinkedBlockingDeque<Client>();
    }

    public synchronized void addClient(Client client) {
        coada.add(client);                  //adaugare in coada curenta
        findWaitingTime();                  //calculare timpi de asteptare;
    }

    public int getWaitingTime() {
        int sum = 0;
        for (Client cl : coada) {
            sum += cl.getServiceTime();       //timpii totali de servire
        }
        return sum;
    }

    //metode pentru obtinere si setare parametri specifici
    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public void setWitch(int witch) {
        this.witch = witch;
    }

    public int getWitch() {
        return witch;
    }

    private void findWaitingTime() {             //determinare timp de asteptare
        int actualWaiting = 0;
        for (Client it : coada) {              //pentru fiecare client se calculeaza timul de asteptare in functie de timpul de sosire servire si asteptare pentru a fi serviti clientii din fata lui
            if (!it.isServed()) {
                actualWaiting -= it.getArrivalTime();
                if (actualWaiting < 0)
                    actualWaiting = 0;
                it.setWaitingTime(actualWaiting);
                actualWaiting = it.getServiceTime() + it.getWaitingTime() + it.getArrivalTime();
            }
        }
    }

    public boolean mustServe() {     //determina daca in coada mai sunt clienti care trebuie serviti
        for (Client i : this.coada) {
            if (!i.isServed()) {
                return true;
            }
        }
        return false;
    }

    private Client nextServed() {         //verifica daca mai sunt clienti de servit
        for (Client i : this.coada) {
            if (!i.isServed()) {
                return i;           //returneaza urmatorul client ce trebuie servit
            }
        }
        return null;
    }

    public synchronized void run() {           //metoda apelata la pornirea unui thread de tipul clasei
        if (open) {
            int firstTime = 0;
            System.out.println("O noua casa a fost deschisa ");
            for (Client it : coada) {
                if (!it.isServed()) {
                    Client actual;
                    actual = this.nextServed();
                    it.setFinishTime(it.getServiceTime() + it.getWaitingTime());        //se seteaza timpul de iesire din coada relativ la intrare
                    if (firstTime == 0) {
                        while (SimulationManager.frame.getRunning().getSecondsPassed() != actual.getArrivalTime()) {
                        } //threadul e blocat intr-0 bucla nula pana la sosirea primului client
                    }
                    firstTime++;
                    try {
                        Log logOut = new Log("logging.txt");
                        logOut.logger.setLevel(Level.INFO);
                        logOut.logger.info("Clientul " + actual.getIdClient() + " a fost servit la casa " + witch + " a sosit la " + actual.getArrivalTime() + " a asteptat " + actual.getWaitingTime() + " a terminat la " + actual.getFinishTime() + " si a stat la casa " + actual.getServiceTime() + "    secunda " + (actual.getWaitingTime() + actual.getArrivalTime() + actual.getServiceTime()));
                    } catch (IOException e) {
                    }
                    it.setServed(true);         //seteaza clientul ca servit
                    try {
                        coada.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (mustServe()) {           //asteapta o perioada de timp
                        while (SimulationManager.frame.getRunning().getSecondsPassed() != coada.peek().getArrivalTime()) {
                        } //threadul e blocat intr-0 bucla nula pana la sosirea urmatorului client
                    }
                }
            }
        }
        System.out.println("Casa " + witch + " a fost inchisa");
    }
}
