import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssassinController
{
    private AssassinView view;
    private AssassinManager manager;

    public AssassinController(AssassinView view, AssassinManager manager)
    {
        this.view = view;
        try
        {
            this.manager = manager;
        }
        catch (IllegalArgumentException NullEmptyList)
        {
            view.displayErrorMessage("Cannot pass null or empty list");
        }
        view.addNextKillListener(new NextKillListener());
        view.setCurrentKillRing(manager.returnKillRing());
        view.setCurrentGraveyard(manager.returnGraveyard());
    }
    class NextKillListener implements ActionListener //listens for kill button
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (!manager.isGameOver()) //check for game over on kill click
            {
                try
                {
                    String nextVictim = view.getNextVictim(); //parse view for next victim string
                    manager.kill(nextVictim); //try killing next victim
                }
                catch (IllegalStateException GameOver)
                {
                    view.displayErrorMessage("Game is already over.");
                }
                catch (IllegalArgumentException NotInRing)
                {
                    view.displayErrorMessage("Person not in current Kill Ring.");
                }
                catch (NoSuchFieldException PlayerDead)
                {
                    String nextVictim = view.getNextVictim();
                    nextVictim = nextVictim.toLowerCase();
                    nextVictim = nextVictim.substring(0, 1).toUpperCase() + nextVictim.substring(1); //capitalizes first letter of string
                    view.displayErrorMessage(nextVictim + " is already dead");
                }
                view.setNextVictim(""); //reset next victim in view to blank
                if (!manager.isGameOver()) //check again if the game has ended after previous kill, if not then show the updated kill ring and graveyard
                {
                    view.setCurrentKillRing(manager.returnKillRing());
                    view.setCurrentGraveyard(manager.returnGraveyard());
                }
                else //previous kill ended game, declare winner
                {
                    String winner = manager.winner();
                    view.setCurrentKillRing(winner + " has won!");
                    view.setCurrentGraveyard("Final Graveyard: \n\n" + manager.returnGraveyard());
                }
            }
            else //game was over before kill button was clicked
            {
                String winner = manager.winner();
                view.displayErrorMessage(winner + " has already won.");
            }

        }
    }
}
