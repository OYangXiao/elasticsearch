package cn.ap2ad.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Document(indexName = "article", type = "article")
@Setting(settingPath = "/article/settings.json")
@Mapping(mappingPath = "/article/mappings.json")
public class Article implements Serializable {
    @Id
    private String id;
    private String title;
    private Author author;
    private String type;
    private String major;
    private Map<String, Float> grades;
    private String content;
    private Collection<String> tags = new ArrayList<>();

    public Article() {
    }

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Map<String, Float> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, Float> grades) {
        this.grades = grades;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }
}

