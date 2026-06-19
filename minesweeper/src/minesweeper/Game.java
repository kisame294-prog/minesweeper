package minesweeper;

import java.util.Scanner;

public class Game {
    public Game() {
        sc = new Scanner(System.in);
    }

    private final Scanner sc;
    private Board board;
    private int bombNum;
    private boolean gameOver = false;
    private boolean firstOpen = true;

    private int inputNumber(String message) {
        while (true) {
            System.out.println(message);

            if (!sc.hasNextInt()) {
                System.out.print("数字を入力してください: ");
                sc.next();
                continue;
            }
            return sc.nextInt();
        }
    }

    public void start() {
        System.out.print("爆弾の個数を選択してください: ");
        bombNum = sc.nextInt();

        System.out.print("盤面の縦の長さを入力してください: ");
        int boardY = sc.nextInt();
        System.out.print("盤面の横の長さを入力してください: ");
        int boardX = sc.nextInt();

        board = new Board(boardY, boardX);

        gameLoop();
    }

    public void gameLoop() {
        while (!gameOver) {
            board.printVisible();

            System.out.println("""
                    操作を選択してください:
                    o(盤面を開く)
                    f(盤面にフラグを置く)""");
            char action = sc.next().charAt(0);
            //oとfだけの入力を受け付けるようにする
            if (action != 'o' && action != 'f') {
                System.out.println("oかfを入力してください");
                continue;
            }

            int openY = inputNumber("Yの座標を入力してください: ") - 1;
            int openX = inputNumber("Xの座標を入力してください") - 1;
            //盤面の要素数以上の数を除く
            if (openY < 0 || openY >= board.getHeight() ||
                    openX < 0 || openX >= board.getWidth()) {
                System.out.println("範囲外です");
                continue;
            }

            if (action == 'o') {
                if (firstOpen) {
                    board.putBombs(bombNum, openY, openX);
                    board.calcBombs();
                    firstOpen = false;
                }

                board.openBoard(openY, openX);

                if (board.isBomb(openY, openX)) {
                    System.out.println("ゲームオーバー!");
                    board.printVisible();
                    gameOver = true;
                } else if (board.isClear()) {
                    System.out.println("クリア!");
                    gameOver = true;
                }

            } else {
                board.toggleFlag(openY, openX);
            }
        }
    }
}