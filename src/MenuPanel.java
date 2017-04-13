import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {         //clasa panel pentru datele de intrare de la fereastra
    private JButton startSim;                       //containare graafice
    private JComboBox nrQueue;
    private JTextArea simTime;
    private JTextArea minSevTime;
    private JTextArea maxSevTime;
    private JTextArea minBtnCl;
    private JTextArea maxBtnCl;
    private JLabel[] labels;

    public MenuPanel() {
        init();                 //initializare
        this.setBounds(0, 0, 175, 600);        //setare dimensiuni
        this.setBackground(Color.WHITE);
        addContainers();        //adaugare componente
        action();           //adaugare atributii
    }

    private void init() {            //setare containere la atributele dorite
        startSim = new JButton("Start");
        nrQueue = new JComboBox();
        nrQueue.addItem("1");
        nrQueue.addItem("2");
        nrQueue.addItem("3");
        nrQueue.addItem("4");
        nrQueue.addItem("5");
        maxSevTime = new JTextArea();
        maxSevTime.setBackground(Color.WHITE.darker());
        minSevTime = new JTextArea();
        minSevTime.setBackground(Color.WHITE.darker());
        maxBtnCl = new JTextArea();
        maxBtnCl.setBackground(Color.WHITE.darker());
        minBtnCl = new JTextArea();
        minBtnCl.setBackground(Color.WHITE.darker());
        simTime = new JTextArea();
        simTime.setBackground(Color.WHITE.darker());
        labels = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            labels[i] = new JLabel("");
        }
        labels[0].setText("No. of Qoques");
        labels[1].setText("Max Service Time");
        labels[2].setText("Min Service Time");
        labels[3].setText("Max Time Between Clients");
        labels[4].setText("Min Time Between Clients");
        labels[5].setText("Simulation Interval");
        place();
    }

    private void place() {               //plasare containare pe fereastra grafica
        startSim.setBounds(30, 80, 80, 25);
        nrQueue.setBounds(30, 130, 50, 20);
        maxSevTime.setBounds(30, 260, 60, 20);
        minSevTime.setBounds(30, 320, 60, 20);
        maxBtnCl.setBounds(30, 380, 60, 20);
        minBtnCl.setBounds(30, 440, 60, 20);
        simTime.setBounds(30, 500, 60, 20);
        labels[0].setBounds(15, 110, 120, 20);
        labels[1].setBounds(15, 240, 120, 20);
        labels[2].setBounds(15, 300, 120, 20);
        labels[3].setBounds(15, 360, 180, 20);
        labels[4].setBounds(15, 420, 180, 20);
        labels[5].setBounds(15, 480, 180, 20);
    }

    private void addContainers() {       //adaugarea efectiva
        this.setLayout(null);
        this.add(startSim);
        this.add(nrQueue);
        this.add(maxBtnCl);
        this.add(maxSevTime);
        this.add(minBtnCl);
        this.add(minSevTime);
        this.add(simTime);
        for (int i = 0; i < 6; i++) {
            this.add(labels[i]);
        }

    }

    private void action() {                  //metoda pentru setarea atributilor butonului de start si combo box-ului
        startSim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SimulationManager.frame.setRunning(true);           //pornire timer
                    SimulationManager.frame.setMaxInterval(Integer.parseInt(maxBtnCl.getText()));           //preluare date
                    SimulationManager.frame.setMinInterval(Integer.parseInt(minBtnCl.getText()));
                    SimulationManager.frame.setMaxServiceTime(Integer.parseInt(maxSevTime.getText()));
                    SimulationManager.frame.setMinServiceTime(Integer.parseInt(minSevTime.getText()));
                    SimulationManager.frame.setSimInterval(Integer.parseInt(simTime.getText()));
                    maxBtnCl.setEditable(false);        //setare field-urilor la needitabile
                    minBtnCl.setEditable(false);
                    maxSevTime.setEditable(false);
                    minSevTime.setEditable(false);
                    simTime.setEditable(false);
                    nrQueue.setEnabled(false);
                    nrQueue.setEditable(false);
                    SimulationManager.frame.setInvalidData(false);
                } catch (Exception er) {
                    SimulationManager.frame.setInvalidData(true);           //daca apare o eroare se semnaleaza
                }

            }
        });

        nrQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String no = (String) nrQueue.getSelectedItem();
                SimulationManager.frame.setNrQueues(Integer.parseInt(no));          //seteaza numarul de cozi
            }
        });
    }
}
