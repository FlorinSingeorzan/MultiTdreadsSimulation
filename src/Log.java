import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    Logger logger;
    private FileHandler fileHandler;

    public Log(String fileName) throws SecurityException,IOException{
        File file=new File(fileName);       //creaza un fisier de log pentru a putea scrie in el
        if(!file.exists()){
            file.createNewFile();
        }
        fileHandler=new FileHandler(fileName,true);
        logger=Logger.getLogger("tema1");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter=new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }
}
