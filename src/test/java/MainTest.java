import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    public static void testMain(String[] args) {
        assertEquals("helwelo", Main.print("hello"), "it works");
    }
}