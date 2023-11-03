package edu.uga.cs.statecapitalsquiz;

/**
 * This class (a POJO) represents a single quiz.
 */
public class Quiz {

    private long id;

    private String date;

    private int result;
    private int answered;

    private int q_1;

    private int q_2;

    private int q_3;

    private int q_4;

    private int q_5;

    private int q_6;


    public Quiz()
    {
        this.id = -1;
        this.date = null;
        this.result = -1;
        this.answered = -1;
        this.q_1 = -1;
        this.q_2 = -1;
        this.q_3 = -1;
        this.q_4 = -1;
        this.q_5 = -1;
        this.q_6 = -1;
    }

    public Quiz(String date, int result, int answered, int q_1, int q_2, int q_3, int q_4, int q_5, int q_6) {
        this.id = -1;
        this.date = null;
        this.result = -1;
        this.answered = -1;
        this.q_1 = -1;
        this.q_2 = -1;
        this.q_3 = -1;
        this.q_4 = -1;
        this.q_5 = -1;
        this.q_6 = -1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getAnswered() { return answered;}
    public void setAnswered(int answered) {this.answered = answered;}

    public int getQ_1() {
        return q_1;
    }

    public void setQ_1(int q_1) {
        this.q_1 = q_1;
    }

    public int getQ_2() {
        return q_2;
    }

    public void setQ_2(int q_2) {
        this.q_2 = q_2;
    }

    public int getQ_3() {
        return q_3;
    }

    public void setQ_3(int q_3) {
        this.q_3 = q_3;
    }

    public int getQ_4() {
        return q_4;
    }

    public void setQ_4(int q_4) {
        this.q_4 = q_4;
    }

    public int getQ_5() {
        return q_5;
    }

    public void setQ_5(int q_5) {
        this.q_5 = q_5;
    }

    public int getQ_6() {
        return q_6;
    }

    public void setQ_6(int q_6) {
        this.q_6 = q_6;
    }
}