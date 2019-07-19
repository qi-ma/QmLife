/**
 * Copyright 2019 bejson.com
 */
package com.qm.qmlife.business.model.technologynow;

/**
 * Auto-generated: 2019-07-18 10:30:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private long id;
    private String title;
    private String slug;
    private String list_image_url;
    private String public_abbr;
    private boolean commentable;
    private String important_collection;
    private User user;
    private int total_fp_amount;
    private int public_comments_count;
    private int total_rewards_count;
    private int likes_count;
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    public String getSlug() {
        return slug;
    }

    public void setList_image_url(String list_image_url) {
        this.list_image_url = list_image_url;
    }
    public String getList_image_url() {
        return list_image_url;
    }

    public void setPublic_abbr(String public_abbr) {
        this.public_abbr = public_abbr;
    }
    public String getPublic_abbr() {
        return public_abbr;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }
    public boolean getCommentable() {
        return commentable;
    }

    public void setImportant_collection(String important_collection) {
        this.important_collection = important_collection;
    }
    public String getImportant_collection() {
        return important_collection;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setTotal_fp_amount(int total_fp_amount) {
        this.total_fp_amount = total_fp_amount;
    }
    public int getTotal_fp_amount() {
        return total_fp_amount;
    }

    public void setPublic_comments_count(int public_comments_count) {
        this.public_comments_count = public_comments_count;
    }
    public int getPublic_comments_count() {
        return public_comments_count;
    }

    public void setTotal_rewards_count(int total_rewards_count) {
        this.total_rewards_count = total_rewards_count;
    }
    public int getTotal_rewards_count() {
        return total_rewards_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }
    public int getLikes_count() {
        return likes_count;
    }

}