package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the quiz data into the database and also
 * retrieves quizzes from it.
 */
public class QuizData {
    public static final String DEBUG_TAG = "QuizData";

    // this is a reference to our database; it is used later to run SQL commands
    private SQLiteDatabase db;

    // helper obj to create/update/delete table data
    private SQLiteOpenHelper quizDBHelper;

    private static final String[] questionColumns = {
            QuizDBHelper.QUESTIONS_COLUMN_ID,
            QuizDBHelper.QUESTIONS_COLUMN_STATE,
            QuizDBHelper.QUESTIONS_COLUMN_CAPITAL,
            QuizDBHelper.QUESTIONS_COLUMN_CITY_2,
            QuizDBHelper.QUESTIONS_COLUMN_CITY_3
    };

    private static final String[] quizColumns = {
            QuizDBHelper.QUIZZES_COLUMN_ID,
            QuizDBHelper.QUIZZES_COLUMN_DATE,
            QuizDBHelper.QUIZZES_COLUMN_RESULT,
            QuizDBHelper.QUIZZES_COLUMN_ANSWERED,
            QuizDBHelper.QUIZZES_COLUMN_Q1,
            QuizDBHelper.QUIZZES_COLUMN_Q2,
            QuizDBHelper.QUIZZES_COLUMN_Q3,
            QuizDBHelper.QUIZZES_COLUMN_Q4,
            QuizDBHelper.QUIZZES_COLUMN_Q5,
            QuizDBHelper.QUIZZES_COLUMN_Q6
    };

