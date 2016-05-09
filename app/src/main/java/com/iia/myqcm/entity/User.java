package com.iia.myqcm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gemax on 30/01/2016.
 */
public class User implements Serializable {

    /*
        User id
     */
    protected long id;

    /*
        User username
     */
    protected String username;

    /*
        User password
    */
    protected String password;

    /*
        User name
    */
    protected String name;

    /*
        User firstname
     */
    protected String firstname;

    /*
        User email
     */
    protected String email;

    /*
        User created_at
     */
    protected Date created_at;

    /*
        User updated_at
     */
    protected Date updated_at;

    /*
        User group
     */
    protected Group group;

    /*
        User qcms
     */
    protected ArrayList<QcmUser> qcmsUser;

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
        Get username
    */
    public String getUsername() {
        return username;
    }

    /*
        Set username
    */
    public void setUsername(String username) {
        this.username = username;
    }

    /*
        Get password
     */
    public String getPassword() {
        return password;
    }

    /*
        Set password
     */
    public void setPassword(String password) {
        this.password = password;
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
            Get firstname
        */
    public String getFirstname() {
        return firstname;
    }

    /*
        Set firstname
    */

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /*
        Get email
    */
    public String getEmail() {
        return email;
    }

    /*
        Get email
    */
    public void setEmail(String email) {
        this.email = email;
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
        Get group
     */
    public Group getGroup() {
        return group;
    }

    /*
        Set group
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /*
        Get list qcmuser
     */
    public ArrayList<QcmUser> getQcmsUser() {
        return qcmsUser;
    }

    /*
        Set list qcmuser
     */
    public void setQcmsUser(ArrayList<QcmUser> qcmUser) {
        this.qcmsUser = qcmUser;
    }
}
