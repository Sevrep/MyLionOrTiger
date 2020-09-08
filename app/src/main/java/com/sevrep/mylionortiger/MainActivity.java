package com.sevrep.mylionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    enum Player {
        ONE, TWO, NO
    }

    Player currentPlayer = Player.ONE;

    Player[] playerChoices = new Player[9];

    int[][] winnerRowsColumns = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    private boolean gameOver = false;
    private Button buttonReset;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        int playerIndex = 0;
        while(playerIndex < 9) {
            playerChoices[playerIndex] = Player.NO;
            playerIndex++;
        }

    }

    private void resetGame() {
        buttonReset.setVisibility(View.GONE);

        for (int index = 0; index < gridLayout.getChildCount(); index++) {

            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);

            playerChoices[index] = Player.NO;

        }

        currentPlayer = Player.ONE;
        gameOver = false;
    }


    public void imageViewIsTapped(View view) {

        ImageView tappedImage = (ImageView) view;

        int tiTag = Integer.parseInt(tappedImage.getTag().toString());

        if(playerChoices[tiTag] == Player.NO && !gameOver) {

            tappedImage.setTranslationX(-2000);

            playerChoices[tiTag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                tappedImage.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            } else {
                tappedImage.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }

            tappedImage.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);

            Toast.makeText(this, tappedImage.getTag().toString(), Toast.LENGTH_SHORT).show();

            for (int[] winnerColumns : winnerRowsColumns) {
                if (
                        (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]]) &&
                                (playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]]) &&
                                (playerChoices[winnerColumns[0]] != Player.NO)
                ) {

                    gameOver = true;

                    buttonReset.setVisibility(View.VISIBLE);

                    String winnerOfGame;
                    if (currentPlayer == Player.ONE) {
                        winnerOfGame = "Tiger";
                    } else {
                        winnerOfGame = "Lion";
                    }

                    Toast.makeText(this, winnerOfGame + " is the winner!", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}