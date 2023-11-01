package edu.uga.cs.statecapitalsquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ViewPager2 pager = findViewById( R.id.viewpager );
        AndroidVersionsPagerAdapter avpAdapter = new
                AndroidVersionsPagerAdapter(
                getSupportFragmentManager(), getLifecycle() );
        pager.setOrientation(
                ViewPager2.ORIENTATION_HORIZONTAL );
        pager.setAdapter( avpAdapter );

    }
}