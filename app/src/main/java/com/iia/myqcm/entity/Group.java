package com.iia.myqcm.entity;

import java.util.Date;

/**
 * Created by gemax on 30/01/2016.
 */
public class Group {

    /**
        Group id
     */
    protected long id;

    /**
        Group name
     */
    protected String name;

    /**
        Group created_at
     */
    protected Date created_at;

    /**
        Group updated_at
     */
    protected Date updated_at;

    /**
     * Get id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
        Set id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
        Set name
     */
    public void setName(String name) {
        this.name = name;
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
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
