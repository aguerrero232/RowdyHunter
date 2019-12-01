/**
 * HighScores.java
 * 
 * @Author Ariel Guerrero
 */

package application.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HighScores implements Comparable<HighScores> {
    /**
	 * Holds score value
	 */
    private int score = 0;
    /**
     * Holds user's name
     */
    private String username = "";
    /**
     * ArrayList to hold HighScore objects
     */
    private ArrayList<HighScores> scores = new ArrayList<HighScores>();
    /**
     * Constructor
     * @param score
     * 			The score value passed in
     * @param username
     * 			The name value passed in
     * @throws IOException
     */
    public HighScores(int score, String username) throws IOException {
        this.score = score;
        this.username = username;
    }
    /**
     *Get the list of HighScore objects
     * @return
     */
    public ArrayList<HighScores> getScores() {
        return scores;
    }
    /**
     * Create a new HighScore object from string input
     * @param line
     * @return
     * @throws IOException
     */
    public HighScores getScoreFromLine(String line) throws IOException {
        String[] vars = new String[2];
        Scanner scan = new Scanner(line);
        scan.useDelimiter(",");
        for (int i = 0; i < vars.length; i++)
            vars[i] = scan.next();

        scan.close();
        return new HighScores(Integer.valueOf(vars[0]), vars[1]);
    }
    /**
     * Load score values from file
     * @throws IOException
     */
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
        reader.close();
    }
    /**
     * Pull the top 5 score values from a HighScore arraylist
     * @param tmp
     * @return
     * 			The top 5 scores in a new HighScore arraylist
     */
    private ArrayList<HighScores> getTop5(ArrayList<HighScores> tmp) {
        ArrayList<HighScores> nottmp = new ArrayList<HighScores>();
        for (int i = 0; i < 5; i++) {
            if (i > tmp.size())
                break;
            nottmp.add(tmp.get(i));
        }
        return nottmp;
    }
    /**
     * Get username
     * @return
     */
    public String getUsername() {
        return username;
    }
    /**
     * Set the username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Get score value
     * @return
     */
    public int getScore() {
        return score;
    }
    /**
     * Set the score value
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * Overridden toString to display scores
     */
    @Override
    public String toString() {
        return getScore() + "," + getUsername() + "\n";
    }
    /**
     * Read scores from file, add current score to values and write values to file
     * @param score
     * @throws IOException
     */
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

        reader.close();
        Collections.sort(scores);

        while (scores.size() > 5)
            scores.remove(scores.size() - 1);

        for (int index = 0; index < scores.size(); index++)
            output += scores.get(index).toString();
        //BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:/Users/lucio/Desktop/Programming FUN/RowdyHunter/RowdyHunter/resources/data/highscores.csv")));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("RowdyHunter/resources/data/highscores.csv")));
        writer.write(output);
        writer.close();
    }
    /**
     * Comparator for array sorting
     */
    public int compareTo(HighScores other) { return other.score - this.score; }

}
