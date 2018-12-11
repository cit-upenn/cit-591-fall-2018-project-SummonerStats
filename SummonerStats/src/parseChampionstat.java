import java.io.*;
import java.util.*;

public class parseChampionstat {
    public static void main (String[] args) {
        try {
            File fi = new File("Detail stat.txt");
            Scanner sc = new Scanner(fi);
            while (sc.hasNextLine()) {
                String s= sc.nextLine().trim();
                String s1 = "";
                PrintWriter pwriter = new PrintWriter(new File(s + " basic.txt"));
                s1 = sc.nextLine().trim() + " ";
                s = s1 + sc.nextLine().trim();
                System.out.println(s);
                pwriter.println("HP  " + s);
                s1 = sc.nextLine().trim() + " ";
                s = s1 + sc.nextLine().trim();
                System.out.println(s);
                pwriter.println("HP5 " + s);
                s1 = sc.nextLine().trim() + " ";
                s = s1 + sc.nextLine().trim();
                System.out.println(s);
                pwriter.println("MP  " + s);
                s1 = sc.nextLine().trim() + " ";
                s = s1 + sc.nextLine().trim();
                System.out.println(s);
                pwriter.println("MP5 " + s);
                s1 = sc.nextLine().trim() + " ";
                s = s1 + sc.nextLine().trim();
                System.out.println(s);
                pwriter.println("AD  " + s);
                s1 = sc.nextLine().trim() + " ";
                s = s1 + sc.nextLine().trim();
                System.out.println(s);
                pwriter.println("AS  " + s);
                s1 = sc.nextLine().trim() + " ";
                s = s1 + sc.nextLine().trim();
                System.out.println(s);
                pwriter.println("AR  " + s);
                s1 = sc.nextLine().trim() + " ";
                s = s1 + sc.nextLine().trim();
                System.out.println(s);
                pwriter.println("MR  " + s);
                s = sc.nextLine();
                pwriter.println("MS  " + s);
                s = sc.nextLine();
                pwriter.println("Range " + s);
                pwriter.close();
            }
//            String content = new Scanner(new File("Basic stat data.txt")).useDelimiter("\\Z").next().replace("\"name\"", "!!!!!");
//            String[] parsed = content.split("!!!!!");
//            for (int count = 1; count < parsed.length; count++) {
//                String s = parsed[count];
//                StringBuilder name = new StringBuilder();
//                int co = 2;
//                while (s.charAt(co) != '"') {
//                    name.append(s.charAt(co));
//                    co++;
//                }
//                PrintWriter pwriter = new PrintWriter(new File(name.toString() + ".txt"));
//                String[] parsed1 = s.split("info");
//                System.out.println(s);
////                System.out.println(parsed1[1]);
//                String basic = parsed1[1].replaceAll("[^0-9,]","");
////                System.out.println(basic);
//                basic = basic.replace(","," ");
//                System.out.println(basic);
//                System.out.println(basic.substring(0, 9).trim());
//                pwriter.println(basic.substring(0, 9).trim());
//                pwriter.close();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
