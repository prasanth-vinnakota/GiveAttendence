package com.prasanth.attendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];

    private boolean playerOneTurn = true;

    private int count = 0;
    private int playerOnePoints;
    private int playerTwoPoints;

    private TextView mPlayerOnePoints;
    private TextView mPlayerTwoPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        //initialize TextView
        mPlayerOnePoints = findViewById(R.id.player1);
        mPlayerTwoPoints = findViewById(R.id.player2);

        //initialize Button
        Button mReset = findViewById(R.id.reset);
        Button mExit = findViewById(R.id.exit);

        //run loop for 2-D array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                //assign button id to String object
                String buttonId = "button" + i + j;

                //get Button id
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());

                //initialize Button
                buttons[i][j] = findViewById(resId);

                //set OnClickListener for Button
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //if Button text is not available
                        if (!((Button) v).getText().toString().equals(""))
                            return;

                        //player 1 turn
                        if (playerOneTurn) {

                            //set Button text as 'X'
                            ((Button) v).setText("X");
                        }
                        //player 2 turn
                        else {

                            //set Button text as 'X'
                            ((Button) v).setText("O");
                        }

                        //increase total turns
                        count++;

                        //someone is winner
                        if (checkForWinner()) {

                            //player 1 turn
                            if (playerOneTurn) {

                                //method call
                                playerOneWon();
                            }
                            //player 2 turn
                            else {

                                //method call
                                playerTwoWon();
                            }
                        }
                        //draw match
                        else if (count == 9) {

                            //method call
                            draw();
                        }
                        //no winner
                        else {

                            //invert turn
                            playerOneTurn = !playerOneTurn;
                        }
                    }

                    private boolean checkForWinner() {

                        //initialize 2-D String array
                        String[][] field = new String[3][3];

                        //run loop for 2-D array
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {

                                //assign String object with Button's text
                                field[i][j] = buttons[i][j].getText().toString();
                            }
                        }

                        //checking rows
                        for (int i = 0; i < 3; i++) {

                            //row has similar character
                            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                                return true;
                            }
                        }

                        //checking columns
                        for (int i = 0; i < 3; i++) {

                            //columns has similar character
                            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                                return true;
                            }
                        }

                        //left diagonal has similar character
                        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
                            return true;
                        }

                        //return true if right diagonal has similar character else return false
                        return field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("");
                    }
                });
            }
        }

        //set OnClickListener to Button
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //method call
                resetGame();
            }
        });

        //set OnClickListener to Button
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start MainActivity
                startActivity(new Intent(TicTacToeActivity.this, MainActivity.class));

                //finish current activity
                finish();
            }
        });
    }

    private void playerTwoWon() {

        //increase player 2 points
        playerTwoPoints++;

        //show message
        Toast.makeText(this, "Player 2 Won!", Toast.LENGTH_SHORT).show();

        //method call
        updatePoints();

        //method call
        reset();
    }

    private void draw() {

        //show message
        Toast.makeText(this, "Draw Match!", Toast.LENGTH_SHORT).show();

        //method call
        reset();
    }

    private void playerOneWon() {

        //increase player 1 points
        playerOnePoints++;

        //show message
        Toast.makeText(this, "Player 1 Won!", Toast.LENGTH_SHORT).show();

        //method call
        updatePoints();

        //method call
        reset();
    }

    private void reset() {

        //run loop for 2-D array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                //remove text for Buttons
                buttons[i][j].setText("");
            }
        }

        //set total to 0
        count = 0;

        //player 1 turn
        playerOneTurn = true;
    }

    private void updatePoints() {

        //assign String objects with player's points
        String playerOne = "Player 1 : " + playerOnePoints;
        String playerTwo = "Player 2 : " + playerTwoPoints;

        //set player's points to TextView
        mPlayerOnePoints.setText(playerOne);
        mPlayerTwoPoints.setText(playerTwo);
    }

    private void resetGame() {

        //set player's points to 0
        playerOnePoints = 0;
        playerTwoPoints = 0;

        //method call
        updatePoints();

        //method call
        reset();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //set variables to Bundle object
        outState.putInt("count", count);
        outState.putInt("playerOnePoints", playerOnePoints);
        outState.putInt("playerTwoPoints", playerTwoPoints);
        outState.putBoolean("playerOneTurn", playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //get variables from Bundle object
        count = savedInstanceState.getInt("count");
        playerOnePoints = savedInstanceState.getInt("playerOnePoints");
        playerTwoPoints = savedInstanceState.getInt("playerTwoPoints");
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn");
    }

    //this method will closes Application when back  is pressed
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //create a AlertDialog.Builder object
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //set builder title
        builder.setTitle("Credits");

        //set builder message
        builder.setMessage("App Credits : Prasanth Vinnakota \nIcon Credits www.flaticon.com");

        //set builder title
        builder.setIcon(R.mipmap.credits);

        //set builder Button
        builder.setPositiveButton("Gotcha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //build builder
        AlertDialog dialog = builder.create();

        //show dialog
        dialog.show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu from resources
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
