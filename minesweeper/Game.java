package minesweeper;

import java.util.Scanner;

public class Game {
    private Scanner sc;
    private Board board;
    private  boolean gameOver;

    public void start(){
        System.out.print("爆弾の個数を選択してください。: ");
        int bombNum = sc.nextInt();

        Board board = new Board(9,9 );
        board.putBombs(bombNum);
        board.calcBombs();

        gameLoop();
    }

    public void gameLoop(){
        while(!gameOver) {
            board.printVisible();

            int openY = sc.nextInt();
            int openX = sc.nextInt();

            board.openBoard(openY, openX);
        }
    }
}
