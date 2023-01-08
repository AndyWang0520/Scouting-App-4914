import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends PanelTemplate{
	public static final String NEWEVENT = "New Event";
	public static final String CURRENTEVENT = "Current Event";
	private static final String LOADEVENT = "Load Event";
	public JButton dataButton;

	public HomePage(TeamData main) {
		super(main, new BorderLayout(10, 10));
	} 

	protected void addComponents(Container pane) {
		addLabel(main.getTitle(), pane, BorderLayout.NORTH);
		JPanel optionPanel = new JPanel(new GridLayout(2, 1));
		dataButton = new JButton(NEWEVENT);
		dataButton.addActionListener(this);
		dataButton.setActionCommand(NEWEVENT);
		dataButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		dataButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		optionPanel.add(dataButton);
		addButton(LOADEVENT, optionPanel);
		pane.add(optionPanel, BorderLayout.CENTER);
	} 

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case NEWEVENT:
			main.goToPage(this, main.getStatsPage());
			break;
		case LOADEVENT:
			main.importFile();
			break;
		default:
			break;
		} 
	} 
} 