package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is the splash screen of the application.
 */
public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "MainActivity";
    private Button startButton;
    private Button pastQuizzesButton;
    private static QuizData quizData = null;
    private int[] a = new int[6];
    private static int empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        pastQuizzesButton = findViewById(R.id.pastQuizzesButton);
        startButton.setOnClickListener(new StartButtonClickListener());
        pastQuizzesButton.setOnClickListener(new PastQuizzesButtonClickListener());

        quizData = new QuizData(MainActivity.this);

        if (quizData != null) {
            quizData.open();
            Log.d(DEBUG_TAG, "opened DB");
        }

        new readInitial().execute();

    }

    /**
     * Method to check if the questions table is empty, and if it is not,
     * will call the appropriate methods to read the CSV file and fill
     * the database table.
     */
    private class readInitial extends AsyncTask<Void, List<QuizQuestion>> {

        @Override
        protected List<QuizQuestion> doInBackground(Void... arguments) {
            String query = "SELECT * FROM quizQuestions";
            empty = quizData.doQuery(query);
            return new ArrayList<QuizQuestion>();
        }

        @Override
        protected void onPostExecute(List<QuizQuestion> quizQuestions) {
            if (empty == 0) {
                // read file
                Log.d(DEBUG_TAG, "table is empty");
                readQuestions();
            } else {Log.d(DEBUG_TAG, "table is not empty");}
        }
    }

    private class StartButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Random ran = new Random();

            // loop to fill array with unique random numbers 1-50
            for (int i = 0; i < 6; i++) {
                a[i] = ran.nextInt(50) + 1;

                for (int j = 0; j < i; j++) {
                    if (a[i] == a[j]) {
                        i--; //if a[i] is a duplicate of a[j], run outer loop on i again
                        break;
                    } // if
                }  // for
            } // for

            Log.d(DEBUG_TAG, "arr vals: " + a[0] + " " + a[1] + " " + a[2] + " " + a[3] + " " + a[4] + " " + a[5]);

            Quiz newQuiz = new Quiz("", -1, -1, a[0], a[1], a[2], a[3], a[4], a[5]);

            new QuizDBWriter().execute(newQuiz);

            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            startActivity(intent);
        }
    }

    private class PastQuizzesButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PastQuizzesActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Method to asynchronously store a new quiz in the database.
     */
    public static class QuizDBWriter extends AsyncTask<Quiz, Quiz> {

        @Override
        protected Quiz doInBackground(Quiz... quizzes) {
            quizData.storeQuiz(quizzes[0]);
            return quizzes[0];
        } // doInBg

        @Override
        protected void onPostExecute(Quiz quiz) {
            // anything that needs to be done
            Log.d("TEST", "in onPostExecute()");
        } // onPost

    } // class

    /**
     * This method will read a line from the CSV file, use it to create
     * a new QuizQuestion object, and call a method to insert that object
     * into the database.
     */
    public void readQuestions() {
        try {
            // open CSV file
            InputStream in_s = getAssets().open("StateCapitals.csv");

            // read data
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextRow;
            while ((nextRow = reader.readNext()) != null) {

                String state = nextRow[0];
                String capital = nextRow[1];
                String city2 = nextRow[2];
                String city3 = nextRow[3];

                QuizQuestion question = new QuizQuestion(state, capital, city2, city3);

                // call async method to write into DB
                new QuestionDBWriter().execute(question);
            }

        } catch (Exception e) {
            // debug msg
        }
    } // readQuestions

    /**
     * Method to asynchronously write a QuizQuestion object to the database.
     */
    public class QuestionDBWriter extends AsyncTask<QuizQuestion, QuizQuestion> {

        @Override
        protected QuizQuestion doInBackground(QuizQuestion... quizQuestions) {
            quizData.storeQuestion(quizQuestions[0]);
            return quizQuestions[0];
        } // doInBg

        @Override
        protected void onPostExecute(QuizQuestion question) {
            // anything that needs to be done
            Log.d(DEBUG_TAG, "in onPostExecute()");
        } // onPost

    } // class



}