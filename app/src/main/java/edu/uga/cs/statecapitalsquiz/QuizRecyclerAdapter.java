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

import java.util.ArrayList;
import java.util.List;

public class QuizRecyclerAdapter
        extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizResultHolder>
        implements Filterable {

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
            time = itemView.findViewById(R.id.quizTime);
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

//        holder.date.setText( quiz.getDate());
//        holder.time.setText( quiz.getDate() );
//        holder.result.setText( quiz.getResult() );
        holder.date.setText( "11/2/2023");
        holder.time.setText( "10:00AM" );
        holder.result.setText( "100%" );
    }

    @Override
    public int getItemCount() {
        if( values != null )
            return values.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Quiz> list = new ArrayList<Quiz>(originalValues);
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = list.size();
                    filterResults.values = list;
                } else {
                    List<Quiz> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Quiz Quiz : list) {
                        // check if either the company name or the comments contain the search string
//                        if (Quiz.getCompanyName().toLowerCase().contains(searchStr)
//                                || Quiz.getComments().toLowerCase().contains(searchStr)) {
//                            resultsModel.add(Quiz);
//                        }
/*
                        // this may be a faster approach with a long list of items to search
                        if( quiz.getCompanyName().regionMatches( true, i, searchStr, 0, length ) )
                            return true;

 */
                    }

                    filterResults.count = resultsModel.size();
                    filterResults.values = resultsModel;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                values = (ArrayList<Quiz>) results.values;
                notifyDataSetChanged();
                if (values.size() == 0) {
                    Toast.makeText(context, "Not Found", Toast.LENGTH_LONG).show();
                }
            }

        };
        return filter;
    }
}
