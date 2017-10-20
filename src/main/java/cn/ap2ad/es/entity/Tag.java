package cn.ap2ad.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "tag", type = "tag")
@Setting(settingPath = "/tag/settings.json")
@Mapping(mappingPath = "/tag/mappings.json")
public class Tag {
    @Id
    private String id;
    private String content;

    public Tag() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
