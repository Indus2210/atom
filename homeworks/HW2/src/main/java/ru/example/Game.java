package ru.example;


import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Game.class);

    public static void main(String[] args) {
        log.info("Game started");
        System.out.println("Привет, ты зашел в игру Быки и Коровы!");
        start();

    }

    private static void start() {
        System.out.println("Выбери что ты хочешь сделать:");
        System.out.println("1 - Начать игру");
        System.out.println("2 - Правила игры");
        System.out.println("3 - Выход");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        switch (num) {
            case 1:
                start_game();
                break;
            case 2:
                rules();
                break;
            case 3:
                exit_game();
                break;
            default:
                exit_game();
        }

    }

    private static void exit_game() {
        log.info("Player finished game");
        System.out.println("Спасибо, что поиграл!");
        System.exit(0);

    }

    private static void rules() {
        log.info("The player opened the rules");
        System.out.println("Компьетер задумывает слово и объявляет длину слова заранее.");
        System.out.println("Участник предлагают свои варианты слов, а компьютер им отвечает в таком виде (допустим):");
        System.out.println(" 2б1к - это значит (два быка и одна корова), т.е. две буквы угаданы и стоят на своем");
        System.out.println("месте, одна угадана, но стоит не на своем месте.");
        System.out.println("На угадывание слова дается 5 попыток");
        Scanner in = new Scanner(System.in);
        System.out.println("Для продолжения нажми Enter...");
        in.nextLine();
        start();

    }


    public static int countCows(String wordPlayer, String wordGame) {
        char letter;
        int count = 0;
        boolean[] flagGame;
        boolean[] flagPlayer;

        flagGame = new boolean[wordGame.length()];
        flagPlayer = new boolean[wordPlayer.length()];
        for (int i = 0; i < wordGame.length(); i++) {
            if (wordGame.charAt(i) == wordPlayer.charAt(i)) {
                flagGame[i] = false;
                flagPlayer[i] = false;
            } else {
                flagGame[i] = true;
                flagPlayer[i] = true;
            }

        }
        for (int i = 0; i < wordPlayer.length(); i++) {
            letter = wordPlayer.charAt(i);
            if (wordGame.indexOf(letter) != -1 && flagPlayer[i]) {
                for (int j = 0; j < wordGame.length(); j++) {
                    if (letter == wordGame.charAt(j) && flagGame[j]) {
                        flagPlayer[i] = false;
                        flagGame[j] = false;
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }

    public static int countBulls(String wordPlayer, String wordGame) {
        int count = 0;
        for (int i = 0; i < wordGame.length(); i++) {
            if (wordGame.charAt(i) == wordPlayer.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    private static void start_game() {
        log.info("The player started a new game");
        System.out.println("Game on.");
        String gameword = wordfromfile();
        int bulls;
        int cows;
        System.out.println(gameword);
        System.out.println("The word consists of " + gameword.length() + " character");
        for (int round = 1; round <= 5; round++) {
            System.out.println("\n\nРаунд " + round);
            System.out.println("Введи слово:");
            Scanner in = new Scanner(System.in);
            String num = in.nextLine();
            bulls = countBulls(num, gameword);
            cows = countCows(num, gameword);

            System.out.println("Быков - " + bulls);
            System.out.println("Коров - " + cows);
            if (bulls == gameword.length()) {
                System.out.println("Ты выйграл! Поздравляю!!!!");
                log.info("Player won for " + round + "round");
                endgame();

            }

        }
        System.out.println("Ты проиграл! Повезет в другой раз!");
        log.info("The player lost without guessing the word");
        endgame();
    }

    private static void endgame() {
        System.out.println("Если хочешь сыргать еще жми 1, если хочешь выйти - 2");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        switch (num) {
            case 1:
                start_game();
                break;
            case 2:
                exit_game();
                break;
            default:
                exit_game();
                break;
        }


    }


    public static String wordfromfile() {
        log.info("Считано новое слово из словаря");
        List<String> words = new LinkedList<>();
        int rand = 0;
        try {
            File file = new File("D:\\Stud\\MAGA\\2 semestr\\java\\atom\\homeworks\\HW2\\\\dictionary.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();

            while (line != null) {
                words.add(line);
                line = reader.readLine();
            }
            int count = words.size();
            rand = (int) (Math.random() * count);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String wordreturn = words.get(rand);
        return wordreturn;
    }
}
