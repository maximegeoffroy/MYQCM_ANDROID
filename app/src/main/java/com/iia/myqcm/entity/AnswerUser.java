package com.iia.myqcm.entity;

/**
 * Created by gemax on 17/02/2016.
 */
public class AnswerUser {

    /**
     * answerUser id
     */
    protected long id;

    /**
     * answerUser answer_id
     */
    protected long answer_id;

    /**
     * answerUser question_id
     */
    protected long question_id;

    /**
     * Get id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Set id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get answer_id
     * @return answer id
     */
    public long getAnswer_id() {
        return answer_id;
    }

    /**
     * Set answer_id
     * @param answer_id
     */
    public void setAnswer_id(long answer_id) {
        this.answer_id = answer_id;
    }

    /**
     * Get question_id
     * @return question id
     */
    public long getQuestion_id() {
        return question_id;
    }

    /**
     * Set question_id
     * @param question_id
     */
    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }
}
