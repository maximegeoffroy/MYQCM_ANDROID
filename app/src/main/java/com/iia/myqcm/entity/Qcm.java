package com.iia.myqcm.entity;

import java.util.Date;

/**
 * Created by gemax on 31/01/2016.
 */
public class Qcm {

    /*
        Qcm id
     */
    protected long id;

    /*
        Qcm name
     */
    protected String name;

    /*
        Qcm start_date
     */
    protected Date start_date;

    /*
        Qcm end_date
     */
    protected Date end_date;

    /*
        Qcm duration
     */
    protected int duration;

    /*
        Qcm created_at
     */
    protected Date created_at;

    /*
        Qcm updated_at
     */
    protected Date updated_at;

    /*
        Qcm category
    */
    protected Category category;

    /*
        Get id
     */
    public long getId() {
        return id;
    }

    /*
        Set id
     */
    public void setId(long id) {
        this.id = id;
    }

    /*
        Get name
     */
    public String getName() {
        return name;
    }

    /*
        Set name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
        Get start_date
     */
    public Date getStart_date() {
        return start_date;
    }

    /*
        Set start_date
     */
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    /*
        Get end_date
     */
    public Date getEnd_date() {
        return end_date;
    }

    /*
        Set end_date
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /*
        Get duration
     */
    public int getDuration() {
        return duration;
    }

    /*
        Set duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /*
        Get created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /*
        Set created_at
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /*
        Get updated_at
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /*
        Set updated_at
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /*
        Get category
    */
    public Category getCategory() {
        return category;
    }

    /*
        Set category
    */
    public void setCategory(Category category) {
        this.category = category;
    }
}
