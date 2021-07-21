import model.Card;
import model.Deck;
import model.User;
import model.server.Server;

public class Main {

    public static void test(String str) {
        new User(str, str, str);
        User.getUserByUsername(str).setScore(200);
        User.getUserByUsername(str).getDecks().add(new Deck("d1"));
        User.getUserByUsername(str).setActivatedDeck(User.getUserByUsername(str).getDeckByDeckName("d1"));
        addCardToDeckByUsername(str);
    }

    private static void addCardToDeckByUsername(String username) {
        for (int i = 5; i < 60; i++){
            User.getUserByUsername(username).getCards().add(Card.getCards().get(i));
            User.getUserByUsername(username).getDeckByDeckName("d1").getMainDeckCards().add(Card.getCards().get(i));
            //User.getUserByUsername(username).getDeckByDeckName("d1").getMainDeckCards().add(Card.getCards().get(23));
            //User.getUserByUsername(username).getDeckByDeckName("d1").getMainDeckCards().add(Spell.getSpells().get(5));
        }
    }


    public static void main(String[] args) {
        try {
            Card.setCards();
            test("1");
            test("2");
            test("admin");
            System.out.println(User.getUsers());
            new Server().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
