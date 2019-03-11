package ru.example;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class UnitTest {

    @Test
    public void CheckBulls() throws Exception {
        String game_word = "aaasss";
        String player_word = "asasas";
        int bulls = Game.countBulls(game_word,player_word);
        assertEquals(4, bulls);
    }


    @Test
    public void CheckCows() throws Exception {
        String game_word = "aaasss";
        String player_word = "asasas";
        int cows = Game.countCows(game_word,player_word);
        assertEquals(2, cows);
    }

    @Test
    public void CheckReading() throws Exception {
        String word = Game.wordfromfile();
        boolean flag;
        if (word instanceof String)
        {
            flag = true;
        }
        else {
            flag = false;
        }
        assertTrue(flag);
    }
}
