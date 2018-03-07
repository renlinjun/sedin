package com.sedin.model;

import javax.persistence.*;

@Table(name = "m_user")
public class MUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    /**
     * 0-男 1-女
     */
    private String gender;

    @Column(name = "id_card")
    private String idCard;

    /**
     * 0-禁止登录 1--可用 -2-删除
     */
    private String status;

    private String tel;

    @Column(name = "user_id")
    private String userId;

    private String qq;

    @Column(name = "res_id")
    private Long resId;

    private String email;

    private String avatar;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取0-男 1-女
     *
     * @return gender - 0-男 1-女
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置0-男 1-女
     *
     * @param gender 0-男 1-女
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return id_card
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取0-禁止登录 1--可用 -2-删除
     *
     * @return status - 0-禁止登录 1--可用 -2-删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置0-禁止登录 1--可用 -2-删除
     *
     * @param status 0-禁止登录 1--可用 -2-删除
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * @param qq
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * @return res_id
     */
    public Long getResId() {
        return resId;
    }

    /**
     * @param resId
     */
    public void setResId(Long resId) {
        this.resId = resId;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}