package PersonList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
public class SaverTxt {

    public void saveText(String text) {
        File file = new File("text" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss"))
                + ".txt");
        if (!file.exists()){
            try {
                file.createNewFile();
                try (FileWriter fileWriter = new FileWriter(file)){
                    fileWriter.append(text);
                }
            } catch (IOException ex) {
                Logger.getLogger(SaverTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
