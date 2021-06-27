package view;

import controller.LoginPageController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends Page {

    public static LoginPageController loginPageController;

    public LoginPage() {
        loginPageController = LoginPageController.getInstance();
    }

    public void runLoginPage(String command) {
        String[] commandPatterns = {"menu exit", "menu show-current",
                "user create --username (\\S+) --nickname (\\S+) --password (\\S+)",
                "user create --username (\\S+) --password (\\S+) --nickname (\\S+)",
                "user create --nickname (\\S+) --password (\\S+) --username (\\S+)",
                "user create --nickname (\\S+) --username (\\S+) --password (\\S+)",
                "user create --password (\\S+) --nickname (\\S+) --username (\\S+)",
                "user create --password (\\S+) --username (\\S+) --nickname (\\S+)",
                "user login --username (\\S+) --password (\\S+)",
                "user login --password (\\S+) --username (\\S+)",
                "menu enter (\\S+)"
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
            if (functionNumber == 0) exitMenu();
            else if (functionNumber == 1) showCurrentMenu();
            else if (functionNumber == 2) firstRegister(matcher);
            else if (functionNumber == 3) secondRegister(matcher);
            else if (functionNumber == 4) thirdRegister(matcher);
            else if (functionNumber == 5) fourthRegister(matcher);
            else if (functionNumber == 6) fifthRegister(matcher);
            else if (functionNumber == 7) sixthRegister(matcher);
            else if (functionNumber == 8) firstLoginUser(matcher);
            else if (functionNumber == 9) secondLoginUser(matcher);
            else if (functionNumber == 10) enterMenu();
            isCommandValid = true;
        }
    }

    private void firstRegister(Matcher matcher) {
        String username = matcher.group(1);
        String nickname = matcher.group(2);
        String password = matcher.group(3);
        createUser(username, nickname, password);
    }

    private void secondRegister(Matcher matcher) {
        String username = matcher.group(1);
        String nickname = matcher.group(3);
        String password = matcher.group(2);
        createUser(username, nickname, password);
    }

    private void thirdRegister(Matcher matcher) {
        String username = matcher.group(3);
        String nickname = matcher.group(1);
        String password = matcher.group(2);
        createUser(username, nickname, password);
    }

    private void fourthRegister(Matcher matcher) {
        String username = matcher.group(2);
        String nickname = matcher.group(1);
        String password = matcher.group(3);
        createUser(username, nickname, password);

    }

    private void fifthRegister(Matcher matcher) {
        String username = matcher.group(3);
        String nickname = matcher.group(2);
        String password = matcher.group(1);
        createUser(username, nickname, password);
    }

    private void sixthRegister(Matcher matcher) {
        String username = matcher.group(2);
        String nickname = matcher.group(3);
        String password = matcher.group(1);
        createUser(username, nickname, password);
    }

    private void createUser(String username, String nickname, String password) {
        LoginPageController.getInstance().registerUser(username, nickname, password);
        System.out.println(message);
    }

    private void firstLoginUser(Matcher matcher) {
        String username = matcher.group(1);
        String password = matcher.group(2);
        LoginPageController.getInstance().loginUser(username, password);
    }

    private void secondLoginUser(Matcher matcher) {
        String username = matcher.group(2);
        String password = matcher.group(1);
        LoginPageController.getInstance().loginUser(username, password);
        System.out.println(message);
    }

    public void setUsername(String username) {

    }

    public void setCommandPatterns(String commandPatterns) {

    }

    public void enterMenu() {
        System.out.println("please login first");
    }

    public void exitMenu() {
        currentMenu = Menu.EXIT;
    }

    public void showCurrentMenu() {
        System.out.println(currentMenu);
    }
}
