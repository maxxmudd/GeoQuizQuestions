/*
From the book Android Programming: The Big Nerd Ranch Guide 3rd Edition
Build upon the previous trivia application to include multiple questions
and a next button with a graphic

Included challenges:
Add a Listener to the TextView
Add a Previous Button
From Button to ImageButton

By Maxx Mudd 12/19/2019
* */
package com.example.geoquizquestions;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// Cool tip: use Alt+Enter to summon IntelliJ and automatically add missing classes
// Side note: using autocompletion will automatically import classes

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton; // m for member prefix is naming convention from book
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    // create array of questions with their corresponding answers
    private Question[] mQuestionsBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    // do this upon creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view); // create textview area and prime it for
        updateQuestion();                                                     // displaying questions

        // create casted true button, set it as listener and create onClick method
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() { // <-- anonymous inner class, has no name, created
            @Override                                               // in argument for single use
            public void onClick(View v) {

                checkAnswer(true); // create and display toast
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);     // create casted false button, set it as listener
        mFalseButton.setOnClickListener(new View.OnClickListener() { // and create onClick method
            @Override
            public void onClick(View v) {
                // creates and displays a toast
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);      // create casted next button, set it as listener
        mNextButton.setOnClickListener(new View.OnClickListener() { // and create onClick method
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length; // use modulus to index next question,
                int question = mQuestionsBank[mCurrentIndex].getTextResId(); // allows wrap around
                mQuestionTextView.setText(question);
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);      // create casted previous button, set it as listener
        mPrevButton.setOnClickListener(new View.OnClickListener() { // and create onClick method
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionsBank.length); // use modulus to wrap around to next
                mCurrentIndex %= mQuestionsBank.length;                      // question and ensuring index is positive
                int question = mQuestionsBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
            }
        });

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view); // allow clicking text to move onto
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {     // next question
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;  // use modulus to wrap around to next
                int question = mQuestionsBank[mCurrentIndex].getTextResId();  // question
                mQuestionTextView.setText(question);
            }
        });

        updateQuestion();
    }

    // method that updates the current question
    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    // method that checks answer
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if(userPressedTrue == answerIsTrue) {   // select a toast to display
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show(); // display toast
    }
}
