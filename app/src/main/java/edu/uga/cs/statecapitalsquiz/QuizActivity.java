package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

/**
 * This class is a layout for the quiz questions. The quiz
 * question fragment will use this viewpager layout to
 * present the questions.
 */
public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ViewPager2 pager = findViewById(R.id.viewpager);
        QuizQuestionPagerAdapter avpAdapter = new QuizQuestionPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(avpAdapter);
    }
}