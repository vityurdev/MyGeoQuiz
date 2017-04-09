package com.vityur.bignerdranch.mygeoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    public static final String KEY_INDEX =
            "com.vityur.bignerdranch.mygeoquiz.KEY_INDEX";
    public static final String EXTRA_ANSWER_IS_TRUE =
            "com.vityur.bignerdranch.mygeoquiz.EXTRA_ANSWER_IS_TRUE";
    public static final int REQUEST_CODE_CHEAT = 0;

    private TextView mQuestionTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPrevButton;
    private Button mNextButton;
    private Button mCheatButton;

    private int mCurrentIndex = 0;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

            for (int i = 0; i < mQuestionBank.length; i++) {
                String key = QuizActivity.this.getPackageName() + ".Q" + new Integer(i).toString();
                boolean value = savedInstanceState.getBoolean(key);
                mQuestionBank[i].setAnswerSeen(value);
            }
        }
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 9) % mQuestionBank.length;
                updateQuestion();

            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();

            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                intent.putExtra(EXTRA_ANSWER_IS_TRUE, mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);

        for (int i = 0; i < mQuestionBank.length; i++) {
            String key = QuizActivity.this.getPackageName() + ".Q" + new Integer(i).toString();
            boolean value = mQuestionBank[i].isAnswerSeen();
            outState.putBoolean(key, value);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE_CHEAT) {
            return;
        }

        if (resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            mQuestionBank[mCurrentIndex].setAnswerSeen(true);
        }
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressed) {
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        boolean isSeen = mQuestionBank[mCurrentIndex].isAnswerSeen();
        int messageResId = 0;

        if (isSeen) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressed == answer) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
