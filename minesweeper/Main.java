package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var in = new Scanner(System.in);

        //個数を選択して爆弾配置
        System.out.print("爆弾の個数を選択してください。: ");
        int bombNum = in.nextInt();

        Board board = new Board(9,9 );
        board.putBombs(bombNum);
        board.calcBombs();
        //board.setBoard();
        //プレイヤーから盤面をみえないように
        board.printVisible();
        //クリックして座標を開けれるようにしたい...
        int openY = in.nextInt();
        int openX = in.nextInt();
        board.openBoard(openY - 1,openX - 1);
        board.printVisible();
    }
}
