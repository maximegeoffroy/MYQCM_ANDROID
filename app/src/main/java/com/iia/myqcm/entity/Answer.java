package com.iia.myqcm.entity;

import java.util.Date;

/**
 * Created by gemax on 06/02/2016.
 */
public class Answer {

    /**
     * answer id
     */
    protected long id;

    /**
     * answer content
     */
    protected String content;

    /**
     * answer point
     */
    protected int point;

    /**
     * answer is valid
     */
    protected boolean is_valid;

    /**
     * answer created at
     */
    protected Date created_at;

    /**
     * answer updated at
     */
    protected Date updated_at;

    /**
     * answer question
     */
    protected Question question;

    /**
     * answer id server
     */
    protected long idServer;

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
     * Get content
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set content
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get point
     * @return point
     */
    public int getPoint() {
        return point;
    }

    /**
     * Set point
     * @param point
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * Get is valid
     * @return is valid
     */
    public boolean getIs_valid() {
        return is_valid;
    }

    /**
     * Set is valid
     * @param is_valid
     */
    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    /**
     * Get created at
     * @return created at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * Set created at
     * @param created_at
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * Get updated at
     * @return updated at
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * Set updated at
     * @param updated_at
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * Get question
     * @return question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Set question
     * @param question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * Get id server
     * @return id server
     */
    public long getIdServer() {
        return idServer;
    }

    /**
     * Set id server
     * @param idServer
     */
    public void setIdServer(long idServer) {
        this.idServer = idServer;
    }
}
