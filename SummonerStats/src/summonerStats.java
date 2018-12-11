import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.json.*;

public class summonerStats {
    public static void main (String[] args) throws IOException {
        Map<String, int[]> cWR = new HashMap<>();
        int[] pWR = new int[3];
        System.setProperty("http.agent", "Chrome");
        String apiKey = "?api_key=RGAPI-368bfdff-b20d-4e15-bcc0-98317d1b3450";
        String prefix = "https://na1.api.riotgames.com";
        String sName = "shiphtur";
        sName = sName.toLowerCase();
        sName = sName.replaceAll("[^a-zA-Z]", "");
        System.out.println(sName);
        String sPath = "/lol/summoner/v3/summoners/by-name/";
        String rSummoner = prefix + sPath + sName +apiKey;
        String current = getData(rSummoner);
        StringBuilder temp = new StringBuilder();
        String search = "accountId";
        int count = current.indexOf(search) + search.length() + 2;
        while (current.charAt(count) != ',') {
            temp.append(current.charAt(count));
            count++;
        }
//        System.out.println(temp.toString());
        String accountId = temp.toString();
        String mlPath = "/lol/match/v3/matchlists/by-account/";
        String rMList = prefix + mlPath + accountId + apiKey;
        String response = getData(rMList);
        String[] matches = response.split("gameId");
        int numMatch = Math.min(10, matches.length);
        String mPath = "/lol/match/v3/matches/";
        for (int i = 1; i < numMatch; i++) {
            try{
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            current = matches[i];
            int pos = 0;
//            System.out.println(current);
            count = 2;
            temp = new StringBuilder();
            while (current.charAt(count) != ',') {
                temp.append(current.charAt(count));
                count++;
            }
            String match = prefix + mPath + temp.toString() + apiKey;
            response = getData(match);
//            System.out.println(response);
            String[] parsed1 = response.split("\\{");
            String playerID = "";
            String champion = "";
            for (int j = 0; j < parsed1.length; j++) {
                if (parsed1[j].contains(accountId)) {
                    search = "participantId";
                    playerID = parsed1[j-1].substring(parsed1[j-1].indexOf(search), parsed1[j-1].indexOf(search) + search.length() + 5);
//                    System.out.println(playerID);
                    break;
                }
            }
            for (int j = 0; j < parsed1.length; j++) {
                if (parsed1[j].contains(playerID)) {
                    search = "championId";
                    System.out.println(parsed1[j]);
//                    System.out.println(parsed1[j].indexOf(search));
                    int co = parsed1[j].indexOf(search) + search.length() + 2;
                    StringBuilder sb = new StringBuilder();
                    while (parsed1[j].charAt(co) != ',') {
                        sb.append(parsed1[j].charAt(co));
//                        System.out.println(co + " " + parsed1[j].charAt(co));
                        co++;
                    }
                    champion = sb.toString();
//                    System.out.println(champion);
                    pos = j;
                    break;
                }
            }
            cWR.putIfAbsent(champion, new int[3]);
            boolean win = true;
            search = "win";
//            System.out.println(parsed1[pos+1].indexOf(search)+search.length()+3);
//            System.out.println(parsed1[pos+1].charAt(parsed1[pos+1].indexOf(search)+search.length()+3));
            if (parsed1[pos+1].charAt(parsed1[pos+1].indexOf(search)+search.length()+2) == 'f') {
                win = false;
                int[] wTemp = cWR.get(champion);
                wTemp[1]++;
                wTemp[2]++;
                cWR.put(champion, wTemp);
                pWR[1]++;
                pWR[2]++;
            } else {
                int[] wTemp = cWR.get(champion);
                wTemp[0]++;
                wTemp[2]++;
                cWR.put(champion, wTemp);
                pWR[0]++;
                pWR[2]++;
            }
//            System.out.println(parsed1[pos+1]);
//            System.out.println(win);
        }
        Set<Integer> games = new TreeSet<>(Collections.reverseOrder() );
        System.out.println("Win rate: " + pWR[0] + " " + pWR[1] + " " + pWR[2] + " " + (double) pWR[0]*100/pWR[2]);
        String mostC = "";
        String highC = "";
        String lowC = "";
        double highR = 0;
        double lowR = 2;
        double mostR = 0;
        int mostP = 0;
        for (String sTemp: cWR.keySet()) {
            if (cWR.get(sTemp)[2] > mostP) {
                mostP = cWR.get(sTemp)[2];
                mostC = sTemp;
            }
            if ((double) cWR.get(sTemp)[0]*100/cWR.get(sTemp)[2] > highR) {
                highR = (double) cWR.get(sTemp)[0]*100/cWR.get(sTemp)[2];
                highC = sTemp;
            }
            if ((double) cWR.get(sTemp)[0]*100/cWR.get(sTemp)[2] < lowR) {
                lowR = (double) cWR.get(sTemp)[0]*100/cWR.get(sTemp)[2];
                lowC = sTemp;
            }
        }
        for (String sTemp: cWR.keySet()) {
            games.add(cWR.get(sTemp)[2]);
        }
        String[] most3 = new String[3];
        count = 0;
        for (int i: games) {
            if (count == 3) break;
            for (String sTemp: cWR.keySet()) {
                if (count == 3) break;
                if (cWR.get(sTemp)[2] == i) {
                    most3[count] = sTemp;
                    count++;
                }
            }
        }
        System.out.println("Most played champions:");
        for (int i = 0; i < 3; i++) {
            mostC = most3[i];
            mostR = (double) cWR.get(mostC)[0]*100/cWR.get(mostC)[2];
            System.out.println(mostC + " " + cWR.get(mostC)[0] + " " + cWR.get(mostC)[1] + " " + cWR.get(mostC)[2] + " " + mostR);
        }
//                mostC + " " + cWR.get(mostC)[0] + " " + cWR.get(mostC)[1] + " " + cWR.get(mostC)[2] + " " + mostR);
        System.out.println("Highest win rate " + highC + " " + cWR.get(highC)[0] + " " + cWR.get(highC)[1] + " " + cWR.get(highC)[2] + " " + highR);
        System.out.println("Lowest win rate " + lowC + " " + cWR.get(lowC)[0] + " " + cWR.get(lowC)[1] + " " + cWR.get(lowC)[2] + " " + lowR);
        int[][] result = new int[5][4];
        for (int i = 0; i < 3; i++) {
            mostC = most3[i];
            result[i][0] = Integer.parseInt(mostC);
            result[i][1] = cWR.get(mostC)[0];
            result[i][2] = cWR.get(mostC)[1];
            result[i][3] = cWR.get(mostC)[2];
        }
        result[3][0] = Integer.parseInt(highC);
        result[3][1] = cWR.get(highC)[0];
        result[3][2] = cWR.get(highC)[1];
        result[3][3] = cWR.get(highC)[2];
        result[4][0] = Integer.parseInt(lowC);
        result[4][1] = cWR.get(lowC)[0];
        result[4][2] = cWR.get(lowC)[1];
        result[4][3] = cWR.get(lowC)[2];
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i][0] + " ");
            String temp1 = getCharName(result[i][0]);
            System.out.print(temp1 + " ");
            System.out.println(convertCharName(temp1));
            for (int j = 0; j < result[i].length; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static String getData(String s) throws IOException{
        URL url = new URL(s);
        String inputLine;
        URLConnection uConn = url.openConnection();
        BufferedReader input = new BufferedReader(new InputStreamReader(uConn.getInputStream()));
        StringBuffer response = new StringBuffer();
        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }
        input.close();
        return response.toString();
    }

