import java.util.Timer;
import java.util.TimerTask;

public class TimerSec {

    private int secondsPassed=0;        //timp initial 0
    private Timer time=new Timer();
    private TimerTask task=new TimerTask() {
           @Override
           public void run() {                          //metoda apelata de start()
               secondsPassed++;                         //secondsPassed se incrementeaza la apelare
           }
       };
    public int getSecondsPassed(){
        return secondsPassed;
    }

    public void start(){
    time.scheduleAtFixedRate(task,1000,1000);          //secondsPassed se va incrementa din secunda in secunda
    }
}
