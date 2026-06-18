package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFrame extends JFrame {
    private final JButton[][] buttons;
    private Board board;
    private final int height;
    private final int width;
    private final int bombNum;
    private boolean gameOver = false;
    private boolean firstOpen = true;

    //ボタンのクリック後に更新する
    private void updateButtons() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char state = board.getVisible(y, x);
                //文字の色の初期化
                buttons[y][x].setForeground(Color.BLACK);
                //ボタンに表示する文字を変更
                if (state == '□' || state == '0') {
                    buttons[y][x].setText("");
                } else if (state == 'F') {
                    buttons[y][x].setText("F");
                } else if (state == '*') {
                    buttons[y][x].setText("B");
                } else {
                    buttons[y][x].setText(String.valueOf(state));
                    //数字の色を変える
                    switch (state) {
                        case '1' -> buttons[y][x].setForeground(Color.BLUE);
                        case '2' -> buttons[y][x].setForeground(Color.GREEN);
                        case '3' -> buttons[y][x].setForeground(Color.ORANGE);
                        case '4' -> buttons[y][x].setForeground(Color.PINK);
                        case '5' -> buttons[y][x].setForeground(Color.cyan);
                        case '6' -> buttons[y][x].setForeground(Color.magenta);
                        case '7' -> buttons[y][x].setForeground(Color.RED);
                    }
                }
                //盤面を凸凹に見えるよう、色を変更
                if (state == '□' || state == 'F') {
                    buttons[y][x].setBackground(Color.LIGHT_GRAY);
                } else {
                    buttons[y][x].setBackground(Color.WHITE);
                }
                //ボタンの枠の消去
                if(state != '□' && state != 'F'){
                    buttons[y][x].setBorderPainted(false);
                }else {
                    buttons[y][x].setBorderPainted(true);
                }
            }
        }
    }

    //ボタンを開ける処理
    private void openCell(int y, int x) {

        if (firstOpen) {
            board.putBombs(bombNum, y, x);
            board.calcBombs();
            firstOpen = false;
        }

        board.openBoard(y, x);
        updateButtons();
        //負け判定
        if (board.isBomb(y, x)) {
            gameOver = true;

            board.showBombs();
            updateButtons();

            JOptionPane.showMessageDialog(
                    this,
                    "GAME OVER!"
            );
            return;
        }
        //勝ち判定
        if (board.isClear()) {
            gameOver = true;

            JOptionPane.showMessageDialog(
                    this,
                    "CLEAR!"
            );
        }
    }

    public void resetGame(){
        gameOver = false;
        firstOpen = true;
        board = new Board(height,width);
        updateButtons();
    }


    public GameFrame(int height, int width, int bombNum) {
        this.height = height;
        this.width = width;
        this.bombNum = bombNum;

        board = new Board(height, width);


        setTitle("Minesweeper");
        setSize(width* 50, height * 50 + 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(height, width));

        JButton resetButton = new JButton("RESET");
        resetButton.addActionListener(e -> {
            resetGame();
        });
        buttons = new JButton[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //座標を覚えておくため
                final int row = y;
                final int col = x;

                //ボタンの設定
                buttons[y][x] = new JButton("");
                buttons[y][x].setPreferredSize(new Dimension(50, 50));
                buttons[y][x].setMargin(new Insets(0, 0, 0, 0));
                buttons[y][x].setFont(new Font("Arial", Font.BOLD, 30));

                //マウスの左、右クリックで入力の操作を分岐
                buttons[y][x].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //ゲームオーバー後、右クリックでフラグを置けないように
                        if (gameOver) {
                            return;
                        }
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            openCell(row, col);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            board.toggleFlag(row, col);
                            updateButtons();
                        }
                    }
                });

                panel.add(buttons[y][x]);
            }
        }
        add(resetButton,BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);
        setVisible(true);
    }
}
