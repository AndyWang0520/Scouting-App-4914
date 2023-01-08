import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class TeamData extends JFrame implements ActionListener {
	private static final String TITLE = "FRC4914 Scouting App";
	public static final String[] CATEGORYTYPES = {"Auto Line;t/f", "Bottom Port;inc", "Outer Port;inc", "Inner Port;inc", "Rotation Control;t/f", "Colour Control;t/f", "Climb;t/f", "Defense;t/f"};
	public static final int[] CATEGORYSCORE = {5, 2, 4, 6, 10, 20, 25, 0};
	private static final Dimension PREFSIZE = new Dimension(500, 500);
	private static final Dimension MINSIZE = new Dimension(500, 400);
	private static final String MENU_OPTIONS = "Options";
	private static final String MENU_HOME = "Home";
	private static final String MENU_QUIT = "Quit";
	public boolean saved = true;
	private LinkedList<Team> teams = new LinkedList<Team>();
	private JMenuBar menuBar;
	private JMenu menu1;
	private JMenuItem item1, item2;
	private HomePage homePage;
	private StatisticsPage statsPage;

	public static void main(String[] args) {
		TeamData main = new TeamData(TITLE);
		main.homePage = new HomePage(main);
		main.statsPage = new StatisticsPage(main);
		main.homePage.setVisible(true);
		main.setContentPane(main.homePage);
	} 

	public TeamData(String title) {
		super();
		setTitle(title);
		setupFrame();
	} 

	private void setupFrame() {
		setVisible(true);
		setPreferredSize(PREFSIZE);
		setMinimumSize(MINSIZE);
		createMenu();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	} 

	private void createMenu() {
		menuBar = new JMenuBar();
		item1 = new JMenuItem(MENU_HOME);
		item2 = new JMenuItem(MENU_QUIT);
		item1.addActionListener(this);
		item2.addActionListener(this);
		menu1 = new JMenu(MENU_OPTIONS);
		menu1.add(item1);
		menu1.add(item2);
		menuBar.add(menu1);
		setJMenuBar(menuBar);
	} 

	public HomePage getHomePage() {
		return homePage;
	} 

	public StatisticsPage getStatsPage() {
		return statsPage;
	} 

	public void goToPage(PanelTemplate originalPage, PanelTemplate nextPage) {
		originalPage.setVisible(false);
		nextPage.setVisible(true);
		setContentPane(nextPage);
	}

	public void addTeam(int teamNum) {
		for (int x = 0; x <= teams.size(); x++) {
			if (x == teams.size()) {
				teams.addLast(new Team(teamNum, CATEGORYTYPES));
				return;
			} else if (((int) teams.get(x).getCompareData()[0]) == teamNum)
				return; 
			else if (((int) teams.get(x).getCompareData()[0]) > teamNum) {
				teams.add(x, new Team(teamNum, CATEGORYTYPES));
				return;
			} 
		} 
	} 

	public int getTeamCount() {
		return teams.size();
	} 
	public Team getTeam(int teamNum) {
		for (int x = 0; x <teams.size(); x++)
			if (teams.get(x).getCompareData()[0] == teamNum)
				return teams.get(x);
		return null;
	}

	public Team getTeamByIndex(int index) {
		return teams.get(index);
	} 

	public void importFile() {
		JFileChooser browse = new JFileChooser();
		int returnVal = browse.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File matchFile = browse.getSelectedFile();
			BufferedReader br = null;
			try {
				FileInputStream fis = new FileInputStream(matchFile);
				InputStreamReader isr = new InputStreamReader(fis);
				br = new BufferedReader(isr);
				boolean isEmpty = false;
				String nextLine = null;
				while ((nextLine = br.readLine()) != null) {
					String[] data = new
							String[statsPage.getIdentifiers().length];
					for (int x = 0; x <
							statsPage.getIdentifiers().length; x++) {
						String[] input = nextLine.split(":");
						if(input[0].equals(statsPage.getIdentifiers()[x])) {
							data[x] = nextLine;
							double test = Double.parseDouble(input[1]);
							if (test < 0)
								throw new
								NumberFormatException();
							nextLine = br.readLine();
						} 
						else
							throw new IOException();
					} 
					String[] teamNumberString = data[0].split(":");
					int teamNum = Integer.parseInt(teamNumberString[1]);
					String[] matchScore = new String[data.length];
					for (int x = 0; x < matchScore.length; x++)
						matchScore[x] = data[x];
					addTeam(teamNum);
					getTeam(teamNum).addMatch(matchScore);
					getStatsPage().addTeam(getTeam(teamNum).getDisplayData());
				} 
				goToPage(homePage, getStatsPage());
				saved = true;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error: this file format is not supported/ may be corrupted.", "File Error",JOptionPane.INFORMATION_MESSAGE);
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null, "Error: this file format is not supported/ may be corrupted.", "File Error", JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Error: the data stored in this file are not positive double values.", "File Error",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error: this file format is not supported/ may be corrupted.", "File Error",
						JOptionPane.INFORMATION_MESSAGE);
			} finally {
				try {
					br.close();
				} catch (IOException closeException) {
					JOptionPane.showMessageDialog(null, "An error occurred with closing the file.", "File Error", JOptionPane.INFORMATION_MESSAGE);
				} 
			} 
		}
	} 

	public boolean checkSaved() {
		if (!saved)
			JOptionPane.showMessageDialog(null, "Please export your file to ensure that your recent data is saved.");
		return saved;
	} 

	public void exportFile() {
		if (teams.size() == 0) {
			JOptionPane.showMessageDialog(null, "Error: there are no teams available.", "File Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		} 
		JFileChooser browse = new JFileChooser();
		int returnVal = browse.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File matchFile = browse.getSelectedFile();
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(matchFile));
				String[] identifiers = statsPage.getIdentifiers();
				for (int x = 0; x < teams.size(); x++) {
					for (int y = 0; y <
							teams.get(x).getDisplayData().length - 1; y++) {
						String input = identifiers[y] + ":" +
								teams.get(x).getDisplayData()[y] + "\n";
						bw.write(input);
					} 
					bw.write("\n");
				} 
				bw.close();
				saved = true;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error: a problem occurred while saving the file.", "File Error", JOptionPane.INFORMATION_MESSAGE);
			} 
		} 
	} 

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == item1) {
			if (checkSaved()) {
				statsPage.setVisible(false);
				homePage.setVisible(true);
				setContentPane(homePage);
				getStatsPage().isEmpty();
			} 
		} else if (source == item2) {
			if (checkSaved()) {
				setVisible(false);
				dispose();
			} 
		} 
	} 
} 