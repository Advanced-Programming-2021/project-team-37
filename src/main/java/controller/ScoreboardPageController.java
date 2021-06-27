package controller;

public class ScoreboardPageController extends Controller {
    private ScoreboardPageController instance;

    private ScoreboardPageController() {

    }

    private ScoreboardPageController getInstance() {
        if (instance == null)
            instance = new ScoreboardPageController();
        return instance;
    }
}
