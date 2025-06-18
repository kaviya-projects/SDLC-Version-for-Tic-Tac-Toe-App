package com.example.xox_game;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xox_game.R;

public class MainActivity extends AppCompatActivity {

    private String currentPlayer = "X";
    private String[][] board = new String[3][3];
    private Button[][] buttons = new Button[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                buttons[i][j] = (Button) gridLayout.getChildAt(index);
                buttons[i][j].setOnClickListener(new CellClickListener(i, j));
            }
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> resetGame());
    }

    private class CellClickListener implements View.OnClickListener {
        int row, col;

        CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            if (board[row][col] == null) {
                board[row][col] = currentPlayer;
                ((Button) v).setText(currentPlayer);
                if (checkWin()) {
                    Toast.makeText(MainActivity.this, currentPlayer + " wins!", Toast.LENGTH_SHORT).show();
                    disableButtons();
                } else if (isBoardFull()) {
                    Toast.makeText(MainActivity.this, "It's a draw!", Toast.LENGTH_SHORT).show();
                } else {
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                }
            }
        }
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) ||
                    (board[0][i] != null && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i]))) {
                return true;
            }
        }
        return (board[0][0] != null && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) ||
                (board[0][2] != null && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]));
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) return false;
            }
        }
        return true;
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        currentPlayer = "X";
        board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }
}
