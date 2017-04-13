import javax.swing.*;
import java.awt.*;


public class Renderer extends JPanel {
    protected void paintComponent(Graphics g){
        this.setLayout(null);
        this.setBounds(180,0,715,600);      //setaere dimensiuni
        super.paintComponent(g);
        SimulationManager.frame.repaint(g);         //redesenare panel grafic
    }


}
