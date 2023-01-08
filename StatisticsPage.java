import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StatisticsPage extends PanelTemplate implements MouseListener{
	private static String[] tableIdentifier;
	private static final String ADD = "Add Match";
	private static final String EXPORT = "Export Data";
	private DefaultTableModel tableModel;
	private JTable table;
	private boolean[] sortCategory;

	public StatisticsPage(TeamData main) {
		super(main, null);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		sortCategory = new boolean[main.CATEGORYTYPES.length + 2];
		for (int x = 0; x < main.CATEGORYTYPES.length + 2; x++)
			sortCategory[x] = false;
	} 

	protected void addComponents(Container pane) {
		tableIdentifier = new String[main.CATEGORYTYPES.length + 2];
		tableIdentifier[0] = "Team";
		for (int x = 0; x < main.CATEGORYTYPES.length; x++) {
			String[] input = main.CATEGORYTYPES[x].split(";");
			tableIdentifier[x + 1] = input[0];
		} 
		tableIdentifier[tableIdentifier.length - 1] = "Score";
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.X_AXIS));
		addButton(ADD, options);
		addButton(EXPORT, options);
		pane.add(options);
		JPanel information = new JPanel();
		information.setLayout(new BoxLayout(information, BoxLayout.Y_AXIS));
		addLabel("Team Statistics (average per game)", information, null);
		tableModel = new DefaultTableModel(0, tableIdentifier.length);
		tableModel.setColumnIdentifiers(tableIdentifier);
		table = new JTable(tableModel);
		table.setCellSelectionEnabled(false);
		information.add(new JScrollPane(table));
		pane.add(information);
		table.getTableHeader().addMouseListener(this);
	} 

	public boolean isEmpty() {
		if (tableModel.getRowCount() == 0) {
			main.saved = true;
			main.getHomePage().dataButton.setText(HomePage.NEWEVENT);
			return true;
		} 
		main.getHomePage().dataButton.setText(HomePage.CURRENTEVENT);
		return false;
	} 

	public void addTeam(String[] data) {
		for (int x = 0; x < tableModel.getRowCount(); x++) {
			String[] input = data[0].split(":");
			if ( tableModel.getValueAt(x, 0).equals(input[0]) ) {
				tableModel.removeRow(x);
				tableModel.insertRow(x, data);
				return;
			} 
		}

		tableModel.insertRow(0, data);
	} 

	public String[] getIdentifiers() {
		String[] headers = new String[tableIdentifier.length];
		for (int x = 0; x < tableIdentifier.length; x++)
			headers[x] = tableIdentifier[x].replaceAll("\\s+", "");
		return headers;
	} 
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case ADD:

			new ScoutPage(main);
			break;
		case EXPORT:
			main.exportFile();
			break;
		default:
			break;
		} 
	} 

	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(table.getTableHeader())) {
			int columnNumber = table.columnAtPoint(e.getPoint());
			BinaryTree tree = new BinaryTree(columnNumber);
			for (int x = 0; x < main.getTeamCount(); x++)
				tree.addNode(main.getTeamByIndex(x));
			if (sortCategory[columnNumber] == false) {

				for (int x = 0; x < sortCategory.length; x++)
					sortCategory[x] = false;
				sortCategory[columnNumber] = true;
			} else
				sortCategory[columnNumber] = false;
			Team[] sortedTeams = tree.getSortedData(sortCategory[columnNumber]);
			for (int x = 0; x < main.getTeamCount(); x++)
				tableModel.removeRow(0);
			for (int x = 0; x < main.getTeamCount(); x++)
				tableModel.addRow(sortedTeams[x].getDisplayData());
			if(sortCategory[columnNumber])
				JOptionPane.showMessageDialog(null, "Sorted by " +
						tableIdentifier[columnNumber] + " from greatest to lowest.", "Sorting Notification", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Sorted by " + tableIdentifier[columnNumber] + " from lowest to greatest.", "Sorting Notification", JOptionPane.INFORMATION_MESSAGE);
		}
	} 

	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
	public void mousePressed(MouseEvent evt){}
	public void mouseReleased(MouseEvent evt){}
}