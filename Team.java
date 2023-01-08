public class Team {
private int teamNumber;
private final int CATEGORYCOUNT;
private double[] avgScores;
private double[] totalScores;
private int matchCount = 0;


public Team(int teamNumber, String[] categoryTypes) {
this.teamNumber = teamNumber;
CATEGORYCOUNT = categoryTypes.length + 1;
totalScores = new double[CATEGORYCOUNT]; // == 8
avgScores = new double[CATEGORYCOUNT];
} 

public String[] getDisplayData() {
String[] data = new String[CATEGORYCOUNT + 2];
data[0] = "" + teamNumber;
for (int x = 1; x < data.length - 1; x++)
data[x] = "" + avgScores[x - 1];
return data;
} 

public double[] getCompareData() {
double[] data = new double[CATEGORYCOUNT + 1];
data[0] = teamNumber;
for (int x = 1; x <= CATEGORYCOUNT; x++)
data[x] = avgScores[x - 1];
return data;
}

public void addMatch(String[] matchScore) {
matchCount++;
for (int x = 0; x < totalScores.length; x++) {
String[] temp = matchScore[x+1].split(":");
totalScores[x] += Double.parseDouble(temp[1]);
avgScores[x] = (double) totalScores[x] / matchCount;
} 
} 
} 