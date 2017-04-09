package com.vityur.bignerdranch.mygeoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {

    public static final String ANSWER_WAS_SHOWN =
            "com.vityur.bignerdranch.mygeoquiz.ANSWER_WAS_SHOWN";

    private TextView mAnswerText;
    private Button mShowAnswerButton;

    private boolean mAnswer;
    private boolean mUserCheated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswer = getIntent().getBooleanExtra(QuizActivity.EXTRA_ANSWER_IS_TRUE, false);

        if (savedInstanceState != null) {
            mUserCheated = savedInstanceState.getBoolean(ANSWER_WAS_SHOWN, false);
        }

        mAnswerText = (TextView) findViewById(R.id.answer_text);
        if (mUserCheated) {
            showAnswer();
            answerWasShown(mUserCheated);
        }

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
                mUserCheated = true;
                answerWasShown(mUserCheated);

            }
        });
    }

    private void answerWasShown(boolean userCheated) {
        if (userCheated) {
            Intent data = new Intent();
            data.putExtra(ANSWER_WAS_SHOWN, userCheated);
            setResult(Activity.RESULT_OK, data);
        } else return;
    }

    private void showAnswer() {
        if (mAnswer) {
            mAnswerText.setText(R.string.true_button);
        } else {
            mAnswerText.setText(R.string.false_button);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ANSWER_WAS_SHOWN, mUserCheated);
    }
}
