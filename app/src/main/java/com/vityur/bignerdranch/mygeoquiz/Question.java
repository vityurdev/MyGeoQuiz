package com.vityur.bignerdranch.mygeoquiz;

/**
 * Created by Vitaly on 11.02.2017.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswerSeen;

    public Question(int textResId, boolean answerTrue, boolean answerSeen) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswerSeen = answerSeen;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswerSeen() {
        return mAnswerSeen;
    }

    public void setAnswerSeen(boolean answerSeen) {
        mAnswerSeen = answerSeen;
    }
}
