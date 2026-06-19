package minesweeper;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        String[]options = {"初級","中級","上級"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "難易度を選択してね♪",
                "Minesweeper",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice){
            case 0 -> new GameFrame(9,9,10);
            case 1 -> new GameFrame(16,16,50);
            case 2 -> new GameFrame(16,32,100);
        }
    }
}