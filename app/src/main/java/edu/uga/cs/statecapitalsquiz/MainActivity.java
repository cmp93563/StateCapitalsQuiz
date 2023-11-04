package edu.uga.cs.statecapitalsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Scanner;
import android.util.Log;
import java.util.Random;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "MainActivity";
    private Button startButton;
    private Button pastQuizzesButton;
    private QuizData quizData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setTitle();
        startButton = findViewById(R.id.startButton);
        pastQuizzesButton = findViewById(R.id.pastQuizzesButton);
        startButton.setOnClickListener(new StartButtonClickListener());
        pastQuizzesButton.setOnClickListener(new PastQuizzesButtonClickListener());

        quizData = new QuizData(MainActivity.this);

        if (quizData != null) {
            quizData.open();
            Log.d(DEBUG_TAG, "opened DB");
        }
    }

    private class StartButtonClickListener implements View.OnClickListener {

        /**
         * Handles overview or details button click
         * @param view the button
         */
        @Override
        public void onClick(View view) {

            int[] a = new int[6];
            Random ran = new Random();

            // loop to fill array with unique random numbers 1-50
            for (int i = 0; i < 6; i++) {
                a[i] = ran.nextInt(50) + 1;

                for (int j = 0; j < i; j++) {
                    if (a[i] == a[j]) {
                        i--; //if a[i] is a duplicate of a[j], then run the outer loop on i again
                        break;
                    } // if
                }  // for
            } // for

            Log.d(DEBUG_TAG, "arr vals: " + a[0] + " " + a[1] + " " + a[2] + " " + a[3] + " " + a[4] + " " + a[5]);

            Quiz newQuiz = new Quiz(null, -1, -1, a[0], a[1], a[2], a[3], a[4], a[5]);

            new QuizDBWriter().execute(newQuiz);

            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            startActivity(intent);
        }
    }

    private class PastQuizzesButtonClickListener implements View.OnClickListener {

        /**
         * Handles overview or details button click
         * @param view the button
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PastQuizzesActivity.class);
            startActivity(intent);
        }
    }

    // after quiz is created - store into DB ASYNCHRONOUSLY
    public class QuizDBWriter extends AsyncTask<Quiz, Quiz> {

        @Override
        protected Quiz doInBackground(Quiz... quizzes) {
            quizData.storeQuiz(quizzes[0]);
            return quizzes[0];
        } // doInBg

        @Override
        protected void onPostExecute(Quiz quiz) {
            // anything that needs to be done
            Log.d("TEST",  "in onPostExecute()");
        } // onPost

    } // class
}