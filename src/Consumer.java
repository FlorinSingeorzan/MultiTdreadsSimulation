import java.awt.*;

public class Consumer {             //clasa folosite pentru partea grafica
    private Client client;
    private Rectangle shape;        //clienti sunt reprezentati printr-un patrat
    private int xPosition;          //pozita in fereastra grafica a fiecarui client
    private int yPosition;
    private int witchHome;          //casa la care este clientul
    private Boolean mustPaint = false;        //variav=bila booleana care specifica daca clientul trebuie desenat sau nu

    public Consumer() {                  //constructor pentru initializare
        shape = new Rectangle();
        client = new Client();
        mustPaint = false;
    }


    //metode pentru obtinerea datelor private
    public Rectangle getShape() {
        return shape;
    }

    public Boolean getMustPaint() {
        return mustPaint;
    }

    public void setMustPaint(Boolean mustPaint) {
        this.mustPaint = mustPaint;
    }

    public void setShape(int xPos, int yPos, int width, int height) {
        shape.setBounds(xPos, yPos, width, height);
    }

    public int getWitchHome() {
        return witchHome;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setWitchHome(int witchHome) {
        this.witchHome = witchHome;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
