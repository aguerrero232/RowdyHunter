package application.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HighScores implements Comparable<HighScores> {

    private int score = 0;
    private String username = "";
    private ArrayList<HighScores> scores = new ArrayList<HighScores>();

    public HighScores(int score, String username) throws IOException {
        this.score = score;
        this.username = username;
    }

    public ArrayList<HighScores> getScores() {
        return scores;
    }

    public HighScores getScoreFromLine(String line) throws IOException {
        String[] vars = new String[2];
        Scanner scan = new Scanner(line);
        scan.useDelimiter(",");
        for (int i = 0; i < vars.length; i++)
            vars[i] = scan.next();

        return new HighScores(Integer.valueOf(vars[0]), vars[1]);
    }

    public void loadScores() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("RowdyHunter/resources/data/highscores.csv")));
        String line = "";
        String[] vars = new String[2];
        HighScores tmp = null;
        while ((line = reader.readLine()) != null) {
            tmp = getScoreFromLine(line);
            scores.add(tmp);
        }
        Collections.sort(scores);
        scores = getTop5(scores);
    }

    private ArrayList<HighScores> getTop5(ArrayList<HighScores> tmp) {
        ArrayList<HighScores> nottmp = new ArrayList<HighScores>();
        for (int i = 0; i < 5; i++) {
            if (i > tmp.size())
                break;
            nottmp.add(tmp.get(i));
        }
        return nottmp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return getScore() + "," + getUsername() + "\n";
    }

    public void writeScoreToFile(int score) throws IOException {
        HighScores test = new HighScores(score, this.username), tmp;
        BufferedReader reader = new BufferedReader(new FileReader(new File("RowdyHunter/resources/data/highscores.csv")));
        String output = "", line = "";
        String[] vars = new String[2];

        if (!scores.isEmpty())
            scores.clear();

        scores.add(test);

        while ((line = reader.readLine()) != null) {
            tmp = getScoreFromLine(line);
            scores.add(tmp);
        }

        Collections.sort(scores);

        while (scores.size() > 5)
            scores.remove(scores.size() - 1);

        for (int index = 0; index < scores.size(); index++)
            output += scores.get(index).toString();

        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("RowdyHunter/resources/data/highscores.csv")));
        writer.write(output);
        writer.close();
    }

    public int compareTo(HighScores pscore) {
        return -(this.score - pscore.score);
    }

}
