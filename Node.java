public class Node {
private Node left;
private Node right;
private Team team;

public Node(Team team) {
this.team = team;
left = null;
right = null;
}
public Team getTeam() {
return team;
} 
public void add(Team newTeam, int sortCategory) {
if (newTeam.getCompareData()[sortCategory] <=
team.getCompareData()[sortCategory]) {
if (left == null)
left = new Node(newTeam);
else
left.add(newTeam, sortCategory);
} else {
if (right == null)
right = new Node(newTeam);
else
right.add(newTeam, sortCategory);
} 
} 
public Node getLeft() {
return left;
} 

public Node getRight() {
return right;
}
} 