    private static String getCharName(int id) {
        File folder = new File("C:\\Users\\ngocn\\Downloads\\Eclipse stuff\\Homework\\SummonerStats\\Champion splash with ID");
        File[] listOfFiles = folder.listFiles();
        String target = Integer.toString(id);
        String realName = "";
        for (int i = 0; i < listOfFiles.length; i++) {
            String iName = listOfFiles[i].getName();
            iName = iName.substring(0, iName.indexOf("."));
            String rName = iName.replaceAll("[^0-9]", "");
            if (rName.equals(target)) {
                String[] parse = iName.split(" ");
                return parse[0];
            }
//            System.out.println(iName);

        }
        return realName;
    }

    private static int convertCharName(String name) {
        File folder = new File("C:\\Users\\ngocn\\Downloads\\Eclipse stuff\\Homework\\SummonerStats\\Champion splash with ID");
        File[] listOfFiles = folder.listFiles();
        int id = 0;
        String realName = "";
        for (int i = 0; i < listOfFiles.length; i++) {
            String iName = listOfFiles[i].getName();
            iName = iName.substring(0, iName.indexOf("."));
            String[] parse = iName.split(" ");
            if (name.equals(parse[0])) {
                String rName = iName.replaceAll("[^0-9]", "");
                return Integer.parseInt(rName);
            }
//            System.out.println(iName);

        }
        return id;
    }
}
