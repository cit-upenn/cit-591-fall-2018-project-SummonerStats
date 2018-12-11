import java.io.*;
import java.net.*;
import java.util.*;

public class checkID {
    public static void main(String[] args) {
        File folder = new File("C:\\Users\\ngocn\\Downloads\\Eclipse stuff\\Homework\\SummonerStats\\Champion splash with ID");
        File[] listOfFiles = folder.listFiles();
        Map<Integer, Integer> Idcheck = new HashMap<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            String name = listOfFiles[i].getName().replaceAll("[^\\d]", "");
            String s = listOfFiles[i].getName().replaceAll("[^\\d]", "");
            if (s.equals("")) {
                System.out.println(name);
            }
            int num = Integer.parseInt(s);
            Idcheck.putIfAbsent(num, 0);
            Idcheck.put(num, Idcheck.get(num)+1);
        }
        for (int i: Idcheck.keySet()) {
            if (Idcheck.get(i) != 1) {
                System.out.println(i);
            }
        }
    }
}
