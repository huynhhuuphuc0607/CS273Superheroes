package edu.orangecoastcollege.cs273.phuynh101.cs273superheroes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;;

public class QuizActivity extends AppCompatActivity {
    private static final int SUPERHEROES_IN_QUIZ = 10;
    public static final String TAG = QuizActivity.class.getSimpleName();

    private Button[] mButtons = new Button[4];
    private List<Superhero> mAllSuperheroesList;
    private List<Superhero> mSuperheroList;
    private Superhero mCorrectSuperhero;
    private int mTotalGuesses;
    private int mCorrectGuesses;
    private SecureRandom rng;
    private Handler mHandler;

    private TextView mQuestionNumberTextView;
    private ImageView mSuperheroImageView;
    private TextView mAnswerTextView;

    private String mQuizType;
    private List<Superhero> mFilteredSuperheroesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mSuperheroList = new ArrayList<>(SUPERHEROES_IN_QUIZ);
        rng = new SecureRandom();
        mHandler = new Handler();

        mQuestionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
        mSuperheroImageView = (ImageView) findViewById(R.id.superheroImageView);
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);

        mButtons[0] = (Button) findViewById(R.id.button1);
        mButtons[1] = (Button) findViewById(R.id.button2);
        mButtons[2] = (Button) findViewById(R.id.button3);
        mButtons[3] = (Button) findViewById(R.id.button4);

        mQuestionNumberTextView.setText(getString(R.string.question, 1, SUPERHEROES_IN_QUIZ));
        try {
            mAllSuperheroesList = JSONLoader.loadJSONFromAsset(this);
        } catch (IOException e) {
            Log.e(TAG, "Error loading JSON file");
        }
        resetQuiz();
    }

    public void resetQuiz() {
        mTotalGuesses = 0;
        mCorrectGuesses = 0;

        mSuperheroList.clear();

        int size = mAllSuperheroesList.size();
        int randomPosition;
        Superhero randomSuperhero;

        while(mSuperheroList.size() < SUPERHEROES_IN_QUIZ)
        {
            randomPosition = rng.nextInt(size);
            randomSuperhero = mAllSuperheroesList.get(randomPosition);
            if(!mSuperheroList.contains(randomSuperhero))
                mSuperheroList.add(randomSuperhero);
        }

        loadNextSuperheroAvatar();
    }

    private void loadNextSuperheroAvatar() {
        mCorrectSuperhero = mSuperheroList.remove(0);

        mAnswerTextView.setText("");

        int questionNumber = SUPERHEROES_IN_QUIZ - mSuperheroList.size();
        mQuestionNumberTextView.setText(getString(R.string.question,
                questionNumber, SUPERHEROES_IN_QUIZ));

        AssetManager am = getAssets();

        try {
            InputStream iStream = am.open(mCorrectSuperhero.getUsername() + ".png");
            Drawable mDrawable = Drawable.createFromStream(iStream, mCorrectSuperhero.getName());
            mSuperheroImageView.setImageDrawable(mDrawable);

        } catch (IOException e) {
            Log.e(TAG, "Error loading the image of " + mCorrectSuperhero.getName());
        }

        do {
            Collections.shuffle(mAllSuperheroesList);
        } while(mAllSuperheroesList.subList(0, mButtons.length).contains(mCorrectSuperhero));

        for(int i = 0; i < mButtons.length; i++)
        {
            mButtons[i].setText(mAllSuperheroesList.get(i).getName());
            mButtons[i].setEnabled(true);
        }

        mButtons[rng.nextInt(mButtons.length)].setText(mCorrectSuperhero.getName());

    }

    public void makeGuess(View v)
    {
        mTotalGuesses++;

        Button clickedButton = (Button)v;

        String guess = clickedButton.getText().toString();

        if(guess.equals(mCorrectSuperhero.getName()))
        {
            for(Button b : mButtons)
                b.setEnabled(false);

            mCorrectGuesses++;
            mAnswerTextView.setTextColor(ContextCompat.getColor(this,R.color.correct_answer));
            mAnswerTextView.setText(mCorrectSuperhero.getName() + "!");

            if(mCorrectGuesses < SUPERHEROES_IN_QUIZ)
            {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextSuperheroAvatar();
                    }
                }, 2000);
            }else
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                mBuilder.setMessage(getString(R.string.results, mTotalGuesses, (double)mCorrectGuesses*100/mTotalGuesses))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.reset_quiz), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                resetQuiz();
                            }
                        }).create().show();
            }
        }else
        {
            mAnswerTextView.setTextColor(ContextCompat.getColor(this,R.color.incorrect_answer));
            mAnswerTextView.setText(getString(R.string.incorrect_answer));
            clickedButton.setEnabled(false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIntent = new Intent(this, SettingsActivity.class);
        startActivity(mIntent);
        return super.onOptionsItemSelected(item);
    }

    SharedPreferences.OnSharedPreferenceChangeListener mPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            mQuizType = sharedPreferences.getString("pref_quizType", "Superhero Name");
            resetQuiz();
        }
    };
}
