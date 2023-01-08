import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class PanelTemplate extends JPanel implements ActionListener{
	protected TeamData main;


	public PanelTemplate(TeamData main, LayoutManager layout) {
		super(layout);
		this.main = main;
		addComponents(this);
		main.add(this);
		main.pack();
		setVisible(false);
	} 

	protected void addComponents(Container pane) {}

	protected void addLabel(String text, Container pane, String position) {
		JLabel label = new JLabel(text);
		JPanel panel = new JPanel();
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setAlignmentY(Component.CENTER_ALIGNMENT);
		panel.add(label);
		pane.add(panel, position);
	}

	protected void addTextField(String text, Container pane) {
		JTextField field = new JTextField(text);
		field.addActionListener(this);
		field.setActionCommand(text);
		field.setAlignmentX(Component.CENTER_ALIGNMENT);
		field.setAlignmentY(Component.CENTER_ALIGNMENT);
		pane.add(field);
	} 
	protected void addButton(String text, Container pane) {
		JButton button = new JButton(text);
		button.addActionListener(this);
		button.setActionCommand(text);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setAlignmentY(Component.CENTER_ALIGNMENT);
		pane.add(button);
	} 
	public void actionPerformed(ActionEvent e) {}
} 