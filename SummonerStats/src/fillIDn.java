import java.io.*;
import java.net.*;
import java.util.*;

public class fillIDn {
    public static void main(String[] args) throws IOException{
        File folder = new File("C:\\Users\\ngocn\\Downloads\\Eclipse stuff\\Homework\\SummonerStats\\Champion loading screen");
        File[] listOfFiles = folder.listFiles();
        File folder1 = new File("C:\\Users\\ngocn\\Downloads\\Eclipse stuff\\Homework\\SummonerStats\\Champion splash with ID");
        File[] listOfFiles1 = folder1.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String iName = listOfFiles[i].getName();
            iName = iName.substring(0, iName.indexOf("."));
            String rName = iName.replaceAll(" basic", "");
//            System.out.println(iName);
            for (int j = 0; j < listOfFiles1.length; j++) {
                if (listOfFiles1[j].getName().contains(rName)) {
//                    System.out.println(listOfFiles1[j].getName());
                    String num = listOfFiles1[j].getName().replaceAll("[^\\d]", "");
//                    System.out.println(num);
                    File rename = new File(iName + " " + num + ".jpg");
                    boolean success = listOfFiles[i].renameTo(rename);
                    if (!success) {
                        System.out.println(iName);
                    }
                    break;
                }
            }
        }
    }
}
