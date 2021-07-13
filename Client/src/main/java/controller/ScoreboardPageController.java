package controller;

public class ScoreboardPageController extends Controller {
    private static ScoreboardPageController instance;

    private ScoreboardPageController() {

    }

    public static ScoreboardPageController getInstance() {
        if (instance == null)
            instance = new ScoreboardPageController();
        return instance;
    }
}
