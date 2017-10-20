package cn.ap2ad.es.repository;

import cn.ap2ad.es.entity.Tag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TagRepository extends ElasticsearchRepository<Tag, String>{

}
