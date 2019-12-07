import java.util.List;
import java.util.Stack;

public class AssassinManager //Creates a circularly linked list of strings
{
    private AssassinNode head; //head node
    private Stack<AssassinNode> graveyard = new Stack<>();

    private class AssassinNode //node constructor
    {
        private String playerName;
        private String victimName;
        private String killerName;
        private AssassinNode next;
    }

    public AssassinManager(List<String> playerList) //circular list constructor
    {
        try
        {
            if (playerList == null || playerList.size() == 0)
            {
                throw new IllegalArgumentException("Cannot pass null or empty list");
            }
            else
            {
                int size = playerList.size();
                head = new AssassinNode();
                AssassinNode temp = head;
                for (int i = 0; i < size; i++) //the magic is done here. links our nodes together.
                {
                    if (i + 1 != size) //if we are not on our last iteration
                    {
                        temp.playerName = playerList.get(i);
                        temp.victimName = playerList.get(i + 1);
                        temp.next = new AssassinNode();
                        temp = temp.next;

                    }
                    else //we are on our last node, so link it to the head.
                    {
                        temp.playerName = playerList.get(i);
                        temp.victimName = head.playerName;
                        temp.next = head;
                    }
                }
            }
        }
        catch (IllegalArgumentException NullEmptyList)
        {
            System.out.println("Cannot pass null or empty list.");
            throw NullEmptyList; //rethrow exception for controller to catch
        }
    }
    public void printKillRing()
    {
        if (!isGameOver())
        {
            AssassinNode temp = head;
            while (temp.next != head) //iterate from head to 2nd to last node
            {
                System.out.println("  " + temp.playerName + " is stalking " + temp.victimName);
                temp = temp.next;
            }
            System.out.println("  " + temp.playerName + " is stalking " + temp.victimName); //handle last node
        }
        else
        {
            String winner = winner();
            System.out.println(winner + " has already won.");
        }
    }
    public void printGraveyard()
    {
        Stack<AssassinNode> temp = (Stack<AssassinNode>) graveyard.clone(); //clone graveyard stack
        while (!temp.isEmpty()) //print each killed player, most recently killed first
        {
            AssassinNode player = temp.pop();
            System.out.println("  " + player.playerName + " was killed by " + player.killerName);
        }
    }
    public boolean killRingContains(String name)
    {
        AssassinNode temp = head;
        while (temp.next != head) //check head to 2nd to last node
        {
            if (name.equalsIgnoreCase(temp.playerName))
            {
                return true;
            }
            else
            {
                temp = temp.next;
            }
        }
        if (name.equalsIgnoreCase(temp.playerName)) //check last node
        {
            return true;
        }
        return false;
    }

    public boolean graveyardContains(String name)
    {
        Stack<AssassinNode> temp = (Stack<AssassinNode>) graveyard.clone(); //clone graveyard stack
        while (!temp.isEmpty()) //check graveyard for name passed
        {
            AssassinNode player = temp.pop();
            if (name.equalsIgnoreCase(player.playerName))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver()
    {
        return head.next == null; //game is over if head has no next node
    }
    public String winner() //checks for gameover, returns head's (only node) playername
    {
        if (isGameOver())
        {
            return head.playerName;
        }
        return null;
    }
    public void kill(String name) throws NoSuchFieldException {
        try
        {
            if (isGameOver())
            {
                throw new IllegalStateException();
            }
            else if (!killRingContains(name) && !graveyardContains(name))
            {
                throw new IllegalArgumentException("Player not part of current game");
            }
            else if (graveyardContains(name))
            {
                throw new NoSuchFieldException("Player is already dead");
            }
            else
            {
                AssassinNode temp = head;
                while (temp.next != head) //iterate from head to 2nd to last node, look at the current node's next node
                {
                    if (name.equalsIgnoreCase(temp.next.playerName)) //check next node to see if it's our player to kill
                    {
                        temp.next.killerName = temp.playerName; //set killer name of next node to current playername
                        graveyard.push(temp.next); //add killed node to graveyard
                        temp.next = temp.next.next; //delete references to killed node
                        temp.victimName = temp.next.playerName; //update current node's victim name to next node's playername
                    }
                    else
                    {
                        temp = temp.next;
                    }
                }
                if (name.equalsIgnoreCase(head.playerName)) //temp is currently at tail, check head node to see if it's our player to kill
                {
                    head.killerName = temp.playerName; //set killer name of head node to tail's playername
                    graveyard.push(head); //add head to graveyard
                    head = head.next; //replace head with next node
                    temp.next = head; //point tail to new head
                    temp.victimName = head.playerName; //update tail's victim name to new head's playername
                }
                if (head.next == head) //with current implementation, kill will leave 2 identical nodes at the end, when that happens, set one of them to null
                {
                    head.next = null;
                }
            }
        }
        catch (IllegalStateException GameOver)
        {
            System.out.println("Game is already over.");
            throw GameOver; //rethrow exception for controller to catch
        }
        catch (IllegalArgumentException NotInRing)
        {
            System.out.println("Person not in current Kill Ring.");
            throw NotInRing; //rethrow exception for controller to catch
        }
        catch (NoSuchFieldException PlayerDead)
        {
            System.out.println(name + " is already dead");
            throw PlayerDead; //rethrow exception for controller to catch
        }
    }
    public String returnKillRing() //returns actual killring string to pass to controller, and subsequently view
    {
        StringBuilder killRing = new StringBuilder();
        AssassinNode temp = head;
        while (temp.next != head) //iterate from head to 2nd to last node
        {
            killRing.append("  ").append(temp.playerName).append(" is stalking ").append(temp.victimName).append("\n");
            temp = temp.next;
        }
        killRing.append("  ").append(temp.playerName).append(" is stalking ").append(temp.victimName).append("\n"); //append last node
        return killRing.toString();
    }
    public String returnGraveyard() //returns actual graveyard string to pass to controller, and subsequently view
    {
        StringBuilder graveyardString = new StringBuilder();
        Stack<AssassinNode> temp = (Stack<AssassinNode>) graveyard.clone(); //clone graveyard stack
        while (!temp.isEmpty()) //append to string each killed player, most recently killed first
        {
            AssassinNode player = temp.pop();
            graveyardString.append("  ").append(player.playerName).append(" was killed by ").append(player.killerName).append("\n");
        }
        return graveyardString.toString();
    }
}
