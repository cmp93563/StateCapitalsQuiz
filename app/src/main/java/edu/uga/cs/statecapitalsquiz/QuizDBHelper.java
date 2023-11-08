package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class creates a new entry in the database for the quiz
 * questions. It is called by the QuizData class to store and
 * fetch data.
 */
public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizDBHelper";
    private static final String DB_NAME = "capitalsquiz.db";
    private static final int DB_VERSION = 1;

    // strings for questions table and columns
    public static final String TABLE_QUESTIONS = "quizQuestions";
    public static final String QUESTIONS_COLUMN_ID = "_id";
    public static final String QUESTIONS_COLUMN_STATE = "state";
    public static final String QUESTIONS_COLUMN_CAPITAL = "city_capital";
    public static final String QUESTIONS_COLUMN_CITY_2 = "city_2";
    public static final String QUESTIONS_COLUMN_CITY_3 = "city_3";

    // strings for quizzes table and columns
    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_DATE = "date";
    public static final String QUIZZES_COLUMN_RESULT = "result";
    public static final String QUIZZES_COLUMN_ANSWERED = "answered";
    public static final String QUIZZES_COLUMN_Q1 = "q_1";
    public static final String QUIZZES_COLUMN_Q2 = "q_2";
    public static final String QUIZZES_COLUMN_Q3 = "q_3";
    public static final String QUIZZES_COLUMN_Q4 = "q_4";
    public static final String QUIZZES_COLUMN_Q5 = "q_5";
    public static final String QUIZZES_COLUMN_Q6 = "q_6";


    // only instance for the helper
    private static QuizDBHelper helperInstance;

    // Create table SQL statement to create a table for quiz questions
    private static final String CREATE_QUESTIONS =
            "create table " + TABLE_QUESTIONS + " ("
                    + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUESTIONS_COLUMN_STATE + " TEXT, "
                    + QUESTIONS_COLUMN_CAPITAL + " TEXT, "
                    + QUESTIONS_COLUMN_CITY_2 + " TEXT, "
                    + QUESTIONS_COLUMN_CITY_3 + " TEXT"
                    + ")";

    // !!! may need 6 additional columns for answers to questions (null if none?) !!!!
    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_COLUMN_DATE + " TEXT, "
                    + QUIZZES_COLUMN_RESULT + " INTEGER, "
                    + QUIZZES_COLUMN_ANSWERED + " INTEGER, "
                    + QUIZZES_COLUMN_Q1 + " INTEGER, "
                    + QUIZZES_COLUMN_Q2 + " INTEGER, "
                    + QUIZZES_COLUMN_Q3 + " INTEGER, "
                    + QUIZZES_COLUMN_Q4 + " INTEGER, "
                    + QUIZZES_COLUMN_Q5 + " INTEGER, "
                    + QUIZZES_COLUMN_Q6 + " INTEGER, "
                    + " FOREIGN KEY (" + QUIZZES_COLUMN_Q1 + ") REFERENCES " + TABLE_QUESTIONS + " (" + QUESTIONS_COLUMN_ID + "), "
                    + " FOREIGN KEY (" + QUIZZES_COLUMN_Q2 + ") REFERENCES " + TABLE_QUESTIONS + " (" + QUESTIONS_COLUMN_ID + "), "
                    + " FOREIGN KEY (" + QUIZZES_COLUMN_Q3 + ") REFERENCES " + TABLE_QUESTIONS + " (" + QUESTIONS_COLUMN_ID + "), "
                    + " FOREIGN KEY (" + QUIZZES_COLUMN_Q4 + ") REFERENCES " + TABLE_QUESTIONS + " (" + QUESTIONS_COLUMN_ID + "), "
                    + " FOREIGN KEY (" + QUIZZES_COLUMN_Q5 + ") REFERENCES " + TABLE_QUESTIONS + " (" + QUESTIONS_COLUMN_ID + "), "
                    + " FOREIGN KEY (" + QUIZZES_COLUMN_Q6 + ") REFERENCES " + TABLE_QUESTIONS + " (" + QUESTIONS_COLUMN_ID + ") "
                    + ")";


    /**
     * private constructor for QuizDBHelper
     *
     * @param context
     */
    private QuizDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    // Access method to the single instance of the class.
    // It is synchronized, so that only one thread can executes this method at a time.
    public static synchronized QuizDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        boolean isNull = false; // debug

        if( helperInstance == null ) {
            helperInstance = new QuizDBHelper( context.getApplicationContext() );
            isNull = true; // debug
        }

        Log.d(DEBUG_TAG, "DBHelper Instance is null: " + isNull);

        return helperInstance;
    }


    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_QUESTIONS );
        Log.d(DEBUG_TAG, "Table " + TABLE_QUESTIONS + "created");
        db.execSQL( CREATE_QUIZZES );
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZZES + "created");
    }


    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_QUESTIONS );
        db.execSQL( "drop table if exists " + TABLE_QUIZZES );
        onCreate( db );
        Log.d(DEBUG_TAG, "Tables upgraded");
    }


}