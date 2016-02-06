package com.iia.myqcm.entity;

import java.util.Date;

/**
 * Created by gemax on 06/02/2016.
 */
public class Question {
    /*
        Question id
    */
    protected long id;

    /*
        Question content
     */
    protected String content;

    /*
        Question created_at
     */
    protected Date created_at;

    /*
        Question updated_at
     */
    protected Date updated_at;

    /*
        Question qcm
    */
    protected  Qcm qcm;


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

    public Qcm getQcm() {
        return qcm;
    }

    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }
}
