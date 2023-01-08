import java.awt.event.ActionEvent;

public class CompletionPanel extends CategoryPanel {

 public CompletionPanel(String ruleLabel) {
 super("\u2713", "x", ruleLabel, "NO");
 } 

 public int getResult() {
 if(result.getText().equals("YES"))
 return 1;
 return 0;
 }

 public void actionPerformed(ActionEvent e) {
 if(e.getSource().equals(up))
 result.setText("YES");
 else if(e.getSource().equals(down))
 result.setText("NO");
 revalidate();
 } 
} 