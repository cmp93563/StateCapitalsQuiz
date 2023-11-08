package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * This class is the layout for the past quizzes fragment.
 */
public class PastQuizzesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // sets fragment for past quizzes
        setContentView(R.layout.activity_past_quizzes);
        Fragment fragment = new PastQuizzesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("Past Quizzes Activity" ).commit();

    }
}