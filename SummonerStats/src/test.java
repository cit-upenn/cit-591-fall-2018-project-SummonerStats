import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class test extends JPanel {
//    static final Dimension bDimension = new Dimension(20,20);

    public test () {
        super(new GridLayout(1,1));

        JTabbedPane tabbedPane = new JTabbedPane();

        JTabbedPane tabbedPane2 = new JTabbedPane();

        JPanel panel1 = (JPanel) makePlayerPanel();
        tabbedPane.addTab("Player Statistics", panel1);
        panel1.setPreferredSize(new Dimension(1024, 768));

        JPanel panel2 = (JPanel) makeCharacterPanel();
        tabbedPane.addTab("Character Statistics", panel2);

        JPanel panel3 = (JPanel) makeSuggestionPanel();
        tabbedPane.addTab("Character Suggestions", panel3);

        add(tabbedPane);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    protected JComponent makeSuggestionPanel () {
        JPanel panel = new JPanel(false);
        panel.setLayout (null);
        JLabel txt = new JLabel("Quick beginner questionaires:");
        txt.setVisible(true);
        txt.setBounds(100, 10, 300, 50);
        panel.add(txt);
        ButtonGroup q1 = new ButtonGroup();
        JRadioButton[] q1c = new JRadioButton[5];
        for (int i = 0; i < 5; i++) {
            q1c[i] = new JRadioButton(Integer.toString(i));
            q1c[i].setVisible(true);
            q1c[i].setBounds(50+i*50, 50, 20, 20);
            panel.add(q1c[i]);
        }
        return panel;
    }
//    private ButtonGroup radioGroup= new ButtonGroup();
//    private JRadioButton radio1= new JRadioButton("Red");
//    private JRadioButton radio2= new JRadioButton("Green");
//    private JRadioButton radio3= new JRadioButton("Blue");
//
//   radioGroup.add(radio1);
//   radioGroup.add(radio2);
//   radioGroup.add(radio3);
    protected JComponent makeCharacterPanel () {
        JPanel panel = new JPanel(false);
        TreeMap<String, Double> getWR = getCharacterWinRate();
        JLabel[] filler = new JLabel[10];
        int count = 0;
        for (String temp: getWR.keySet()) {
            int length = 20;
            String dRates = String.format("%.2f", getWR.get(temp));
            length = length - temp.length();
            StringBuilder spaces = new StringBuilder();
            for (int i = 0; i < length*2; i++) {
                spaces.append(" ");
            }
            filler[count] = new JLabel(temp + spaces.toString() + dRates + "%");
            filler[count].setBounds(100, count*50+100, 200, 30);
            filler[count].setVisible(false);
            panel.add(filler[count]);
            count++;
        }
//        for (int i = 0; i < filler.length; i++) {
//            filler[i] = new JLabel("label" + i);
//            filler[i].setBounds(30, i*50+100, 50, 30);
//            filler[i].setVisible(false);
//            panel.add(filler[i]);
//        }
        final JLabel[] fLabel = filler;
//        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout (null);
        JButton button = new JButton("Generate highest winrates characters");
        button.setBounds(10, 10, 250, 30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < fLabel.length; i++) {
                    fLabel[i].setVisible(true);
                }
            }
        });
        panel.add(button);
