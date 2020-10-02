package com.example.tugas4vsga;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private final int H = 4;
    private final int[][] BUTTON_IDN = {{R.id.tbl1, R.id.tbl2, R.id.tbl3, R.id.tbl4, R.id.tbl5, R.id.tbl6, R.id.tbl7, R.id.tbl8, R.id.tbl9, R.id.tbl10,
    R.id.tbl11, R.id.tbl12, R.id.tbl13, R.id.tbl14, R.id.tbl15, R.id.tbl15, R.id.tbl16}};
    private Button[][] button;
    private String[][] board;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = new Button[H][H];
        board = new String[H][H];
        for (int i = 0; i < H; i++){
            for (int j = 0; j < H; j++){
                button[i][j] = this.findViewById(BUTTON_IDN[i][j]);
                button[i][j].setOnClickListener(onClickListener);
            }
        }
        startGame();

    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            for (int i = 0; i < H; i++)
                for (int j = 0; j < H; j++)
                    if (v.getId() == BUTTON_IDN[i][j])
                        buttonFunction(i, j);
        }
    };
    void buttonFunction (int row, int column){
        moveCard(row, column);
    }

    void moveCard (int boardX, int boardY){
        int XKosong = -1, YKosong = -1, gap;
        for (int i = 0; i < H; i++)
            for (int j = 0; j < H; j++)
                if (board[i][j].equals("")){
                    XKosong = i;
                    YKosong = j;
                }
        if (XKosong == boardX || YKosong == boardY){
            if (! (XKosong == boardX && YKosong == boardY)) {
                if (XKosong == boardX){
                    gap = boardY - YKosong;
                    if (gap == 1 || gap == -1){
                        if (YKosong < boardY)
                            board[boardX][YKosong] = board[boardX][YKosong + 1];
                        else
                            board[boardX][YKosong] = board[boardX][YKosong - 1];

                        board[boardX] [boardY] = "";
                    }
                }
                if (YKosong == boardY){
                    gap = boardX - XKosong;
                    if (gap == 1 || gap == -1){
                        if (XKosong <boardX)
                            board[XKosong][boardY] = board[XKosong + 1][boardY];
                        else
                            board[XKosong][boardY] = board [XKosong - 1][boardY];

                        board[boardX][boardY] = "";
                    }
                }
            }
        }
        showBoard();
    }



    void  startGame(){
        String[] array = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        Random rdm = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--){
            int index = rdm.nextInt(i + 1);

            String temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        int index = 0;
        for (int i = 0; i < H; i++){
            for (int j = 0; j < H; j++){
                if (index == array.length){
                    button[i][j].setText("");
                    board[i][j] = "";
                    break;
                }
                button[i][j].setText(array[index]);
                board[i][j] = array[index];
                index++;
            }
        }
        checkFinish();
    }
    void showBoard(){
        for (int i = 0; i < H; i++){
            for (int j = 0; j < H; j++){
                button[i][j].setText(board[i][j]);
            }
        }
        checkFinish();
    }

    void checkFinish(){
        String[] checker = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        int index = 0;
        boolean win = false;

        outerloop:
        for (int i = 0; i < H; i++){
            for (int j = 0; j < H; j++){
                if (index == checker.length){
                    win = true;
                    break  outerloop;
                }
                if (!board[i][j].equals(checker[index])){
                    break outerloop;
                }
                index++;
            }
        }
        if (win){
            Toast.makeText(MainActivity.this, "You win", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refresh){
            startGame();
        } else if (item.getItemId() == R.id.logout){
            finishAndRemoveTask();
        }
        return true;
    }
}
