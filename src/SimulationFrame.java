import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimulationFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JFrame Fereastra;
    private Renderer renderer;              //panou aplicatie
    private MenuPanel btnPanel;         //panou date de intrare
    private final int WIDTH = 900, HEIGHT = 600;        //dimensiuni
    private ArrayList<Consumer> consumers;           //lista clientilor de desenat
    private TimerSec running;           //timer pentru sincronizare
    private Boolean isRunning;          //starea ferestrei
    private int minInterval;            //input-urile de functionare
    private int maxInterval;
    private int minServiceTime;
    private int maxServiceTime;
    private int nrQueues;               //numarul de cozi
    private int simInterval;            //perioada de simulare
    private boolean invalidData = false;        //starea datelor de intrare
    private Timer timer;

    public SimulationFrame() {          //initializare date
        consumers = new ArrayList<Consumer>();
        Fereastra = new JFrame();
        btnPanel = new MenuPanel();
        renderer = new Renderer();
        running = new TimerSec();
        timer = new Timer(500, this);         //timper care apeleaza metoda actionPerformed la fiecare 500 ms
        Fereastra.add(btnPanel);        //adaugarea panourilor pe fereastra
        Fereastra.add(renderer);
        Fereastra.setTitle("Simulare Threads");
        Fereastra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Fereastra.setSize(WIDTH, HEIGHT);
        Fereastra.setResizable(false);
        Fereastra.setVisible(true);
        isRunning = false;
        timer.start();      //pornire timer
    }

    private void addForm() {                    //adaugare forma pentru fiecare client(Rectangle)
        for (Consumer i : consumers) {
            i.setShape(i.getxPosition(), i.getyPosition(), 30, 30);
        }
    }

    private void paintClients(Graphics g, Consumer c) {         //desenare efectiva
        g.setColor(Color.ORANGE);
        g.fillRect(c.getShape().x, c.getShape().y, c.getShape().width, c.getShape().height);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", 1, 12));
        g.drawString("" + c.getClient().getIdClient(), c.getShape().x + 10, c.getShape().y + 19);       //in fiecare patrat se va gasi un numar care reprezinta id-ul clientului in cauza
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning == true) {            //cat timp aplicatia trebuie sa ruleze se verifica fiecare daca e timpul ca clientul sa fie desenat si se transmite mai departe spre metoda repaint
            try {
                if (invalidData == false)
                    running.start();
            } catch (Exception er) {
            }
            for (Consumer c : consumers) {
                if (running.getSecondsPassed() == c.getClient().getArrivalTime()) {
                    c.setMustPaint(true);
                }
            }
            for (int c = 0; c < consumers.size(); c++) {
                if (running.getSecondsPassed() > consumers.get(c).getClient().getArrivalTime() + consumers.get(c).getClient().getServiceTime() + consumers.get(c).getClient().getWaitingTime()) {
                    consumers.remove(c);
                }
            }
            detX();         //se determina coordonatele si se seteaza forma "shape" la pozitile calculate
            detY();
            addForm();
        }
        if (isRunning == true)
            renderer.repaint();
    }

    public void repaint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);                //setare culoare
        paintHomeStores(g);                     //desenare case
        if (isRunning == true) {
            g.setColor(Color.black.darker());
            g.setFont(new Font("Arial", 1, 15));
            g.drawString("Seconds " + running.getSecondsPassed(), 10, 30);      //afisare timer
        }
        if (invalidData == true) {          //daca datele de intrare sunt invalide se deseneaza "Invalid data set" pe fereastra
            g.setColor(Color.red);
            g.setFont(new Font("Arial", 1, 17));
            g.drawString("Invalid data set", 500, 30);
        }
        for (Consumer c : consumers) {
            if (c.getMustPaint() == true)           //atata timp cat sun clienti de desenat
                paintClients(g, c);                 //deseneaza
        }
    }

    private void paintHomeStores(Graphics g) {              //desenare case
        g.setColor(Color.BLUE);
        switch (nrQueues) {             //se seteaza pozitia si numarul casei in functie de input
            case 1:
                g.fillRect(34, 530, 70, 30);
                break;
            case 2:
                g.fillRect(34, 530, 70, 30);
                g.fillRect(176, 530, 70, 30);
                break;
            case 3:
                g.fillRect(34, 530, 70, 30);
                g.fillRect(176, 530, 70, 30);
                g.fillRect(318, 530, 70, 30);
                break;
            case 4:
                g.fillRect(34, 530, 70, 30);
                g.fillRect(176, 530, 70, 30);
                g.fillRect(318, 530, 70, 30);
                g.fillRect(460, 530, 70, 30);
                break;
            case 5:
                g.fillRect(34, 530, 70, 30);
                g.fillRect(176, 530, 70, 30);
                g.fillRect(318, 530, 70, 30);
                g.fillRect(460, 530, 70, 30);
                g.fillRect(604, 530, 70, 30);
            default:
        }
    }

    private void detX() {                   //setarea pozitie pe X in functie de casa
        for (Consumer i : consumers) {
            switch (i.getWitchHome()) {
                case 0:
                    i.setxPosition(53);
                    break;
                case 1:
                    i.setxPosition(195);
                    break;
                case 2:
                    i.setxPosition(337);
                    break;
                case 3:
                    i.setxPosition(479);
                    break;
                case 4:
                    i.setxPosition(623);
                    break;
                default:
            }
        }
    }

    private void detY() {           //calcularea cooronatei Y pe fereastra
        int yPos = 520;
        int witch = 0;
        for (Consumer i : consumers) {
            if (i.getWitchHome() != witch) {
                yPos = 520;
                witch = i.getWitchHome();
            }
            yPos -= 50;
            if (yPos < 50) {
                yPos = -10000;                        //pictare in afara ferestrei in caz de suparpunere
            }
            i.setyPosition(yPos);
        }
    }

    //metode pentru obtinerea datelor ferestrei grafice
    public TimerSec getRunning() {
        return running;
    }

    public int getMaxInterval() {
        return maxInterval;
    }

    public void setRunning(Boolean isRunning) {
        this.isRunning = isRunning;
    }

    public int getMaxServiceTime() {
        return maxServiceTime;
    }

    public int getMinInterval() {
        return minInterval;
    }

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    public int getNrQueues() {
        return nrQueues;
    }

    public int getSimInterval() {
        return simInterval;
    }

    public int getMinServiceTime() {
        return minServiceTime;
    }

    public void setInvalidData(boolean invalidData) {
        this.invalidData = invalidData;
    }

    public void setNrQueues(int nrQueues) {
        this.nrQueues = nrQueues;
    }

    public void setMaxInterval(int maxInterval) {
        this.maxInterval = maxInterval;
    }

    public void setMinInterval(int minInterval) {
        this.minInterval = minInterval;
    }

    public void setMaxServiceTime(int maxServiceTime) {
        this.maxServiceTime = maxServiceTime;
    }

    public void setMinServiceTime(int minServiceTime) {
        this.minServiceTime = minServiceTime;
    }

    public void setSimInterval(int simInterval) {
        this.simInterval = simInterval;
    }

    public Boolean getIfRunning() {
        return isRunning;
    }

    public boolean isInvalidData() {
        return invalidData;
    }
}


