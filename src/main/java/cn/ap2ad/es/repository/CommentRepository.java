package cn.ap2ad.es.repository;


import cn.ap2ad.es.entity.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CommentRepository extends ElasticsearchRepository<Comment, String> {
}
