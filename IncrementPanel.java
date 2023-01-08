import java.awt.event.ActionEvent;

public class IncrementPanel extends CategoryPanel {

 public IncrementPanel(String categoryLabel) {
 super("+1", "-1", categoryLabel, "0");
 } 

 @Override
 public int getResult() {
 return Integer.parseInt(result.getText());
 }


 public void actionPerformed(ActionEvent e) {
 int score = Integer.parseInt(result.getText());
 if(e.getSource().equals(up))
 result.setText(Integer.toString(score + 1));
 else if(e.getSource().equals(down) && score > 0)
 result.setText(Integer.toString(score - 1));
 revalidate();
 }
} 