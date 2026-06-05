package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Cells {
    public static void main(String[] args) {
        int[][] board = new int[9][9];

        //爆弾配置
        var in = new Scanner(System.in);
        System.out.print("爆弾の個数を選択してください。: ");
        int bombNum = in.nextInt();
        var rand = new Random();
        for (int i = 1; i <= bombNum; i++) {
            int y = rand.nextInt(9);
            int x = rand.nextInt(9);
            board[x][y] = 9;
        }

        //9×9盤面を表示
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}
