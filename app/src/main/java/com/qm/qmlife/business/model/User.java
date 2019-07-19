package com.qm.qmlife.business.model;


import org.litepal.crud.DataSupport;

/**
 * Created by syt on 2019/7/8.
 */

public class User extends DataSupport {
    private Long id;
    private String account;
    private String name;
    private String pwd;
    private String sex;
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