//        panel.add(filler);
        return panel;
    }

    protected JComponent makePlayerPanel () {
        JPanel panel = new JPanel(false);
        panel.setLayout(null);
        JButton button = new JButton("Analyze");
        JTextField tf = new JTextField("Enter player name here");
        JTextField tfAPI = new JTextField("Enter API key here");
        button.setBounds(30,140,100,30);
        tf.setBounds(10, 100, 200, 30);
        tf.setVisible(true);
        tfAPI.setBounds(10, 10, 300, 30);
        panel.add(tf);
        panel.add(tfAPI);
        panel.add(button);
//        final int[][] finalStat = new int[5][4];
        final JLabel[] finalLabel = new JLabel[5];
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = tf.getText();
                String inAPI = tfAPI.getText();
                System.out.println(text);
                System.out.println(inAPI);
                try {
                    int[][] analysis = getAnalysis(text, inAPI);
//                    for (int i = 0; i < analysis.length; i++) {
//                        for (int j = 0; j < analysis[i].length; j++) {
//                            System.out.print(analysis[i][j] + " ");
//                        }
//                        System.out.println();
//                    }
                    for (int i = 0; i < 3; i++) {
                        String charName = getCharName(analysis[i][0]);
                        int length = 15;
                        length = length - charName.length();
                        StringBuilder spaces = new StringBuilder();
                        for (int j = 0; j < length * 2; j++) {
                            spaces.append(" ");
                        }
                        finalLabel[i] = new JLabel(charName + spaces.toString() + analysis[i][1] + "W  " + analysis[i][2] + "L  " + analysis[i][3] + "T  " + String.format("%.2f", (double) analysis[i][1] * 100 / analysis[i][3]) + "% WR");
                    }
                    System.out.println("Final text" + finalLabel[0].getText());
                    JLabel txt1 = new JLabel("Most played characters: ");
                    for (int i = 0; i < 3; i++) {
                        finalLabel[i].setBounds(50, 230+i*35, 250, 30);
                        panel.add(finalLabel[i]);
                    }
                    String charName = getCharName(analysis[3][0]);
                    int length = 15;
                    length = length - charName.length();
                    StringBuilder spaces = new StringBuilder();
                    for (int i = 0; i < length*2; i++) {
                        spaces.append(" ");
                    }
                    finalLabel[3] = new JLabel(charName + spaces.toString() + analysis[3][1] + "W  " + analysis[3][2] + "L  " + analysis[3][3] + "T  " + (double) analysis[3][1] * 100 / analysis[3][3] + "% WR");
                    charName = getCharName(analysis[4][0]);
                    length = 15;
                    length = length - charName.length();
                    spaces = new StringBuilder();
                    for (int i = 0; i < length*2; i++) {
                        spaces.append(" ");
                    }
                    finalLabel[4] = new JLabel(charName + spaces.toString() + analysis[4][1] + "W  " + analysis[4][2] + "L  " + analysis[4][3] + "T  " + (double) analysis[4][1] * 100 / analysis[4][3] + "% WR");
                    txt1.setBounds(30, 200, 150, 30);
                    txt1.setVisible(true);
                    panel.add(txt1);
                    JLabel txt2 = new JLabel("Highest win rates: ");
                    txt2.setBounds(30, 400, 150, 30);
                    JLabel txt3 = new JLabel("Lowest win rates: ");
                    txt3.setBounds(30, 500, 150, 30);
                    finalLabel[3].setBounds(50, 435, 250, 30);
                    finalLabel[4].setBounds(50, 535, 250, 30);
                    panel.add(txt2);
                    panel.add(txt3);
                    panel.add(finalLabel[3]);
                    panel.add(finalLabel[4]);
                    panel.repaint();
                } catch (IOException err) {
                    System.out.println(err);
                }
                JLabel filler = new JLabel(text);
                filler.setPreferredSize(new Dimension(100,100));
                filler.setHorizontalAlignment(JLabel.CENTER);
                panel.add(filler);
            }
        });

        return panel;
    }

    private TreeMap<String, Double> getCharacterWinRate() {
        TreeMap<String, Double> result = new TreeMap<>();
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
        return result;
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

    private static int[][] getAnalysis (String sName, String inAPI) throws IOException {
        Map<String, int[]> cWR = new HashMap<>();
        int[] pWR = new int[3];
        System.setProperty("http.agent", "Chrome");
        String apiKey = "?api_key=" + inAPI;
        String prefix = "https://na1.api.riotgames.com";
        sName = sName.toLowerCase();
        sName = sName.replaceAll("[^a-zA-Z]", "");
//        System.out.println(sName);
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
//                    System.out.println(parsed1[j]);
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
//        System.out.println("Win rate: " + pWR[0] + " " + pWR[1] + " " + pWR[2] + " " + (double) pWR[0]*100/pWR[2]);
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
//        System.out.println("Most played champions:");
//        for (int i = 0; i < 3; i++) {
//            mostC = most3[i];
//            mostR = (double) cWR.get(mostC)[0]*100/cWR.get(mostC)[2];
//            System.out.println(mostC + " " + cWR.get(mostC)[0] + " " + cWR.get(mostC)[1] + " " + cWR.get(mostC)[2] + " " + mostR);
//        }
//                mostC + " " + cWR.get(mostC)[0] + " " + cWR.get(mostC)[1] + " " + cWR.get(mostC)[2] + " " + mostR);
//        System.out.println("Highest win rate " + highC + " " + cWR.get(highC)[0] + " " + cWR.get(highC)[1] + " " + cWR.get(highC)[2] + " " + highR);
//        System.out.println("Lowest win rate " + lowC + " " + cWR.get(lowC)[0] + " " + cWR.get(lowC)[1] + " " + cWR.get(lowC)[2] + " " + lowR);
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
        return result;
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

    private static void createAndShowGUI() {
        JFrame frame = new JFrame ("League Stats");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new test(), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldmetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}