package com.iia.myqcm.entity;

import java.util.Date;

/**
 * Created by gemax on 06/02/2016.
 */
public class Answer {
    /*
        Answer id
    */
    protected long id;

    /*
        Answer content
     */
    protected String content;

    /*
        Answer point
     */
    protected int point;

    /*
        Answer is_valid
     */
    protected boolean is_valid;

    /*
        Answer created_at
     */
    protected Date created_at;

    /*
        Answer updated_at
     */
    protected Date updated_at;

    /*
        Answer question
     */
    protected Question question;

    /*
        Answer idServer
    */
    protected long idServer;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public long getIdServer() {
        return idServer;
    }

    public void setIdServer(long idServer) {
        this.idServer = idServer;
    }
}
