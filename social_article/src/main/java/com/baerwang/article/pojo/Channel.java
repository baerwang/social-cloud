package com.baerwang.article.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/18 12:00
 */
@Entity
@Table(name = "tb_channel")
public class Channel implements Serializable {

    private static final long serialVersionUID = -3911471536668923023L;

    @Id
    private String id;
    /**
     * 频道名称
     */
    private String name;
    /**
     * 状态
     */
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
