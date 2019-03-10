package ru.example;


import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Game {
    public static void main(String[] args) {
        System.out.println("Привет, ты зашел в игру Быки и Коровы!");
        StartScrin();
    }

    static void StartScrin(){
        System.out.println("Выбери что ты хочешь сделать:");
        System.out.println("1 - Начать игру");
        System.out.println("2 - Правила игры");
        System.out.println("3 - Выход");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        switch (num){
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

    static void Exit(){
        System.out.println("Спасибо, что поиграл!");
        System.exit(0);

    }

    static void Rules(){
        System.out.println("Компьетер задумывает слово и объявляет длину слова заранее.");
        System.out.println("Участник предлагают свои варианты слов, а компьютер им отвечает в таком виде (допустим):\n 2б1к - это значит (два быка и одна корова), т.е. две буквы угаданы и стоят на своем месте, одна угадана, но стоит не на своем месте.");
        System.out.println("На угадывание слова дается 5 попыток");
        Scanner in = new Scanner(System.in);
        System.out.println("Для продолжения нажми Enter...");
        String num = in.nextLine();
        StartScrin();

    }

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    static void Game(){
        System.out.println("Игра началась");
        String game_word = WordFromFile();
        int lenght = game_word.length();
        System.out.println(game_word);
        System.out.println("Слово состоит из " + game_word.length() + " букв");
        for(int round=1;round<=5;round++) {
            System.out.println("\n\nРаунд " + round);
            System.out.println("Введи слово:");
            Scanner in = new Scanner(System.in);
            String num = in.nextLine();
            int Bulls = 0;
            int Cows = 0;
            for (int i = 0; i < game_word.length(); i++) {
                if (game_word.charAt(i) == num.charAt(i)) {
                    Bulls++;
                }
            }
            System.out.println(game_word);
            for (int i = 0; i < game_word.length(); i++)
                for (int j = 0; j < num.length(); j++)
                    if (game_word.charAt(i) == num.charAt(j) && i != j){
                        Cows++;
                    }

            System.out.println("Быков - " + Bulls);
            System.out.println("Коров - " + Cows);
            if(Bulls == lenght){
                System.out.println("Ты выйграл! Поздравляю!!!!");
                EndGame();
            }

        }
        System.out.println("Ты проиграл! Повезет в другой раз!");
        EndGame();
    }

    static void EndGame(){
        System.out.println("Если хочешь сыргать еще жми 1, если хочешь выйти - 2");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        switch (num){
            case 1:
                Game();
                break;
            case 2:
                Exit();
        }


    }


    public static String WordFromFile() {
        List<String> words = new LinkedList<>();
        int rand=0;
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
