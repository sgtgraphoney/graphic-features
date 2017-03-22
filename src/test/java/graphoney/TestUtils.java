package graphoney;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestUtils {

    public static String readLastLineFromFile(String path) {
        String lastLine = null;

        try {
            FileInputStream inputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lastLine = line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lastLine;
    }

    public static String readTextFromFile(String path) {
        StringBuilder text = new StringBuilder();

        try {
            FileInputStream inputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }

}
