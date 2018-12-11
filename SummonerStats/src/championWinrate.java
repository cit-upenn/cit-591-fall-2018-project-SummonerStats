import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


public class championWinrate{
    public static void main(String[] args) {

        URL url;
        Map<String, double[] > champInfo = new HashMap<>();

        try {
            // get URL content
            System.setProperty("http.agent", "Chrome");
            String a="https://champion.gg/statistics/";
            url = new URL(a);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                if (inputLine.contains("matchupData.stats")) {
                    sb.append(inputLine);
                    while(!(inputLine = br.readLine()).contains("  var champData")) {
                        sb.append(inputLine);
                    }
                    break;
                }
            }
            br.close();

            String[] parsed = sb.toString().split("title");
            for (int i = 1; i < parsed.length; i++) {
                double[] stats = new double[9];
                sb = new StringBuilder();
                String current = parsed[i];
                int count = 3;
                while (current.charAt(count) != '"') {
                    sb.append(current.charAt(count));
                    count++;
                }
                String name = sb.toString();
//                System.out.println(name);
                sb = new StringBuilder();
                String temp = "winPercent";
                count = current.indexOf(temp) + temp.length() + 2;
                while (current.charAt(count) != ',') {
                    sb.append(current.charAt(count));
                    count++;
                }
                stats[0] = Double.parseDouble(sb.toString()) * 100;
//                System.out.println(stats[0]);
                sb = new StringBuilder();
                temp = "playPercent";
                count = current.indexOf(temp) + temp.length() + 2;
                while (current.charAt(count) != ',') {
                    sb.append(current.charAt(count));
                    count++;
                }
                stats[1] = Double.parseDouble(sb.toString()) * 100;
//                System.out.println(stats[1]);
                sb = new StringBuilder();
                temp = "banRate";
                count = current.indexOf(temp) + temp.length() + 2;
                while (current.charAt(count) != ',') {
                    sb.append(current.charAt(count));
                    count++;
                }
                stats[2] = Double.parseDouble(sb.toString()) * 100;
//                System.out.println(stats[2]);
                sb = new StringBuilder();
                temp = "kills";
                count = current.indexOf(temp) + temp.length() + 2;
                while (current.charAt(count) != ',') {
                    sb.append(current.charAt(count));
                    count++;
                }
                stats[3] = Double.parseDouble(sb.toString());
//                System.out.println(stats[3]);
                sb = new StringBuilder();
                temp = "deaths";
                count = current.indexOf(temp) + temp.length() + 2;
                while (current.charAt(count) != ',') {
                    sb.append(current.charAt(count));
                    count++;
                }
                stats[4] = Double.parseDouble(sb.toString());
//                System.out.println(stats[4]);
                sb = new StringBuilder();
                temp = "assists";
                count = current.indexOf(temp) + temp.length() + 2;
                while (current.charAt(count) != ',') {
                    sb.append(current.charAt(count));
                    count++;
                }
                stats[5] = Double.parseDouble(sb.toString());
//                System.out.println(stats[5]);
                sb = new StringBuilder();
                temp = "totalDamageDealtToChampions";
                count = current.indexOf(temp) + temp.length() + 2;
                while (current.charAt(count) != ',') {
                    sb.append(current.charAt(count));
                    count++;
                }
                stats[6] = Double.parseDouble(sb.toString());
//                System.out.println(stats[6]);
                sb = new StringBuilder();
                temp = "totalDamageTaken";
                count = current.indexOf(temp) + temp.length() + 2;
                while (current.charAt(count) != ',') {
                    sb.append(current.charAt(count));
                    count++;
                }
                stats[7] = Double.parseDouble(sb.toString());
//                System.out.println(stats[7]);
                sb = new StringBuilder();
                temp = "totalHeal";
                count = current.indexOf(temp) + temp.length() + 2;
                while (current.charAt(count) != ',') {
                    sb.append(current.charAt(count));
                    count++;
                }
                stats[8] = Double.parseDouble(sb.toString());
//                System.out.println(stats[8]);
                champInfo.putIfAbsent(name, stats);
                if (champInfo.get(name)[0] < stats[0]) {
                    champInfo.put(name, stats);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TreeMap<String, Double> result = new TreeMap<>();
        TreeSet<Double> winRates = new TreeSet<>(Collections.reverseOrder());
        for (String temp: champInfo.keySet()) {
            winRates.add(champInfo.get(temp)[0]);
        }
        for (int i = 0; i < 10; i++) {
            double tWR = 0;
            int tCount = 0;
            for (double ttemp: winRates) {
                if (tCount == i) {
                    tWR = ttemp;
                    break;
                }
                tCount++;
            }
            for (String temp: champInfo.keySet()) {
                if (Math.abs(tWR-champInfo.get(temp)[0]) < 0.00000001) {
                    result.put(temp, tWR);
                }
            }
        }
        for (String temp: result.keySet()) {
            System.out.println(temp + " " + result.get(temp));
        }
    }
}