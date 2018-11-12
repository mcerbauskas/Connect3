package com.mcerbauskas.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // kas pirmas prades: 0 - raudonas, 1 - melynas
    int activePlayer = 0;

    //whether the game is active or not
    boolean gameIsActive = true;

    // 2 reiskia, kad langelyje nieko nera
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[] [] winningPostitions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6} };

       // paspaudus ant tuscios vietos iskvieciamas dropIn metodas
    public void dropIn (View view) {

        ImageView counter = (ImageView) view; //nereikia nurodyti resurso nes tai tiesiog tuscias plotas

        int tappedCounter = Integer.parseInt(counter.getTag().toString()); //first convert to string, then to integer, counteris tagui gaudyti

        if (gameState[tappedCounter] == 2 && gameIsActive) { //nereikia else statement'o ,nes norime vykdyti visa tai tik jei gameState yra 2, jei jis 1 arba 0 - reiskia laukelis jau buvo spaustas

            gameState[tappedCounter] = activePlayer;

            //pirmiausiai nustumiame paveiksleli i ekrano virsu
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {

                //nustatome, kad paveikslelis butu raudonas iksas
                counter.setImageResource(R.drawable.redx);
                activePlayer = 1; // dabar bus melynojo eile

            } else {
                //nustatome, kad paveikslelis butu raudonas iksas
                counter.setImageResource(R.drawable.bluex);
                activePlayer = 0; // dabar bus raudonojo eile
            }

            //graziname atgal paveiksleli is virsaus
            counter.animate().translationYBy(1000f).setDuration(300);
        }

        //looping through winning positions and checking if any of them in the game state have the same value, tarkim jei pirmos trys pozicijos vien vienetai - laimejo melyni

        for (int[] winningPosition : winningPostitions) {
            //pirma, antra, trecia pozicija (, kitaip tariant, ar visos trys pozicijos gamestate'e vienodos
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {      //nes jeigu 2 reiskia niekas tame laukelyje dar nezaide

                //when someone wins, game is not active and you can't continue playing
                gameIsActive = false;
                //by default laimi melynieji
                String winner = "melynieji";

                if (gameState[winningPosition[0]] == 0) {

                    winner = "raudonieji";

                }

                //when someone has won

                TextView winnerMessage = (TextView)findViewById(R.id.winnerMessage);
                winnerMessage.setText(winner +" laimėjo");

                LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);

                //if no one has won (looping throuh gameState array and checking to see if everything is not 2)
            } else {
                //temporary boolean
                boolean gameIsOver = true;

                for (int counterState : gameState) {
                    if (counterState == 2) gameIsOver = false; //if one of them equal to 2 then game is not over yet
                }
                if (gameIsOver) {
                    TextView winnerMessage = (TextView)findViewById(R.id.winnerMessage);
                    winnerMessage.setText("Lygiosios");

                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {

        gameIsActive = true;

        //pradanginame layoutview
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        //setting gameState and player back to defaults
        activePlayer = 0;

        // we have to update each item in the array separately, because we cant set the gameState array to another array (like gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};)
        for (int i = 0; i < gameState.length; i++) {

            gameState[i] = 2;
        }

        //dabar loopiname per visus image'us kad jie neturetu jokio source

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) { //getChildCount tells us how many views there are inside GridLayout
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0); //gauname i-taji child'a, apskliaudziame viska ir set'iname 0
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
