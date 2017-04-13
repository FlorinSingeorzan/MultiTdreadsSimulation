import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addClient(List<HomeStore> l, Client c) {
        int minInLine=100;
        int index=0;
                for(int k=0;k<l.size();k++){
                if(l.get(k).isOpen() && l.get(k).coada.size()<minInLine ){          //pentru fiecare coada se numare clienti
                    minInLine=l.get(k).coada.size();
                    index=k;            //se pastreaza coada cu numarul cel mai mic de clienti
                }
            }
            l.get(index).setWitch(index);       //se seeteaza numarul casei
            l.get(index).addClient(c);          //se adauga un client in coada casei cu cel mai mic numar de clienti

    }
}
