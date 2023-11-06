package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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

    private static final String DEBUG_TAG = "QQFragment";

    private int questionNum;

    private QuizData quizData = null;

    private List<QuizQuestion> questionsList;

    private List<Quiz> quizList;

    private int[] indices;

    private int currentQuiz;
    private int index1;
    private int index2;
    private int index3;
    private int index4;
    private int index5;
    private int index6;
    private int[] a;

    TextView textView;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;

    private static String[] questions;

    private static String[][] choices;


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

        questions = new String[6];
        choices = new String[6][3];

        textView = view.findViewById( R.id.question );
        radioButton1 = view.findViewById( R.id.choice1);
        radioButton2 = view.findViewById( R.id.choice2);
        radioButton3 = view.findViewById( R.id.choice3);

        // randomize order of answers
        a = new int[3];
        Random ran = new Random();

        // loop to fill array with unique random numbers 1-50
        for (int i = 0; i < 3; i++) {
            a[i] = ran.nextInt(3);

            for (int j = 0; j < i; j++) {
                if (a[i] == a[j]) {
                    i--; //if a[i] is a duplicate of a[j], then run the outer loop on i again
                    break;
                } // if
            }  // for
        } // for

        quizData = new QuizData(getActivity());
        quizData.open();

        new QuestionDBReader().execute();
        new QuizDBReader().execute();


//        titleView.setText( androidVersions[ versionNum ] );
//        highlightsView.setText( androidVersionsInfo[ versionNum ] );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_question, container, false);
    }

    public static int getNumberOfQuestions() {
        //return questions.length;
        return 6;
    }

    private class QuestionDBReader extends AsyncTask<Void, List<QuizQuestion>> {

        @Override
        protected List<QuizQuestion> doInBackground( Void... params ) {
            questionsList = quizData.retrieveAllQuestions();

            return questionsList;
        }

        @Override
        protected void onPostExecute( List<QuizQuestion> qsList ) {
            //questionsList.addAll(qsList);
            Log.d(DEBUG_TAG, "questions post execute");

        }
    }

    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {

        @Override
        protected List<Quiz> doInBackground( Void... params ) {
            quizList = quizData.retrieveAllQuizzes();

            return quizList;
        }

        @Override
        protected void onPostExecute( List<Quiz> qzList ) {
            //quizList.addAll(qzList);
            Log.d(DEBUG_TAG, "quiz post execute");

            currentQuiz = quizList.size() - 1;
            Log.d(DEBUG_TAG, "currentQuiz: " + currentQuiz);
            index1 = quizList.get(currentQuiz).getQ_1();
            index2 = quizList.get(currentQuiz).getQ_2();
            index3 = quizList.get(currentQuiz).getQ_3();
            index4 = quizList.get(currentQuiz).getQ_4();
            index5 = quizList.get(currentQuiz).getQ_5();
            index6 = quizList.get(currentQuiz).getQ_6();

            indices = new int[] {index1, index2, index3, index4, index5, index6};

            Log.d(DEBUG_TAG, "indices: [" + index1 + " " + index2 + " " + index3 + " " + index4 + " " + index5 + " " + index6 + "]");

            questions = createQuestionsArr(indices);
            choices = createChoicesArr(indices);

            // set question and choices
            textView.setText(questions[ questionNum ]);
            radioButton1.setText("a. " + choices[questionNum][a[0]]);
            radioButton2.setText("b. " + choices[questionNum][a[1]]);
            radioButton3.setText("c. " + choices[questionNum][a[2]]);
        }
    }

    public String[] createQuestionsArr(int[] indices) {
        String[] qs = new String[6];
        // get values from DB
        String q1 = questionsList.get(indices[0]-1).getState();
        String q2 = questionsList.get(indices[1]-1).getState();
        String q3 = questionsList.get(indices[2]-1).getState();
        String q4 = questionsList.get(indices[3]-1).getState();
        String q5 = questionsList.get(indices[4]-1).getState();
        String q6 = questionsList.get(indices[5]-1).getState();

        qs[0] = "What is the capital of " + q1 + "?";
        qs[1] = "What is the capital of " + q2 + "?";
        qs[2] = "What is the capital of " + q3 + "?";
        qs[3] = "What is the capital of " + q4 + "?";
        qs[4] = "What is the capital of " + q5 + "?";
        qs[5] = "What is the capital of " + q6 + "?";


        return qs;
    } // createQsArr


    public String[][] createChoicesArr(int[] indices) {
        String[][] as = new String[6][3];
        String a1_1 = questionsList.get(indices[0]-1).getCapital();
        String a1_2 = questionsList.get(indices[0]-1).getCity2();
        String a1_3 = questionsList.get(indices[0]-1).getCity3();

        String a2_1 = questionsList.get(indices[1]-1).getCapital();
        String a2_2 = questionsList.get(indices[1]-1).getCity2();
        String a2_3 = questionsList.get(indices[1]-1).getCity3();

        String a3_1 = questionsList.get(indices[2]-1).getCapital();
        String a3_2 = questionsList.get(indices[2]-1).getCity2();
        String a3_3 = questionsList.get(indices[2]-1).getCity3();

        String a4_1 = questionsList.get(indices[3]-1).getCapital();
        String a4_2 = questionsList.get(indices[3]-1).getCity2();
        String a4_3 = questionsList.get(indices[3]-1).getCity3();

        String a5_1 = questionsList.get(indices[4]-1).getCapital();
        String a5_2 = questionsList.get(indices[4]-1).getCity2();
        String a5_3 = questionsList.get(indices[4]-1).getCity3();

        String a6_1 = questionsList.get(indices[5]-1).getCapital();
        String a6_2 = questionsList.get(indices[5]-1).getCity2();
        String a6_3 = questionsList.get(indices[5]-1).getCity3();

        as[0][0] = a1_1;
        as[0][1] = a1_2;
        as[0][2] = a1_3;

        as[1][0] = a2_1;
        as[1][1] = a2_2;
        as[1][2] = a2_3;

        as[2][0] = a3_1;
        as[2][1] = a3_2;
        as[2][2] = a3_3;

        as[3][0] = a4_1;
        as[3][1] = a4_2;
        as[3][2] = a4_3;

        as[4][0] = a5_1;
        as[4][1] = a5_2;
        as[4][2] = a5_3;

        as[5][0] = a6_1;
        as[5][1] = a6_2;
        as[5][2] = a6_3;

        return as;
    }

}