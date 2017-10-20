package cn.ap2ad.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "comment", type = "comment", shards = 1, replicas = 0, refreshInterval = "-1")
public class Comment {
    @Id
    private String id;

    @Field(analyzer = "ik", store = true, type = FieldType.Object)
    private Author author;

    @Field(analyzer = "ik", store = true, type = FieldType.String)
    private String[] comments;

    @Field(analyzer = "ik", store = true, type = FieldType.Object)
    private Reply[] replies;

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }

    public Reply[] getReplies() {
        return replies;
    }

    public void setReplies(Reply[] replies) {
        this.replies = replies;
    }
}
