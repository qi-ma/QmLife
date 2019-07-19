package com.qm.qmlife.business.model;

import org.litepal.crud.DataSupport;

/**
 * Created by syt on 2019/7/9.
 */

public class Note extends DataSupport {
    private long  id;
    private String title;
    private String date;
    private String content;
    private String userAccount;
    public Note(){}
    public Note(long id, String title, String date, String content,String userAccount) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.userAccount=userAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
