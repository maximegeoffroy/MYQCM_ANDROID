package com.iia.myqcm.entity;

/**
 * Created by gemax on 17/02/2016.
 */
public class AnswerUser {

    /*
        AnswerUser id
    */
    protected long id;

    /*
        Answer id
    */
    protected long answer_id;

    /*
        Question id
     */
    protected long question_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(long answer_id) {
        this.answer_id = answer_id;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }
}
