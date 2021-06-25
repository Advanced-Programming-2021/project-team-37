package view;

import controller.ProfilePageController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfilePage extends Page
{

    public void setUsername(String username)
    {

    }

    public void setCommandPatterns(String commandPatterns)
    {

    }

    public void NewOperation6()
    {

    }

    public void enterMenu(String menuName)
    {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }

    public void exitMenu()
    {
        currentMenu = Menu.MAIN;
    }

    public void showCurrentMenu()
    {

    }

    public void run()
    {

    }

    public void runProfilePage(String command) {
        String[] commandPatterns = {
                "profile change --nickname (\\S+)",
                "profile change --password --current (\\S+) --new (\\S+)",
                "profile change --password --new (\\S+) --current (\\S+)",
                "profile change --current (\\S+) --password --new (\\S+)",
                "profile change --current (\\S+) --new (\\S+) --password",
                "profile change --new (\\S+) --password --current (\\S+)",
                "profile change --new (\\S+) --current (\\S+) --password",
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
            if (functionNumber == 0) {
                ProfilePageController.getInstance().changeNickname(matcher.group(1));
                System.out.println(message);
            }
            else if (functionNumber == 1) firstChangePassword(matcher);
            else if (functionNumber == 2) secondChangePassword(matcher);
            else if (functionNumber == 3) thirdChangePassword(matcher);
            else if (functionNumber == 4) fourthChangePassword(matcher);
            else if (functionNumber == 5) fifthChangePassword(matcher);
            else if (functionNumber == 6) sixthChangePassword(matcher);
            else if (functionNumber == 7) enterMenu(matcher.group(1));
            else if (functionNumber == 8) exitMenu();
            isCommandValid = true;
        }
    }

    private void changePassword(String currentPassword, String newPassword) {
        ProfilePageController.getInstance().changePassword(currentPassword, newPassword);
        System.out.println(message);
    }

    private void firstChangePassword(Matcher matcher) {
        String currentPassword = matcher.group(1);
        String newPassword = matcher.group(2);
        changePassword(currentPassword, newPassword);
    }

    private void secondChangePassword(Matcher matcher) {
        String currentPassword = matcher.group(2);
        String newPassword = matcher.group(1);
        changePassword(currentPassword, newPassword);
    }

    private void thirdChangePassword(Matcher matcher) {
        String currentPassword = matcher.group(1);
        String newPassword = matcher.group(2);
        changePassword(currentPassword, newPassword);
    }

    private void fourthChangePassword(Matcher matcher) {
        String currentPassword = matcher.group(1);
        String newPassword = matcher.group(2);
        changePassword(currentPassword, newPassword);
    }

    private void fifthChangePassword(Matcher matcher) {
        String currentPassword = matcher.group(2);
        String newPassword = matcher.group(1);
        changePassword(currentPassword, newPassword);
    }

    private void sixthChangePassword(Matcher matcher) {
        String currentPassword = matcher.group(2);
        String newPassword = matcher.group(1);
        changePassword(currentPassword, newPassword);
    }
}
