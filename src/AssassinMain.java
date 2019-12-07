import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssassinMain
{
    public static void main(String[] args)
    {
            List<String> playerList = initializeList();
            ShufflePlayerList(playerList);
            AssassinManager manager = new AssassinManager(playerList);
            AssassinView view = new AssassinView();
            AssassinController controller = new AssassinController(view, manager);
            view.setVisible(true);
    }
    public static List<String> initializeList() //reads strings from file stream, stores them in a list
    {
        List<String> playerList = new ArrayList<>();
        try
        {
            File file = new File("src/Text Resources/Players");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null)
            {
                playerList.add(st);
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return playerList;
    }
    public static void ShufflePlayerList(List<String> playerList) //shuffles playerlist
    {
        Collections.shuffle(playerList);
    }

}
