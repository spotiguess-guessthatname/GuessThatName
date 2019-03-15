package com.example.guessthatname;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

import static com.example.guessthatname.R.font.arcade_classic;

public class MainActivity extends AppCompatActivity {
private int score;

private static final String TAG = "GuessThatName";
private static final String SCORE_KEY = "currentScore";
private static final String testLink = "https://www.sageaudio.com/blog/wp-content/uploads/2014/04/album-art-300x300.png";

private TextView mScoreTV;
private ImageView mAlbumArtIV;
private Choice[] mChoices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlbumArtIV = findViewById(R.id.iv_album_art);


        ImageUtil.displayImageFromLink(mAlbumArtIV, testLink);
        mAlbumArtIV.setVisibility(View.VISIBLE);

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(SCORE_KEY)){
                score = savedInstanceState.getInt(SCORE_KEY);
            } else {
                score = 0;
            }
        }
        initChoices();

        mScoreTV = findViewById(R.id.tv_score);
        mScoreTV.setText(getString(R.string.score_pre)+" "+score);

        Typeface typeface = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            typeface = getResources().getFont(arcade_classic);
            mScoreTV.setTypeface(typeface);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(score > 0){
            outState.putInt(SCORE_KEY, score);
        }
    }

    private void updateScore(int popularity){
        score += (100-(popularity/2));
        mScoreTV.setText(getString(R.string.score_pre)+" "+score);
    }

    private void initChoices() {
        // Get buttons
        mChoices = new Choice[4];
        mChoices[0] = new Choice((Button)findViewById(R.id.button_0), false);
        mChoices[1] = new Choice((Button)findViewById(R.id.button_1), false);
        mChoices[2] = new Choice((Button)findViewById(R.id.button_2), false);
        mChoices[3] = new Choice((Button)findViewById(R.id.button_3), false);

        // TODO: Send the above statements into the for loop below

        for(int i = 0; i < 4; i++) {
            //Add a touch listener to each of the buttons
            Button b = mChoices[i].getButton();
            b.setTag(i);
            b.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    float x = event.getX();
                    float y = event.getY();
                    float height = (float)v.getHeight();
                    float width = (float)v.getWidth();

                    //Check if the touch event is within the bounds of the button layout
                    if((0 < x && x < width) && (0 < y && y < height)) {
                        if(action == MotionEvent.ACTION_DOWN) {
                            //Fade the button when pressed
                            ((ColorDrawable)v.getBackground()).setAlpha(100);
                        } else if(action == MotionEvent.ACTION_UP) {
                            //Restore original button opacity
                            ((ColorDrawable)v.getBackground()).setAlpha(255);
                            displayResults(mChoices[(Integer)v.getTag()].getCorrect());
                        }
                    } else {
                        //Restore original button opacity
                        ((ColorDrawable)v.getBackground()).setAlpha(255);
                    }

                    return true;
                }

            });
        }
    }

    /**
     * Gets a list of 4 songs and an associated boolean value indicating
     * correctness of the song choice.
     * Updates the button UI elements from argument data
     * @param songs
     */
    public void updateChoices(Pair<String, Boolean> songs[]) {
        for(int i = 0; i < 4; i++) {
            mChoices[i].updateText(songs[i].first);
            mChoices[i].setCorrectness(songs[i].second);
        }
    }

    private void displayResults(boolean correct) {
        Log.d(TAG, "Correct song? : " + correct);
        if(correct) {
            // TODO: Success view
        } else {
            // TODO: Failure view
        }
    }
}
