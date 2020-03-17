package com.baerwang.base.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 标签(TbLabel)实体类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020-02-14 10:59:29
 */
@Entity
@Table(name = "tb_label")
public class Label implements Serializable {

    private static final long serialVersionUID = 133252521788834243L;

    /**
     * 标签ID
     */
    @Id
    private String id;
    /**
     * 标签名称
     */
    private String labelname;
    /**
     * 状态
     */
    private String state;
    /**
     * 使用数量
     */
    private Long count;
    /**
     * 是否推荐
     */
    private String recommend;
    /**
     * 粉丝数
     */
    private Long fans;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Long getFans() {
        return fans;
    }

    public void setFans(Long fans) {
        this.fans = fans;
    }
}