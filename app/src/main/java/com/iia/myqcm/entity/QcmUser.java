package com.iia.myqcm.entity;

/**
 * Created by gemax on 26/04/2016.
 */
public class QcmUser {
    /*
        user
     */
    protected User user;
    /*
        qcm
     */
    protected Qcm qcm;
    /*
        qcm note
     */
    protected float note;
    /*
        qcm is_done
     */
    protected Boolean is_done;

    /*
        get user
     */
    public User getUser() {
        return user;
    }

    /*
        set user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /*
        get qcm
     */
    public Qcm getQcm() {
        return qcm;
    }

    /*
        set qcm
     */
    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }

    /*
        get note
     */
    public float getNote() {
        return note;
    }

    /*
        set note
     */
    public void setNote(float note) {
        this.note = note;
    }

    /*
       get is_done
     */
    public Boolean getIs_done() {
        return is_done;
    }

    /*
        set is_done
     */
    public void setIs_done(Boolean is_done) {
        this.is_done = is_done;
    }
}
