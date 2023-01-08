public class BinaryTree {
	private final int SORTCATEGORY;
	private int count = 0;
	private int size = 0;
	private Node root;
	private Team[] teams = null;

	public BinaryTree(int sortCategory) {
		SORTCATEGORY = sortCategory;
		root = null;
	} 

	public void addNode(Team team) {
		if (root == null)
			root = new Node(team);
		else
			root.add(team, SORTCATEGORY);
		size++;
	}

	public Team[] getSortedData(boolean order) {
		teams = new Team[size];
		if (order)
			getDecreasingOrder(root);
		else
			getIncreasingOrder(root);
		return teams;
	} 

	public void getDecreasingOrder(Node current) {
		if (current != null) {
			getDecreasingOrder(current.getRight());
			teams[count] = current.getTeam();
			count++;
			getDecreasingOrder(current.getLeft());
		} 
	} 

	public void getIncreasingOrder(Node current) {
		if (current != null) {
			getIncreasingOrder(current.getLeft());
			teams[count] = current.getTeam();
			count++;
			getIncreasingOrder(current.getRight());
		} 
	} 
}
