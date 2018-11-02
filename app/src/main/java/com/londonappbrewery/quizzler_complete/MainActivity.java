package com.londonappbrewery.quizzler_complete;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class MainActivity extends Activity {

    // Member variables accessible in all the methods of the MainActivity:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    TextView mScoretextView;
    ProgressBar mProgressBar;
    int mIndex;
    int mScore;
    int mQuestion;

    // Create question bank using the TrueFalse class for each item in the array
    @NonNull
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };


    final int PROGRESS_BAR_INCREAMENT = (int) Math.ceil(100.0/mQuestionBank.length);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        } else {
            mScore = 0;
            mIndex = 0;
        }

        // In API 26 'casting' is no longer needed.
        // For API 25 and lower: mTrueButton = (Button) findViewById(R.id.true_button);
        // For API 26 and higher can use: mTrueButton = findViewById(R.id.true_button);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mScoretextView = findViewById(R.id.score);
        mProgressBar = findViewById(R.id.progress_bar);
        mQuestionTextView = findViewById(R.id.question_text_view);


        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mScoretextView.setText("Score " + mScore + "/" + mQuestionBank.length);


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chechkAnswer(true);
                updateQuestion();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chechkAnswer(false);
                updateQuestion();
            }
        });

    }

    private void updateQuestion(){
        mIndex = (mIndex + 1) % mQuestionBank.length;

        if(mIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }
        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREAMENT);
        mScoretextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    }


    private void chechkAnswer(boolean userSelection){

        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        if(userSelection == correctAnswer){
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore = mScore + 1;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("Scorekey", mScore);
        outState.putInt("IndexKey", mIndex);
    }

}



