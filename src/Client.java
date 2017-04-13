public class Client implements Comparable {
    private int idClient;           //clientii se diferentiaza prin id
    private int arrivalTime;        //timpul de sosire al fiecarui client
    private int serviceTime;        //timul petrecut la coada
    private int finishTime;         //timul parasirii cozii
    private boolean served = false;   //clienttul nu e servit la initializare
    private int waitingTime;        //timpul petrecut in coada

    public Client(int arrivalTime, int serviceTime) {         //constructor pt initializare variabile
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        finishTime = -1;
        served = false;
        waitingTime = 0;
    }

    public Client() {
        this(0, 0);                  //constuctor
    }

    //gettere si settere pentu obtinerea datelor private
    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isServed() {
        return served;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    //metoda de comparare a clientilor in functie de timul de sosire
    @Override
    public int compareTo(Object o) {
        return (int) (this.arrivalTime - ((Client) o).arrivalTime);
    }
}
