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
        log.info("Игра запущена");
        System.out.println("Привет, ты зашел в игру Быки и Коровы!");
        StartScrin();

    }

    private static void StartScrin() {
        System.out.println("Выбери что ты хочешь сделать:");
        System.out.println("1 - Начать игру");
        System.out.println("2 - Правила игры");
        System.out.println("3 - Выход");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        switch (num) {
            case 1:
                Game();
                break;
            case 2:
                Rules();
                break;
            case 3:
                Exit();
                break;
        }

    }

    private static void Exit() {
        log.info("Игрок закончил игру");
        System.out.println("Спасибо, что поиграл!");
        System.exit(0);

    }

    private static void Rules() {
        log.info("Игрок открыл правила");
        System.out.println("Компьетер задумывает слово и объявляет длину слова заранее.");
        System.out.println("Участник предлагают свои варианты слов, а компьютер им отвечает в таком виде (допустим):\n 2б1к - это значит (два быка и одна корова), т.е. две буквы угаданы и стоят на своем месте, одна угадана, но стоит не на своем месте.");
        System.out.println("На угадывание слова дается 5 попыток");
        Scanner in = new Scanner(System.in);
        System.out.println("Для продолжения нажми Enter...");
        in.nextLine();
        StartScrin();

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

    private static void Game() {
        log.info("Игрок начал новую игру");
        System.out.println("Игра началась");
        String game_word = WordFromFile();
        int Bulls, Cows;
        System.out.println(game_word);
        System.out.println("Слово состоит из " + game_word.length() + " букв");
        for (int round = 1; round <= 5; round++) {
            System.out.println("\n\nРаунд " + round);
            System.out.println("Введи слово:");
            Scanner in = new Scanner(System.in);
            String num = in.nextLine();
            Bulls = countBulls(num, game_word);
            Cows = countCows(num, game_word);

            System.out.println("Быков - " + Bulls);
            System.out.println("Коров - " + Cows);
            if (Bulls == game_word.length()) {
                System.out.println("Ты выйграл! Поздравляю!!!!");
                log.info("Игрок выйграл за " + round + "ходов");
                EndGame();

            }

        }
        System.out.println("Ты проиграл! Повезет в другой раз!");
        log.info("Игрок проиграл, не угадав слово");
        EndGame();
    }

    private static void EndGame() {
        System.out.println("Если хочешь сыргать еще жми 1, если хочешь выйти - 2");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        switch (num) {
            case 1:
                Game();
                break;
            case 2:
                Exit();
        }


    }


    public static String WordFromFile() {
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
        String s = words.get(rand);
        return s;
    }
}
