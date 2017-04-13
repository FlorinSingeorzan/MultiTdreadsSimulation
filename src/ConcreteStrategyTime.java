import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public void addClient(List<HomeStore> l, Client c) {
        int waitingTime=Integer.MAX_VALUE;
        int index=0;
        for(int i=0;i<l.size();i++){
                if(l.get(i).isOpen() && l.get(i).getWaitingTime()<waitingTime){            //pentru fiecare clasa se calculeaza suma timpilor de servire pentru clientii aflati deja la coada
                    waitingTime=l.get(i).getWaitingTime();
                    index=i;            //se retine coada
            }
        }
        l.get(index).setWitch(index);       //se seteaza numarul casei
        l.get(index).addClient(c);      //se adauga in coada
    }

}

