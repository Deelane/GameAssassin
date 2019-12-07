import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AssassinView extends JFrame //creates a new view for our game
{
    private JLabel nextVictimLabel = new JLabel("Next Victim?");
    private JTextField nextVictim = new JTextField(10);
    private JButton nextKillButton = new JButton("Kill");
    private JLabel currentKillRingLabel = new JLabel("Current Kill Ring:");
    private JTextPane currentKillRing = new JTextPane();
    private JLabel currentGraveyardLabel = new JLabel("Current Graveyard:");
    private JTextPane currentGraveyard = new JTextPane();

    public AssassinView()
    {
        JPanel AssassinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Left aligns all of our components
        this.getRootPane().setDefaultButton(nextKillButton); //Allows enter key to control kill button
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ensures program stops when X button is hit
        this.setSize(975, 250);
        this.setResizable(false);
        this.setTitle("Game Assassin");

        String title = "Game Assassin";
        Border border = BorderFactory.createTitledBorder(title);
        AssassinPanel.setBorder(border);

        nextVictimLabel.setHorizontalAlignment(JLabel.CENTER);
        AssassinPanel.add(nextVictimLabel);

        AssassinPanel.add(nextVictim);
        nextVictim.requestFocusInWindow();

        this.addWindowListener(new WindowAdapter()//ensures cursor appears in next victim text field on startup
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                nextVictim.requestFocus();
            }
        });

        nextKillButton.setHorizontalAlignment(JButton.CENTER);
        AssassinPanel.add(nextKillButton);

        currentKillRingLabel.setHorizontalAlignment(JLabel.CENTER);
        AssassinPanel.add(currentKillRingLabel);

        currentKillRing.setEditable(false);
        AssassinPanel.add(currentKillRing);

        currentGraveyardLabel.setHorizontalAlignment(JLabel.CENTER);
        AssassinPanel.add(currentGraveyardLabel);

        currentGraveyard.setEditable(false);
        AssassinPanel.add(currentGraveyard);

        this.add(AssassinPanel);
    }
    public String getNextVictim()
    {
        return nextVictim.getText();
    }
    public void setNextVictim(String string)
    {
        nextVictim.setText(string);
    }
    public void setCurrentKillRing(String killRing)
    {
        currentKillRing.setText(killRing);
    }
    public void setCurrentGraveyard(String graveyard)
    {
        currentGraveyard.setText(graveyard);
    }
    void addNextKillListener (ActionListener listenforNextKillButton) //event listener for our next kill button. Executes the kill method when clicked.
    {
        nextKillButton.addActionListener(listenforNextKillButton);
    }
    void displayErrorMessage(String errorMessage)
    {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}
