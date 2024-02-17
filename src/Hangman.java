

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
/*
Поменял на маленькие буквы (?)
Убрал глобальные переменные класса
Чуть поменял подход к проверке и замене символов, сделал их более прозрачными
Сделал чтобы счётчик считал не 5 - 0, а с 6 - 1, так более логично мне кажется
Добавил вариант "уже было"

Заметки от 17 февраля 2024
Добавил через так её курву мать визуализацию вешалки, вроде работает.
Нужно добавить функционал запоминания УЖЕ НАЗВАННЫХ И НЕВЕРНЫХ БУКВ, а не только названных-верных
что бы в ОБОИХ случаях система говорила уже было, а не как сейчас - если буква есть то говорит уже было
Но для этого надо создавать видимо какой то пул названных букв и проверять их, складывая туда всё
и верные и неверные...Но хер знает надо это или нет. Читать ТЗ ?
 */
public class Hangman {

    static int tryCounter = 6;

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в игру Виселица версии 1.0\n" +
                "Вам нужно угадать слово с шести попыток.\n" +
                "Каждая правильно названная буква отрывается в закрытом слове,\n" +
                "даже если их несколько одинаковых.\n" +
                "Если вы не угадали с шести попыток, игра окончена.\n");

        Scanner gameChoose = new Scanner(System.in);
        while (true) {
            System.out.println("Желаете начать новую игру (д) или выйти ?(н)");
            String gamerInput = gameChoose.nextLine();
            if (gamerInput.contains("д")) {
                tryCounter = 6;
                gameLoop();
            } else if (gamerInput.contains("н")) {
                System.out.println("Выход из игры");
                return;
            } else {
                System.out.println("Введена неверная команда, попробуйте ещё раз");
            }
        }

    }

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

