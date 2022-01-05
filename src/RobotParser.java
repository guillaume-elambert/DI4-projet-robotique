import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class RobotParser {

    public static Robot parseRobot(String pathToJSONFile){
        FileReader fr;
        try {
            fr = new FileReader(pathToJSONFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return new Gson().fromJson(fr, Robot.class);
    }
}
