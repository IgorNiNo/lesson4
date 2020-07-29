package ru.geekbrains.javaCore.baseLevel;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int SIZE = 5;
    public static int DOTS_TO_WIN = 4;
    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static char[][] map;
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Вы победили!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Победил Искуственный Интеллект");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра закончена");
    }


    public static boolean checkWin(char symb) {
        int countSymb;
        int l;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if( (j + DOTS_TO_WIN) <= SIZE ) {   //проверка горизонтальных линий
                    countSymb = 0;
                    for (l = j; l < (j + DOTS_TO_WIN); l++) {
                        if(map[i][l] == symb) {
                            countSymb += 1;
                        } else {
                            countSymb = 0;
                        }
                        if(countSymb >= DOTS_TO_WIN) {
                            return true;
                        }
                    }
                }
                if( (i + DOTS_TO_WIN) <= SIZE ) {   //проверка вертикальных линий
                    countSymb = 0;
                    for (l = i; l < (i + DOTS_TO_WIN); l++) {
                        if(map[l][j] == symb) {
                            countSymb += 1;
                        } else {
                            countSymb = 0;
                        }
                        if(countSymb >= DOTS_TO_WIN) {
                            return true;
                        }
                    }
                }
                if( (DOTS_TO_WIN - i) < 2 && (DOTS_TO_WIN - j) > 2 ) {   //проверка растущей диагонали
                    countSymb = 0;
                    for (l = 0; l < DOTS_TO_WIN; l++) {
                        if(map[i-l][j+l] == symb) {
                            countSymb += 1;
                        } else {
                            countSymb = 0;
                        }
                        if(countSymb >= DOTS_TO_WIN) {
                            return true;
                        }
                    }
                }
                if( (i + DOTS_TO_WIN) <= SIZE && (j + DOTS_TO_WIN) <= SIZE ) {   //проверка падающей диагонали
                    countSymb = 0;
                    for (l = 0; l < DOTS_TO_WIN; l++) {
                        if(map[i+l][j+l] == symb) {
                            countSymb += 1;
                        } else {
                            countSymb = 0;
                        }
                        if(countSymb >= DOTS_TO_WIN) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    public static void aiTurn() {
        int x, y;
        for (int i = 0; i < SIZE; i++) {    //цикл поиска победного варианта для искусственного интелекта
            for (int j = 0; j < SIZE; j++) {
                if(map[i][j] == DOT_EMPTY) {
                    map[i][j] = DOT_O;
                    if( checkWin(DOT_O) ) {
                        System.out.println("Компьютер сделал ход в точку " + (j + 1) + " " + (i + 1));
                        return;
                    } else {
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }
        for (int i = 0; i < SIZE; i++) {    //цикл поиска варианта блокировки победы человека
            for (int j = 0; j < SIZE; j++) {
                if(map[i][j] == DOT_EMPTY) {
                    map[i][j] = DOT_X;
                    if( checkWin(DOT_X) ) {
                        map[i][j] = DOT_O;
                        System.out.println("Компьютер сделал ход в точку " + (j + 1) + " " + (i + 1));
                        return;
                    } else {
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }
        do {    //если нет вариантов, то ходим по рандому
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (!isCellValid(x, y));
        System.out.println("Компьютер сделал ход в точку " + (x + 1) + " " + (y + 1));
        map[y][x] = DOT_O;
    }

    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты в формате X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y)); // while(isCellValid(x, y) == false)
        map[y][x] = DOT_X;
    }

    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        if (map[y][x] == DOT_EMPTY) return true;
        return false;
    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        System.out.print("   ");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print(i + " ");
        }

        System.out.print("\n |");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print("——");
        }
        System.out.println("> X");

        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + "|" + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        //System.out.println(" ˅\n Y");
        System.out.println(" Y");
    }


}
