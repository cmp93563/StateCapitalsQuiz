package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizQuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int questionNum;

    private static final String[] questions = {
            "What is the capital of GA?",
            "What is the capital of GA?",
            "What is the capital of GA?",
            "What is the capital of GA?",
            "What is the capital of GA?",
            "What is the capital of GA?"
    };

    private static final String[][] choices = {
            {"Atlanta", "Madison", "Seattle"},
            {"Atlanta", "Madison", "Seattle"},
            {"Atlanta", "Madison", "Seattle"},
            {"Atlanta", "Madison", "Seattle"},
            {"Atlanta", "Madison", "Seattle"},
            {"Atlanta", "Madison", "Seattle"}
    };

    public QuizQuestionFragment() {
        // Required empty public constructor
    }

    public static QuizQuestionFragment newInstance(int questionNum) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putInt( "questionNum", questionNum );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if( getArguments() != null ) {
            questionNum = getArguments().getInt( "questionNum" );
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        TextView textView = view.findViewById( R.id.question );
        RadioButton radioButton1 = view.findViewById( R.id.choice1);
        RadioButton radioButton2 = view.findViewById( R.id.choice2);
        RadioButton radioButton3 = view.findViewById( R.id.choice3);

        // set question and choices

        textView.setText(questions[ questionNum ]);
        radioButton1.setText("a");
        radioButton2.setText("b");
        radioButton3.setText("c");


//        titleView.setText( androidVersions[ versionNum ] );
//        highlightsView.setText( androidVersionsInfo[ versionNum ] );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_question, container, false);
    }

    public static int getNumberOfQuestions() {
        return questions.length;
    }
}