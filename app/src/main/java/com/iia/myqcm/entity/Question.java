package com.iia.myqcm.entity;

import java.util.Date;

/**
 * Created by gemax on 06/02/2016.
 */
public class Question {
    /**
        Question id
    */
    protected long id;

    /**
        Question content
     */
    protected String content;

    /**
        Question created_at
     */
    protected Date created_at;

    /**
        Question updated_at
     */
    protected Date updated_at;

    /**
        Question qcm
    */
    protected  Qcm qcm;

    /**
        Question idServer
    */
    protected  long idServer;

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
     * Get created_at
     * @return created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * Set created_at
     * @param created_at
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * Get updated_at
     * @return updated_at
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * Set updated_at
     * @param updated_at
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * Get qcm
     * @return qcm
     */
    public Qcm getQcm() {
        return qcm;
    }

    /**
     * Set qcm
     * @param qcm
     */
    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }

    /**
     * Get idServer
     * @return idServer
     */
    public long getIdServer() {
        return idServer;
    }

    /**
     * Set idServer
     * @param idServer
     */
    public void setIdServer(long idServer) {
        this.idServer = idServer;
    }
}
