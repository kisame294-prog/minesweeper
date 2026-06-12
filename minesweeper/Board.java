package minesweeper;

import java.util.Random;

public class Board {

    private final int[][] board;
    private final char[][] visible;

    public Board(int sizeY, int sizeX){
        board = new int[sizeY][sizeX];
        visible = new char[sizeY][sizeX];
        //コンストラクタ内で初期化
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                visible[y][x] = '□';
            }
        }
    }

    public  void putBombs(int bombNum){
        var rand = new Random();
        int count = 0;
        while (count < bombNum){
            int y = rand.nextInt(board.length);
            int x = rand.nextInt(board[0].length);
            //ランダムに置いた爆弾の座標が被らないように
            if(board[y][x] != 9){
                board[y][x] = 9;
                count ++;
            }
        }

    }

    public void calcBombs(){
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                //爆弾を見つけたら
                if(board[y][x] == 9){
                    //周囲8マスを見る
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            int ny = y + dy;
                            int nx = x + dx;
                            //範囲内か確認
                            if(ny >= 0 && ny < board.length &&
                                    nx >= 0 && nx < board[0].length){
                                //爆弾じゃなければ+1
                                if(board[ny][nx] != 9){
                                    board[ny][nx]++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void setBoard(){
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public void openBoard(int y, int x){
        int value = board[y][x];
        if(value == 9){
            visible[y][x] = '*';
        }else{
            visible[y][x] = (char)('0' + value);
        }
    }
    public void printVisible(){
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                System.out.print(visible[y][x] + " ");
            }
            System.out.println();
        }
    }

    public void isBomb(int y, int x){
    }

    public void isNum(){

    }
}
