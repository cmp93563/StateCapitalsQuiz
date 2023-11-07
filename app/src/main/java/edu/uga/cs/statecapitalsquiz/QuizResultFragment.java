package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizResultFragment extends Fragment {
    private String score = "5/6";
    public QuizResultFragment() {
        // Required empty public constructor
    }

    public static QuizResultFragment newInstance(String score) {
        QuizResultFragment fragment = new QuizResultFragment();
        Bundle args = new Bundle();
        args.putString("score", score);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getString("score");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        TextView textView = view.findViewById( R.id.score );
        RadioButton playAgainButton = view.findViewById( R.id.homeButton);

        // set question and choices

//        textView.setText("X/6");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_result, container, false);
    }
}