package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all quizzes.
 */
public class QuizRecyclerAdapter
        extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizResultHolder> {

    public static final String DEBUG_TAG = "QuizRecyclerAdapter";

    private final Context context;

    private List<Quiz> values;
    private List<Quiz> originalValues;

    public QuizRecyclerAdapter(Context context, List<Quiz> quizList) {
        this.context = context;
        this.values = quizList;
        this.originalValues = new ArrayList<Quiz>(quizList);
    }

    public void sync() {
        originalValues = new ArrayList<Quiz>(values);
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public static class QuizResultHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView time;
        TextView result;

        public QuizResultHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.quizDate);
            result = itemView.findViewById(R.id.quizScore);
        }
    }

    @NonNull
    @Override
    public QuizResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz, parent, false);
        return new QuizRecyclerAdapter.QuizResultHolder(view);
    }

    @Override
    public void onBindViewHolder( QuizResultHolder holder, int position ) {

        Quiz quiz = values.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + quiz );

        holder.date.setText( quiz.getDate());
        holder.result.setText( Integer.toString(quiz.getResult()) );
    }

    @Override
    public int getItemCount() {
        if( values != null )
            return values.size();
        else
            return 0;
    }
}
