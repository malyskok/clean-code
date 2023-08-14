package com.d.tdd;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MyGameTest {

    // 0 20
    // 1 20
    // 0 19, 1 1
    // 5 1, 5 1, 0 18

    public static final int FULL_GAME_NUMBER_OF_TIMES = 20;
    MyGame game = new MyGame();

    @Test
    void testScore_all0s() {
        knockMultipleTimes(0, FULL_GAME_NUMBER_OF_TIMES);
        assertEquals(0, game.getScore());
    }

    @Test
    void testScore_all1s() {
        knockMultipleTimes(1, FULL_GAME_NUMBER_OF_TIMES);
        assertEquals(FULL_GAME_NUMBER_OF_TIMES, game.getScore());
    }

    @Test
    void testScore_all2s() {
        knockMultipleTimes(2, FULL_GAME_NUMBER_OF_TIMES);
        assertEquals(40, game.getScore());
    }

    @Test
    void testScore_all0s_last1() {
        knockMultipleTimes(0, FULL_GAME_NUMBER_OF_TIMES - 1);
        knockMultipleTimes(1, 1);
        assertEquals(1, game.getScore());
    }

    // 5 1, 5 1, 0 18
    @Test
    void testScore_spareOnFirst_other0s() {
        rollSpare();
        knockMultipleTimes(0, FULL_GAME_NUMBER_OF_TIMES - 2);
        assertEquals(10, game.getScore());
    }

    @Test
    void testScore_spareOnFirst_second1_other0s() {
        rollSpare();
        knockMultipleTimes(1, 1);
        knockMultipleTimes(0, FULL_GAME_NUMBER_OF_TIMES - 3);
        assertEquals(5 + (5 + 1) + 1, game.getScore());
    }

    @Test
    void testScore_spareOnSecond_third1_other0s() {
        knockMultipleTimes(0, 2);
        rollSpare();
        knockMultipleTimes(1, 1);
        knockMultipleTimes(0, FULL_GAME_NUMBER_OF_TIMES - 5);
        assertEquals(5 + (5 + 1) + 1, game.getScore());
    }

    @Test
    void testScore_spareOnMiddle_other0s() {
        knockMultipleTimes(0, 1);
        rollSpare();
        knockMultipleTimes(1, 1);
        knockMultipleTimes(0, FULL_GAME_NUMBER_OF_TIMES - 4);
        assertNotEquals(0 + 5 + (5 + 1) + 1, game.getScore());
        assertEquals(5 + 5 + 1, game.getScore());
    }

    @Test
    void testScore_twoSpares_third1_other0s() {
        rollSpare();
        rollSpare();
        knockMultipleTimes(1, 1);
        knockMultipleTimes(0, FULL_GAME_NUMBER_OF_TIMES - 5);
        assertEquals((5 + 5 + 5) + (5 + 5 + 1) + 1, game.getScore());
    }

    @Test
    void testScore_strike() {
        // 10 0| 1 1| 0 0 ...
        knockMultipleTimes(10, 1);
        knockMultipleTimes(0, 1);
        knockMultipleTimes(1, 2);
        knockMultipleTimes(0, FULL_GAME_NUMBER_OF_TIMES - 4);
        assertEquals((10 + 1 + 1) + (1 + 1), game.getScore());
    }

    private void rollSpare() {
        knockMultipleTimes(5, 2);
    }

    private class MyGame {

        int index;
        int[] scores = new int[21];

        public void roll(int pinsKnockedDown) {
            scores[index++] = pinsKnockedDown;
        }

        public int getScore() {
            int sum = 0;
            int rollIndex = 0;
            for (int frame = 0; frame < 10; frame++) {
                if(isStrike(rollIndex)){
                    sum += getBonusForStrike(rollIndex);
                } else if (isSpare(rollIndex)) {
                    sum += getBonusForSpare(rollIndex);
                }
                sum += getFrameScore(rollIndex);
                rollIndex += 2;
            }
            return sum;
        }

        private int getFrameScore(int rollIndex) {
            return scores[rollIndex] + scores[rollIndex + 1];
        }

        private int getBonusForSpare(int rollIndex) {
            return scores[rollIndex + 2];
        }

        private int getBonusForStrike(int rollIndex) {
            return getBonusForSpare(rollIndex) + scores[rollIndex + 3];
        }

        private boolean isStrike(int rollIndex) {
            return scores[rollIndex] == 10 || scores[rollIndex + 1] == 10;
        }

        private boolean isSpare(int rollIndex) {
            return getFrameScore(rollIndex) == 10;
        }
    }

    private void knockMultipleTimes(int pinsKnockedDown, int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            game.roll(pinsKnockedDown);
        }
    }
}
