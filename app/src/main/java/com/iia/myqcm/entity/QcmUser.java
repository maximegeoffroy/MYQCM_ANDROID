package com.iia.myqcm.entity;

/**
 * Created by gemax on 26/04/2016.
 */
public class QcmUser {
    /**
        QcmUser user
     */
    protected User user;
    /**
        QcmUser qcm
     */
    protected Qcm qcm;
    /**
        QcmUser note
     */
    protected float note;
    /**
        QcmUser is_done
     */
    protected Boolean is_done;

    /**
     * get user
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
        set user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * get qcm
     * @return qcm
     */
    public Qcm getQcm() {
        return qcm;
    }

    /**
        set qcm
     */
    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }

    /**
     * get note
     * @return note
     */
    public float getNote() {
        return note;
    }

    /**
        set note
     */
    public void setNote(float note) {
        this.note = note;
    }

    /**
     * get is_done
     * @return is_done
     */
    public Boolean getIs_done() {
        return is_done;
    }

    /**
        set is_done
     */
    public void setIs_done(Boolean is_done) {
        this.is_done = is_done;
    }
}
