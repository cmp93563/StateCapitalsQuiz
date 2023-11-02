package edu.uga.cs.statecapitalsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    private Button startButton;
    private Button pastQuizzesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setTitle();
        startButton = findViewById(R.id.startButton);
        pastQuizzesButton = findViewById(R.id.pastQuizzesButton);
        startButton.setOnClickListener(new StartButtonClickListener());
        pastQuizzesButton.setOnClickListener(new PastQuizzesButtonClickListener());
    }

    private class StartButtonClickListener implements View.OnClickListener {

        /**
         * Handles overview or details button click
         * @param view the button
         */
        @Override
        public void onClick(View view) {
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
}