package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView p1, p2;
    Button reset;
    Button[][] buttons = new Button[3][3];
    int player1score = 0, player2score = 0;
    Boolean player1turn = true;
    int rounds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            rounds = savedInstanceState.getInt("rounds");
            player1score = savedInstanceState.getInt("player1score");
            player2score = savedInstanceState.getInt("player2score");
            player1turn = savedInstanceState.getBoolean("player1turn");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p1 = findViewById(R.id.player1_score);
        p2 = findViewById(R.id.player2_score);

        reset = findViewById(R.id.reset_button);

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                String btn = "button_" + i + j;
                int res = getResources().getIdentifier(btn, "id", getPackageName());
                buttons[i][j] = findViewById(res);
                buttons[i][j].setOnClickListener(this);
            }
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("player1score", player2score);
        outState.putInt("player2score", player2score);
        outState.putInt("rounds", rounds);
        outState.putBoolean("player1turn", player1turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        rounds = savedInstanceState.getInt("rounds");
        player1score = savedInstanceState.getInt("player1score");
        player2score = savedInstanceState.getInt("player2score");
        player1turn = savedInstanceState.getBoolean("player1turn");
    }

    @Override
    public void onClick(View v) {
        String text = ((Button)v).getText().toString();
        if(text.equals("")){
            if(player1turn){
                ((Button) v).setText("X");
            }

            else{
                ((Button) v).setText("O");
            }

           rounds++;
            if(rounds == 9){
                if(!hasWon()){
                    isDraw();
                }
            }

            else{
                if(!hasWon())
                player1turn = !player1turn;
            }
        }
    }

    public boolean hasWon(){
        String[][] texts = new String[3][3];

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                texts[i][j] = buttons[i][j].getText().toString();
            }
        }

        //checking row
        for(int i = 0; i < 3; ++i){
            if(texts[i][0].equals(texts[i][1]) && texts[i][1].equals(texts[i][2]) && !texts[i][0].equals("")){
                if(player1turn){
                    player1won();
                }

                else{
                    player2won();
                }
                return true;
            }
        }

        //checking column
        for(int i = 0; i < 3; ++i){
            if(texts[0][i].equals(texts[1][i]) && texts[1][i].equals(texts[2][i]) && !texts[0][i].equals("")){
                if(player1turn){
                    player1won();
                }

                else{
                    player2won();
                }
                return true;
            }
        }

        //checking left diagonal
        if(texts[0][0].equals(texts[1][1]) && texts[1][1].equals(texts[2][2]) && !texts[0][0].equals("")){
            if(player1turn){
                player1won();
            }

            else{
                player2won();
            }
            return true;
        }

        //checking right diagonal
        if(texts[0][2].equals(texts[1][1]) && texts[1][1].equals(texts[2][0]) && !texts[0][2].equals("")){
            if(player1turn){
                player1won();
            }

            else{
                player2won();
            }
            return true;
        }
        return false;
    }

    public void player1won(){
        player1score++;
        Toast.makeText(MainActivity.this,"Player 1 Won!", Toast.LENGTH_SHORT).show();
        String display = "Player 1 : " + player1score;
        p1.setText(display);
        reset();
    }

    public void player2won(){
        player2score++;
        Toast.makeText(MainActivity.this,"Player 2 Won!", Toast.LENGTH_SHORT).show();
        String display = "Player 2 : " + player2score;
        p2.setText(display);
        reset();
    }

    public void isDraw(){
            Toast.makeText(MainActivity.this,"Match Drawn!", Toast.LENGTH_SHORT).show();
            reset();
    }

    public void reset(){
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                buttons[i][j].setText("");
            }
        }
        rounds = 0;
        player1turn = true;
    }

}