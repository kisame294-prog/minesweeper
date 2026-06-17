package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFrame extends JFrame {
    private final JButton[][] buttons;
    private final Board board;
    private boolean gameOver = false;
    private boolean firstOpen = true;

    //ボタンのクリック後に更新する
    private void updateButtons() {

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                char state = board.getVisible(y, x);

                if (state == '□' || state == '0') {
                    buttons[y][x].setText("");
                } else if (state == 'F'){
                    buttons[y][x].setText("F");
                } else if (state == '*'){
                    buttons[y][x].setText("B");
                } else {
                    buttons[y][x].setText(String.valueOf(state));
                }

                if (gameOver){
                    buttons[y][x].setEnabled(false);
                } else if (state == '□' || state == 'F'){
                    buttons[y][x].setEnabled(true);
                }else{
                    buttons[y][x].setEnabled(false);
                }
            }
        }
    }

    //クリックした場所の座標を取得し同期していく
    private void openCell(int y, int x) {

        if (firstOpen) {
            board.putBombs(10, y, x);
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

    public GameFrame() {

        board = new Board(9, 9);

        setTitle("Minesweeper");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 9));

        buttons = new JButton[9][9];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                //座標を覚えておくため
                final int row = y;
                final int col = x;

                //ボタンの設定
                buttons[y][x] = new JButton("□");
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

        add(panel);
        setVisible(true);
    }
}
