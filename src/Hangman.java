

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

/**
 * @author El Baron 10.02.2024
 */
//Todo Переделать функционал с массивов на коллекции
//Todo Реализовать проверку символов на повторный ввод (тех которых нет в искомом слове)




public class Hangman {

    static int tryCounter = 6;

    /**
     * Основная логика игры, в которой вызываются и работают все нужные методы.
     * Я буду писать подробные комментарии в первую очередь для себя, не взыщите кто читает - я учусь
     * Обновление от 3 мая 2024
     */
    public static void main(String[] args) {
        System.out.println("""
                Добро пожаловать в игру Виселица версии 1.0
                Вам нужно угадать слово с шести попыток.
                Каждая правильно названная буква отрывается в закрытом слове,
                даже если их несколько одинаковых.
                Если вы не угадали с шести попыток, игра окончена.\n""");

        Scanner gameChoose = new Scanner(System.in);//Создание объекта класса Сканнер
        String gamerInput;
        do {
            tryCounter = 6;
            gameLoop();
            System.out.println("Желаете начать новую игру(д) или выйти(н) ? ");
            gamerInput = gameChoose.nextLine();

        } while (gamerInput.contains("д"));// От 37 по 43 строки цикл Do-While, это одно целое

//        while (true) {
//            System.out.println("Новая игра (д) или выход ?(н)");
//            String gamerInput = gameChoose.nextLine();
//            if (gamerInput.contains("д")) {
//                tryCounter = 6;
//                gameLoop();
//            } else if (gamerInput.contains("н")) {
//                System.out.println("Выход из игры");
//                return;
//            } else {
//                System.out.println("Введена неверная команда, попробуйте ещё раз");
//            }
//        }

    }

    /**
     * Основной метод игры.
     */
    public static void gameLoop() {

        String word = getRandomWord(getAllWords());

        String hiddenWord = getHiddenWord(word);

        Scanner sc = new Scanner(System.in);

        System.out.println(hiddenWord);


        while (!hiddenWord.equals(word)) {
            System.out.println("Введите букву");
            char input = sc.next().charAt(0);
            if (hiddenWord.contains(String.valueOf(input))) {
                System.out.println("Уже было!");
            } else if (word.contains(String.valueOf(input))) {
                hiddenWord = getWordWithInsertedLetter(input, word, hiddenWord);
            } else {
                tryCounter--;
                System.out.println("Такой буквы нет !");
                hangmanDrow();
                if (tryCounter == 0) {
                    System.out.println("У вас кончились попытки, игра окончена ! " +
                            "Было загадано слово: " + word + "\n");
                    break;
                }
                System.out.println("Осталось " + tryCounter + " попыток !");
            }
            System.out.println(hiddenWord);
        }
        if (hiddenWord.equals(word)) {
            System.out.println("Вы выиграли !");
        }


    }

    /**
     * Метод getHiddenWord
     * @param word
     *
     */
    public static String getHiddenWord(String word) {
        String result = "*".repeat(word.length());
        return result;
    }

    public static String getWordWithInsertedLetter(char input, String word, String hiddenWord) {
        char[] chars = hiddenWord.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == input) {
                chars[i] = input;
            }
        }
        return new String(chars);
    }

    public static String[] getAllWords() {
        Path path = Paths.get("src", "words.txt");
        String[] wordsArray = new String[100];
        BufferedReader bf;
        try {
            bf = Files.newBufferedReader(path);
            String word;
            int i = 0;
            while ((word = bf.readLine()) != null) {
                wordsArray[i] = word.toLowerCase();
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wordsArray;

    }

    public static String getRandomWord(String[] wordsArray) {
        Random random = new Random();
        return wordsArray[random.nextInt(wordsArray.length)];
    }

    public static void hangmanDrow() {
        switch (tryCounter) {
            case 5:
                System.out.println("|\n|\n|\n|\n|\n|\n|\n");
                System.out.println();
                break;
            case 4:
                System.out.println(" ---");
                System.out.println("|/  |");
                System.out.println("|\n|\n|\n|\n|\n|\n");
                System.out.println();
                break;
            case 3:
                System.out.println(" ---");
                System.out.println("|/  |");
                System.out.println("|   0");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|\n");
                System.out.println();
                break;
            case 2:
                System.out.println(" ---");
                System.out.println("|/  |");
                System.out.println("|   0");
                System.out.println("|  /|\\");
                System.out.println("|");
                System.out.println("|\n");
                System.out.println();
                break;
            case 1:
                System.out.println(" ---");
                System.out.println("|/  |");
                System.out.println("|   0");
                System.out.println("|  /|\\");
                System.out.println("|  / \\");
                System.out.println("  -----");
                System.out.println("  |   |");
                System.out.println();
                break;
            case 0:
                System.out.println(" ---");
                System.out.println("|/  |");
                System.out.println("|   @");
                System.out.println("|  /|\\");
                System.out.println("|  / \\");
                System.out.println("|\n");
                break;
        }
    }
}

