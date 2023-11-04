package edu.uga.cs.statecapitalsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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
    private int[] a = new int[6];

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

        // if table is null - read questions file into DB
        String query = "SELECT * from quizQuestions";
        int empty = quizData.doQuery(query);
        if (empty == 0) {
            // read file
            readQuestions();
        }
    }

    private class StartButtonClickListener implements View.OnClickListener {

        /**
         * Handles overview or details button click
         *
         * @param view the button
         */
        @Override
        public void onClick(View view) {

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

            String[] questionsArr = createQuestionsArr();
            String[][] choicesArr = createChoicesArr();

            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            // send questions/answers arrays with putExtra
            intent.putExtra("questionsArr", questionsArr);
            intent.putExtra("choicesArr", choicesArr);
            startActivity(intent);
        }
    }

    private class PastQuizzesButtonClickListener implements View.OnClickListener {

        /**
         * Handles overview or details button click
         *
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
            Log.d("TEST", "in onPostExecute()");
        } // onPost

    } // class

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

    public class QuestionDBWriter extends AsyncTask<QuizQuestion, QuizQuestion> {

        @Override
        protected QuizQuestion doInBackground(QuizQuestion... quizQuestions) {
            quizData.storeQuestion(quizQuestions[0]);
            return quizQuestions[0];
        } // doInBg

        @Override
        protected void onPostExecute(QuizQuestion question) {
            // anything that needs to be done
            Log.d("TEST", "in onPostExecute()");
        } // onPost

    } // class

    public String[] createQuestionsArr() {
        // get values from DB
        Cursor cursor = null;
        String query = null;

        query = "SELECT qq.state FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[0];
        String q1 = quizData.doQuery(query, 2);
        query = "SELECT qq.state FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[1];
        String q2 = quizData.doQuery(query, 2);
        query = "SELECT qq.state FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[2];
        String q3 = quizData.doQuery(query, 2);
        query = "SELECT qq.state FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[3];
        String q4 = quizData.doQuery(query, 2);
        query = "SELECT qq.state FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[4];
        String q5 = quizData.doQuery(query, 2);
        query = "SELECT qq.state FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[5];
        String q6 = quizData.doQuery(query, 2);

        // create arrays to send with intent
        String[] questions = {
                "What is the capital of " + q1,
                "What is the capital of " + q2,
                "What is the capital of " + q3,
                "What is the capital of " + q4,
                "What is the capital of " + q5,
                "What is the capital of " + q6
        };
        return questions;
    } // createQsArr


    public String[][] createChoicesArr() {
        Cursor cursor = null;
        String query = null;

        query = "SELECT qq.city_capital FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[0];
        String a1_1 = quizData.doQuery(query, 3);
        query = "SELECT qq.city_2 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[0];
        String a1_2 = quizData.doQuery(query, 4);
        query = "SELECT qq.city_3 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[0];
        String a1_3 = quizData.doQuery(query, 5);

        query = "SELECT qq.city_capital FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[1];
        String a2_1 = quizData.doQuery(query, 3);
        query = "SELECT qq.city_2 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[1];
        String a2_2 = quizData.doQuery(query, 4);
        query = "SELECT qq.city_3 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[1];
        String a2_3 = quizData.doQuery(query, 5);

        query = "SELECT qq.city_capital FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[2];
        String a3_1 = quizData.doQuery(query, 3);
        query = "SELECT qq.city_2 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[2];
        String a3_2 = quizData.doQuery(query, 4);
        query = "SELECT qq.city_3 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[2];
        String a3_3 = quizData.doQuery(query, 5);

        query = "SELECT qq.city_capital FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[3];
        String a4_1 = quizData.doQuery(query, 3);
        query = "SELECT qq.city_2 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[3];
        String a4_2 = quizData.doQuery(query, 4);
        query = "SELECT qq.city_3 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[3];
        String a4_3 = quizData.doQuery(query, 5);

        query = "SELECT qq.city_capital FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[4];
        String a5_1 = quizData.doQuery(query, 3);
        query = "SELECT qq.city_2 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[4];
        String a5_2 = quizData.doQuery(query, 4);
        query = "SELECT qq.city_3 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[4];
        String a5_3 = quizData.doQuery(query, 5);

        query = "SELECT qq.city_capital FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[5];
        String a6_1 = quizData.doQuery(query, 3);
        query = "SELECT qq.city_2 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[5];
        String a6_2 = quizData.doQuery(query, 4);
        query = "SELECT qq.city_3 FROM quizQuestions qq, quizzes q WHERE qq._id == " + a[5];
        String a6_3 = quizData.doQuery(query, 5);

        String[][] choices = {
                {a1_1, a1_2, a1_3},
                {a2_1, a2_2, a2_3},
                {a3_1, a3_2, a3_3},
                {a4_1, a4_2, a4_3},
                {a5_1, a5_2, a5_3},
                {a6_1, a6_2, a6_3}
        };

        return choices;
    }
}