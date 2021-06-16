package view;

import model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardPage extends Page
{
    
    public void setCommandPatterns(String commandPatterns) 		
    {

    }
    
    public void enterMenu(String menuName)
    {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }		
    
    private void showScoreboard() 		
    {
        ArrayList<User> users = User.getUsers();
        Collections.sort(users, new UserSortingComparator());
        int rank = 0;
        for (int i = 0; i < users.size(); i++) {
            if (!(i > 0 && users.get(i).getScore() == users.get(i - 1).getScore())) rank = i;
            System.out.println((rank + 1) + "- " + users.get(i).getNickname() + ": " + users.get(i).getScore());
        }
    } // write a test for here
    
    public void setUsername(String username) 		
    {
        
    }		
    
    public void exitMenu() {
        currentMenu = Menu.MAIN;
    }
    
    public void showCurrentMenu() 		
    {
        
    }

    private ArrayList<User> sortUsers(ArrayList<User> users) {

        return users;
    }

    public void runScoreboardPage(String command) {
        String[] commandPatterns = {
                "scoreboard show",
                "menu enter (\\S+)",
                "menu exit"
        };

        isCommandValid = false;
        for (functionNumber = 0; functionNumber < commandPatterns.length && !isCommandValid; functionNumber++) {
            getCommandMatcher(command, commandPatterns[functionNumber]);
        }
        if (!isCommandValid) System.out.println("invalid command");
    }

    private void getCommandMatcher(String command, String commandPattern) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            if (functionNumber == 0) showScoreboard();
            else if (functionNumber == 1) enterMenu(matcher.group(1));
            else if (functionNumber == 2) exitMenu();
            isCommandValid = true;
        }
    }

    static class UserSortingComparator
            implements Comparator<User> {

        @Override
        public int compare(User a, User b)
        {
            // for comparison
            int scoreCompare = b.getScore() - a.getScore();
            int nicknameCompare = a.getNickname().compareTo(b.getNickname());

            // 2-level comparison
            return (scoreCompare == 0) ? nicknameCompare : scoreCompare;
        }
    }
}

class SortByScore implements Comparator<User> {
    // Used for sorting in ascending order of
    public int compare(User a, User b)
    {
        return b.getScore() - a.getScore();
    }
}


