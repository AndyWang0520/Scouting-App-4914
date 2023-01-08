import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ScoutPage extends JFrame implements ActionListener{
	private static final Dimension SIZE = new Dimension(450, 450);
	private TeamData main;
	private ArrayList<CategoryPanel> categories = new ArrayList<CategoryPanel>();
	private int teamNumber;
	private JTextField teamField;
	private JButton endButton;


	public ScoutPage(TeamData main) {
		super();
		this.main = main;
		setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addComponents(this);
		setSize(SIZE);
		setVisible(true);
	} 

	protected void addComponents(Container pane) {
		JPanel doublePanel = new JPanel();
		doublePanel.setLayout(new BoxLayout(doublePanel, BoxLayout.Y_AXIS));
		JPanel info = new JPanel(new GridLayout(2,2));
		JLabel teamLabel = new JLabel("Team Number: ");
		info.add(teamLabel);
		teamField = new JTextField();
		teamField.addActionListener(this);
		info.add(teamField);
		doublePanel.add(info, BorderLayout.NORTH);
		JLabel reminder = new JLabel("Make sure you press ENTER after each field provided above.");
		reminder.setAlignmentX(Component.CENTER_ALIGNMENT);
		doublePanel.add(reminder, BorderLayout.SOUTH);
		pane.add(doublePanel, BorderLayout.NORTH);
		JPanel statsPanel = new JPanel(new FlowLayout());
		for (int x = 0; x < main.CATEGORYTYPES.length; x++)
			createCategory(main.CATEGORYTYPES[x], statsPanel);
		pane.add(statsPanel, BorderLayout.CENTER);
		JPanel endPanel = new JPanel();
		endButton = new JButton("End Game");
		endButton.addActionListener(this);
		endPanel.add(endButton);
		pane.add(endPanel, BorderLayout.SOUTH);
	} 


	public void createCategory(String categoryData, Container pane) {
		String[] data = categoryData.split(";");
		String name = data[0];
		CategoryPanel category;
		if(data[1].equals("inc")) {
			category = new IncrementPanel(name);
			categories.add(category);
			pane.add(category);
		} else if(data[1].equals("t/f")) {
			category = new CompletionPanel(name);
			categories.add(category);
			pane.add(category);
		} 
	} 

	public int calculateScore(String[] matchScore) {
		int score = 0;
		for (int x = 0; x < main.CATEGORYSCORE.length - 1; x++) {
			String[] input = matchScore[x+1].split(":");
			int temp = Integer.parseInt(input[1]);
			score += temp * main.CATEGORYSCORE[x];
		} 
		return score;
	} 
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == teamField) {
			try {
				String input = teamField.getText();
				teamNumber = Integer.parseInt(input);
				if (teamNumber < 0)
					throw new NumberFormatException(); 
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter a valid team number.");
			} 
		} else if (source == endButton) {
			if (teamNumber == 0) {
				JOptionPane.showMessageDialog(null, "Please make sure you enter a valid team number and press ENTER.");
				return;
			} 
			String[] matchScore = new String[categories.size() + 3];
			matchScore[0] = "Team:" + teamNumber;
			for (int x = 1; x <= categories.size(); x++)
				matchScore[x] = categories.get(x - 1).getRuleLabel() + ":" + categories.get(x - 1).getResult();
			matchScore[categories.size() + 1] = "Score:" +
					calculateScore(matchScore);
			main.addTeam(teamNumber);
			main.getTeam(teamNumber).addMatch(matchScore);
			this.setVisible(false);

			main.getStatsPage().addTeam(main.getTeam(teamNumber).getDisplayData());
			main.saved = false;
			dispose();
		} 
	} 
} 