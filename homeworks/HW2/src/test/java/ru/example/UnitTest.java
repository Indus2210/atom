package ru.example;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class UnitTest {

    @Test
    public void checkBulls() throws Exception {
        String gameword = "aaasss";
        String playerword = "asasas";
        int bulls = Game.countBulls(gameword, playerword);
        assertEquals(4, bulls);
    }


    @Test
    public void checkCows() throws Exception {
        String gameword = "aaasss";
        String playerword = "asasas";
        int cows = Game.countCows(gameword, playerword);
        assertEquals(2, cows);
    }

    @Test
    public void checkReading() throws Exception {
        String word = Game.wordfromfile();
        boolean flag;
        if (word instanceof String) {
            flag = true;
        } else {
            flag = false;
        }
        assertTrue(flag);
    }
}
