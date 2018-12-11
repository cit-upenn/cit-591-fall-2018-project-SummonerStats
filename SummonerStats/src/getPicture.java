import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.json.*;

public class getPicture {
    public static void main(String[] args) throws IOException{
        System.out.println("opening connection");
        File folder = new File("C:\\Users\\ngocn\\Downloads\\Eclipse stuff\\Homework\\SummonerStats\\Champion splash");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && !listOfFiles[i].getName().contains("basic")) {
                String name = listOfFiles[i].getName().replace(".jpg", "");
                try{
                    URL url = new URL("http://ddragon.leagueoflegends.com/cdn/img/champion/loading/" + name + "_0.jpg");
                    InputStream in = url.openStream();
                    FileOutputStream fos = new FileOutputStream(new File(name + ".jpg"));

                    System.out.println("reading from resource and writing to file...");
                    int length = -1;
                    byte[] buffer = new byte[1024];// buffer for portion of data from connection
                    while ((length = in.read(buffer)) > -1) {
                        fos.write(buffer, 0, length);
                    }
                    fos.close();
                    in.close();

                } catch (IOException e) {
                    System.out.println(e);
                    System.out.println(name);
                }
            }
        }
        System.out.println("File downloaded");
    }
}
