package cn.ap2ad.es.controller;

import cn.ap2ad.es.entity.Comment;
import cn.ap2ad.es.repository.CommentRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/es/comment")
public class CommentController {

    private CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public Comment getComment(@RequestParam(value = "id") String id) {
        return commentRepository.findOne(id);
    }

    @PostMapping
    public String postComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }

    @GetMapping(value = "search")
    public PageImpl<Comment> getCommentsByFragments(
            @RequestParam(name = "fragment") String fragment,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        QueryBuilder queryBuilder = new MatchQueryBuilder("_all", fragment);//match the full content
        Pageable pageable = new PageRequest(page, size);
        PageImpl<Comment> facetPages = (PageImpl<Comment>) commentRepository.search(queryBuilder, pageable);

        //warning: repair spring boot here, where should be removed after the bug officially repaired.
        return new PageImpl<>(facetPages.getContent(), pageable, facetPages.getTotalElements());
    }

    @GetMapping(value = "all")
    public PageImpl<Comment> getComments(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        Pageable pageable = new PageRequest(page, size);
        PageImpl<Comment> facetPages = (PageImpl<Comment>) commentRepository.findAll(pageable);

        //warning: repair spring boot here, where should be removed after the bug officially repaired.
        return new PageImpl<>(facetPages.getContent(), pageable, facetPages.getTotalElements());
    }
}
