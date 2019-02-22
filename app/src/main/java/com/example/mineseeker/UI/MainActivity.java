package com.example.mineseeker.UI;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.mineseeker.MineseekerLogic.Game;
import com.example.mineseeker.R;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_ROWs = 9;
    private static final int NUM_COLS = 9;
    private static final int BOMB_COUNT = 10;

    Game game;
    int UIClicks;
    int maxClickCount;
    int totalBombCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        populateButtons();
    }

    private void init() {
        game = new Game(NUM_ROWs, NUM_COLS, BOMB_COUNT);
        maxClickCount = game.maxClickCounts();
        UIClicks = 0;
        totalBombCount = 0;
        game.start();
    }

    private void populateButtons() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.minefield);
        for(int i = 0; i < NUM_ROWs; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            tableLayout.addView(tableRow);
            for(int j = 0; j < NUM_COLS; j++) {
                final Button button = new Button(this);
                button.setWidth(7);
                button.setHeight(7);
                button.setBackgroundResource(R.drawable.button_normal);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                final int finalI = i; // row
                final int finalJ = j; // col

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // reveal cell
                        if (revealCell(finalI, finalJ)) {

                            // bomb reveal
                            button.setText("!");
                        } else {
                            button.setText(game.getNearestMine(finalI, finalJ));
                        }

                        // losing condition
                        if(UIClicks >= maxClickCount) {
                            Toast.makeText(MainActivity.this, "You're a LOSER!", Toast.LENGTH_SHORT).show();
                            game.start();

                            // re-set the board
                        }

                        // winning condition
                        if(totalBombCount == BOMB_COUNT) {
                            Toast.makeText(MainActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
                            game.start();

                            // re-set the board
                        }
                    }
                });
                tableRow.addView(button);
            }
        }
    }

    private boolean revealCell(int i, int j) {
        return game.checkIsBomb(i, j);
    }
}
