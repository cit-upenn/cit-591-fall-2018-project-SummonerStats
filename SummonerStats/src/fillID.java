import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.json.*;

public class fillID {
    public static void main (String[] args) throws IOException{
        File folder = new File("C:\\Users\\ngocn\\Downloads\\Eclipse stuff\\Homework\\SummonerStats");
        File[] listOfFiles = folder.listFiles();
        File fi = new File("champion.json");
        Scanner sc = new Scanner(fi);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }
        String content = sb.toString();
        for (int i = 0; i < listOfFiles.length; i++) {
            String fName = listOfFiles[i].getName();
//            System.out.println(fName);
            if (fName.contains("jpg")) {
                String name = fName.replace(".jpg", "");
                int pre = content.indexOf(name);
                String search = "key";
                int pos = content.indexOf(search, pre);
                if (pos > 0) {
                    int count = pos + search.length() + 3;
                    sb = new StringBuilder();
                    while (content.charAt(count) != '"') {
                        sb.append(content.charAt(count));
                        count++;
                    }
                    File rename = new File(name + " " + sb.toString() + " splash.jpg");
                    boolean success = listOfFiles[i].renameTo(rename);
                    if (!success) {
                        System.out.println("Not changed" + name);
                    }
                } else {
                    System.out.println(name);
                }
            }
        }
    }
}
