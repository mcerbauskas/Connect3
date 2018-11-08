package com.mcerbauskas.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // kas pirmas prades: 0 - raudonas, 1 - melynas
    int activePlayer = 0;

    // 2 reiskia, kad langelyje nieko nera
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[] [] winningPostitions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6} };

       // paspaudus ant tuscios vietos iskvieciamas dropIn metodas
    public void dropIn (View view) {

        ImageView counter = (ImageView) view; //nereikia nurodyti resurso nes tai tiesiog tuscias plotas

        int tappedCounter = Integer.parseInt(counter.getTag().toString()); //first convert to string, then to integer, counteris tagui gaudyti

        if (gameState[tappedCounter] == 2) { //nereikia else statement'o ,nes norime vykdyti visa tai tik jei gameState yra 2, jei jis 1 arba 0 - reiskia laukelis jau buvo spaustas

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

                System.out.println(gameState[winningPosition[0]]);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
