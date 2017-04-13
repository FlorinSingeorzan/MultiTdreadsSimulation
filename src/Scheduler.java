import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<HomeStore> homeStores;         //multime de cozi
    private Strategy strategy;          //stratehie de adaugare
    private ArrayList<Thread> homeThread;       //multime de threduri

    public Scheduler(int noHome) {
        homeStores = new ArrayList<HomeStore>(5);         //setare numar case
        homeThread = new ArrayList<>(5);              //setare numarul de threaduri
        for (int i = 0; i < noHome; i++) {                              //pt fiecare casa
            HomeStore current = new HomeStore();     //se seteaza casa curenta
            homeStores.add(current);                            //se adauga casa in multimea de case(cozi)
            Thread currentThread = new Thread(current);
            homeThread.add(currentThread);                      //se put thredurile in multime
        }
    }

    public ArrayList<Thread> getHomeThread() {
        return homeThread;
    }

    public void changeStrategy(SelectionPolicy policy) {         //metoda de modificare a strategiei de adaugare
        if (policy == SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ConcreteStrategyQueue();
        if (policy == SelectionPolicy.SHORTEST_TIME)
            strategy = new ConcreteStrategyTime();

    }

    double findAvgWaitingTime() {
        double sum = 0;
        int noClients = 0;
        for (HomeStore i : homeStores) {
            for (Client j : i.coada) {
                sum += j.getWaitingTime();
                noClients++;

            }
        }
        double result = sum / noClients;
        return result;
    }

    public List<HomeStore> getHomeStores() {
        return homeStores;
    }                   //obtinere multime de case

    public void dispachClient(Client t) {
        strategy.addClient(homeStores, t);//
    }       //obtinere multime de threduri

}
