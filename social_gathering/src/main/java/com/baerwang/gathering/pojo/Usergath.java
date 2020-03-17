package com.baerwang.gathering.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户关注活动(TbUsergath)实体类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 12:36
 */
@Entity
@Table(name = "tb_usergath")
public class Usergath implements Serializable {
    private static final long serialVersionUID = -27350948126814671L;
    /**
     * 用户ID
     */
    @Id
    private String userid;
    /**
     * 活动ID
     */
    private String gathid;
    /**
     * 点击时间
     */
    private Date exetime;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGathid() {
        return gathid;
    }

    public void setGathid(String gathid) {
        this.gathid = gathid;
    }

    public Date getExetime() {
        return exetime;
    }

    public void setExetime(Date exetime) {
        this.exetime = exetime;
    }

}