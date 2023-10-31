package edu.uga.cs.statecapitalsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

    private Button start;

    private QuizData quizData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setTitle();

        // test to see if CSV reading works
        start = findViewById(R.id.button);

        start.setOnClickListener(new ButtonClickListener());
    }

    // !! test methods - will need to be moved !!
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            try {
                // open CSV file
                InputStream in_s = getAssets().open("StateCapitals.csv");

                // read data
                CSVReader reader = new CSVReader( new InputStreamReader( in_s) );
                String[] nextRow;
                while( (nextRow = reader.readNext() ) != null) {
                    // nextRow is arr of value in line
                    // so i = 0 is state, 1 is capital, 2 is 2nd city, 3 is 3rd city

                    // use methods to store vals into questions DB
                    String state = nextRow[0];
                    String capital = nextRow[1];
                    String city2 = nextRow[2];
                    String city3 = nextRow[3];

                    QuizQuestion question = new QuizQuestion(state, capital, city2, city3);

                    // need to create a QuizData instance somewhere
                    quizData = new QuizData(MainActivity.this);

                    // call async method to write into DB
                    new QuestionDBWriter().execute(question);
                }

            } catch (Exception e) {
                // debug msg
            }
        }
    }

    public class QuestionDBWriter extends AsyncTask<QuizQuestion, QuizQuestion> {

        @Override
        protected QuizQuestion doInBackground( QuizQuestion... quizQuestions) {
            quizData.storeQuestion(quizQuestions[0]);
            return quizQuestions[0];
        } // doInBg

        @Override
        protected void onPostExecute(QuizQuestion question) {
            // anything that needs to be done
            // log msg?
        } // onPost

    } // class
}