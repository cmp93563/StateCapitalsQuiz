package edu.uga.cs.statecapitalsquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class PastQuizzesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_quizzes);
        Fragment fragment = new PastQuizzesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("Past Quizzes Activity" ).commit();

    }
}