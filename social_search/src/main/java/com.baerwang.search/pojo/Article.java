package com.baerwang.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * 文章实体类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/19 21:09
 */
@Document(indexName = "social_article", type = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 6653004421758701536L;

    @Id
    private String id;
    /**
     * 标题
     * index=true（默认为true),是否索引，就是看该域是否能被搜索
     * analyzer : 是否分词，就表示搜索的时候是整体匹配还是单词匹配
     * searchAnalyzer : 是否存储，是否在页面上显示
     */
    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;
    /**
     * 文章正文
     */
    @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;
    /**
     * 审核状态
     */
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
