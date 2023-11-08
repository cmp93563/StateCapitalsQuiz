package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PastQuizzesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastQuizzesFragment extends Fragment {

    private static final String TAG = "PastQuizzesFragment";

    private QuizData quizData = null;
    private List<Quiz> quizzesList;

    private RecyclerView recyclerView;
    private QuizRecyclerAdapter recyclerAdapter;

    public PastQuizzesFragment() {
        // Required empty public constructor
    }

    public static PastQuizzesFragment newInstance() {
        PastQuizzesFragment fragment = new PastQuizzesFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        // Enable the search menu population.
        // When the parameter of this method is true, Android will call onCreateOptionsMenu on
        // this fragment, when the options menu is being built for the hosting activity.
        setHasOptionsMenu( true );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_past_quizzes, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        recyclerView = getView().findViewById( R.id.recyclerView );
//        FloatingActionButton floatingButton = getView().findViewById( R.id.floatingActionButton );

//        floatingButton.setOnClickListener(v -> {
//            AddJobLeadDialogFragment newFragment = new AddJobLeadDialogFragment();
//            newFragment.setHostFragment( PastQuizzesFragment.this );
//            newFragment.show( getParentFragmentManager(), null );
//        });

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager( layoutManager );

        quizzesList = new ArrayList<>();

        // Create a JobLeadsData instance, since we will need to save a new JobLead to the dn.
        // Note that even though more activites may create their own instances of the JobLeadsData
        // class, we will be using a single instance of the JobLeadsDBHelper object, since
        // that class is a singleton class.
        quizData = new QuizData( getActivity() );

        // Open that database for reading of the full list of job leads.
        // Note that onResume() hasn't been called yet, so the db open in it
        // was not called yet!
        quizData.open();

        // Execute the retrieval of the job leads in an asynchronous way,
        // without blocking the main UI thread.
        new JobLeadDBReader().execute();

    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of job leads, asynchronously.
    private class JobLeadDBReader extends AsyncTask<Void, List<Quiz>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<Quiz> doInBackground( Void... params ) {
            List<Quiz> quizzesList = quizData.retrieveAllQuizzes();

            Log.d( TAG, "JobLeadDBReader: Job leads retrieved: " + quizzesList.size() );

            return quizzesList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<Quiz> jobsList ) {
            Log.d( TAG, "JobLeadDBReader: jobsList.size(): " + jobsList.size() );
            quizzesList.addAll( jobsList );

            // create the RecyclerAdapter and set it for the RecyclerView
//            Collections.sort(quizzesList, new QuizSortByDate());
            List<Quiz> reverse = new ArrayList<>();
            for (int i = quizzesList.size() - 1; i >= 0; i--) {
                reverse.add(quizzesList.get(i));
            }
            recyclerAdapter = new QuizRecyclerAdapter( getActivity(), reverse );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Open the database
        if( quizData != null && !quizData.isDBOpen() ) {
            quizData.open();
            Log.d( TAG, "PastQuizzesFragment.onResume(): opening DB" );
        }

        // Update the app name in the Action Bar to be the same as the app's name
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

    // We need to save job leads into a file as the activity stops being a foreground activity
    @Override
    public void onPause() {
        super.onPause();

        // close the database in onPause
        if( quizData != null ) {
            quizData.close();
            Log.d( TAG, "PastQuizzesFragment.onPause(): closing DB" );
        }
    }
}