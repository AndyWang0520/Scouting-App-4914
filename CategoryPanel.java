import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class CategoryPanel extends JPanel implements ActionListener {
 protected JButton down;
 protected JButton up;
 protected JLabel result;
 protected String categoryLabel;


public CategoryPanel(String labelUp, String labelDown, String categoryLabel,
 String defaultResult) {
 JLabel label = new JLabel(categoryLabel);
 this.categoryLabel = categoryLabel;
 label.setAlignmentX(Component.CENTER_ALIGNMENT);
 up = new JButton(labelUp);
 up.setAlignmentX(Component.CENTER_ALIGNMENT);
 up.addActionListener(this);
 result = new JLabel(defaultResult);
 result.setAlignmentX(Component.CENTER_ALIGNMENT);
 down = new JButton(labelDown);
 down.setAlignmentX(Component.CENTER_ALIGNMENT);
 down.addActionListener(this);
 setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 add(label);
 add(up);
 add(result);
 add(down);
 } 


 public String getRuleLabel() {
 return categoryLabel.replaceAll(" ", "");
 } 


 public abstract int getResult();

 @Override
 public void actionPerformed(ActionEvent e) {}
} 