    // constructor
    public QuizData( Context context ) {
        //context.deleteDatabase("capitalsquiz.db");
        this.quizDBHelper = QuizDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = quizDBHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "DB is open.");
    }

    // Close the database
    public void close() {
        if( quizDBHelper != null ) {
            quizDBHelper.close();
            Log.d(DEBUG_TAG, "DB is closed.");
        }
    }

    public boolean isDBOpen() {
        return db.isOpen();
    }

    /**
     * Method to retrieve questions currently in the database and turn
     * them into an ArrayList of QuizQuestion objects.
     *
     * @return the ArrayList of question objects
     */
    public List<QuizQuestion> retrieveAllQuestions() {

        ArrayList<QuizQuestion> quizQuestions = new ArrayList<>();
        Cursor cursor = null;
        int colIndex;

            try {
                cursor = db.query(QuizDBHelper.TABLE_QUESTIONS, questionColumns, null, null, null, null, null);

                // store questions into list
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {

                        if (cursor.getColumnCount() >= 5) {

                            // get data from DB
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_COLUMN_ID);
                            long id = cursor.getLong(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_COLUMN_STATE);
                            String state = cursor.getString(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_COLUMN_CAPITAL);
                            String capital = cursor.getString(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_COLUMN_CITY_2);
                            String city2 = cursor.getString(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUESTIONS_COLUMN_CITY_3);
                            String city3 = cursor.getString(colIndex);

                            // new Question obj
                            QuizQuestion question = new QuizQuestion(state, capital, city2, city3);
                            question.setId(id);

                            quizQuestions.add(question);

                        } // if
                    } // while
                } // if
            } catch (Exception e) {
                Log.d(DEBUG_TAG, "Exception: " + e);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        return quizQuestions;
        } // QQ List



    /**
     * This method will create and return a convenient list of Quiz objects from the
     * information currently in the DB.
     *
     * @return an ArrayList of Quiz objects
     */
    public List<Quiz> retrieveAllQuizzes() {
            ArrayList<Quiz> quizzes = new ArrayList<>();
            Cursor cursor = null;
            int colIndex;

            try {

                cursor = db.query(QuizDBHelper.TABLE_QUIZZES, quizColumns, null, null, null, null, null);

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        if (cursor.getColumnCount() >= 10) {

                            // get vals
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_ID);
                            long id = cursor.getLong(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_DATE);
                            String date = cursor.getString(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_RESULT);
                            int result = cursor.getInt(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_ANSWERED);
                            int answered = cursor.getInt(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_Q1);
                            int q1 = cursor.getInt(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_Q2);
                            int q2 = cursor.getInt(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_Q3);
                            int q3 = cursor.getInt(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_Q4);
                            int q4 = cursor.getInt(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_Q5);
                            int q5 = cursor.getInt(colIndex);
                            colIndex = cursor.getColumnIndex(QuizDBHelper.QUIZZES_COLUMN_Q6);
                            int q6 = cursor.getInt(colIndex);

                            Quiz quiz = new Quiz(date, result, answered, q1, q2, q3, q4, q5, q6);
                            quiz.setId(id);

                            quizzes.add(quiz);
                        } // if
                    } // while
                } // if

            } catch (Exception e) {
                Log.d(DEBUG_TAG, "Exception: " + e);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return quizzes;
        }

    /**
     * This method will take a quiz object and store it in the Quiz DB table.
     *
     * @param quiz the Quiz object to be stored
     * @return the Quiz object that was stored
     */
    public Quiz storeQuiz(Quiz quiz) {

        ContentValues values = new ContentValues();
        values.put( QuizDBHelper.QUIZZES_COLUMN_DATE, quiz.getDate());
        values.put( QuizDBHelper.QUIZZES_COLUMN_RESULT, quiz.getResult());
        values.put( QuizDBHelper.QUIZZES_COLUMN_ANSWERED, quiz.getAnswered());
        values.put( QuizDBHelper.QUIZZES_COLUMN_Q1, quiz.getQ_1());
        values.put( QuizDBHelper.QUIZZES_COLUMN_Q2, quiz.getQ_2());
        values.put( QuizDBHelper.QUIZZES_COLUMN_Q3, quiz.getQ_3());
        values.put( QuizDBHelper.QUIZZES_COLUMN_Q4, quiz.getQ_4());
        values.put( QuizDBHelper.QUIZZES_COLUMN_Q5, quiz.getQ_5());
        values.put( QuizDBHelper.QUIZZES_COLUMN_Q6, quiz.getQ_6());

        long id = db.insert(QuizDBHelper.TABLE_QUIZZES, null, values);
        quiz.setId(id);

        Log.d(DEBUG_TAG, "Stored quiz with id: " + quiz.getId());

        return quiz;
    } // storeQuiz

    /**
     * This method will take a QuizQuestion object and store it into the Questions DB table.
     *
     * @param quizQuestion the quizQuestion object to be stored in DB
     * @return the quizQuestion that was stored
     */
    public QuizQuestion storeQuestion(QuizQuestion quizQuestion) {
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUESTIONS_COLUMN_STATE, quizQuestion.getState());
        values.put(QuizDBHelper.QUESTIONS_COLUMN_CAPITAL, quizQuestion.getCapital());
        values.put(QuizDBHelper.QUESTIONS_COLUMN_CITY_2, quizQuestion.getCity2());
        values.put(QuizDBHelper.QUESTIONS_COLUMN_CITY_3, quizQuestion.getCity3());

        Log.d(DEBUG_TAG, "after put values, before insert");

        long id = db.insert(QuizDBHelper.TABLE_QUESTIONS, null, values);
        quizQuestion.setId(id);

        Log.d(DEBUG_TAG, "Stored question with id: " + quizQuestion.getId());

        return quizQuestion;
    } // storeQuestion

    /**
     * This method will run a query on a table into the database
     * and return the desired value (specifically to check if the
     * quizQuestions DB table is empty or not).
     *
     * @param query the string query to be executed
     * @return 0 if the table is empty
     */
    public int doQuery(String query) {
        Cursor cursor = null;
        int num = -1;
        cursor = db.rawQuery(query, null);
        if (!cursor.moveToFirst()) {
            num = 0;
        }
        cursor.close();
        return num;
    }

    /**
     * This method will take the id, date, result, and number of
     * answered questions for a quiz and update those columns.
     *
     * @param id the id of the desired quis to update
     * @param date the date the quiz was completed
     * @param result the result of the quiz
     * @param answered the number of answered questions of the quiz
     */
    public void storeItems(int id, String date, int result, int answered) {
        String query = "UPDATE " + QuizDBHelper.TABLE_QUIZZES +
                        " SET " + QuizDBHelper.QUIZZES_COLUMN_DATE + " = " + date + ", "
                        + QuizDBHelper.QUIZZES_COLUMN_RESULT + " = " + result + ", "
                        + QuizDBHelper.QUIZZES_COLUMN_ANSWERED + " = " + answered
                        + " WHERE " + QuizDBHelper.QUIZZES_COLUMN_ID + " = " + id;

        db.execSQL(query);
        Log.d(DEBUG_TAG, "stored items in QuizData class");
    }


}

