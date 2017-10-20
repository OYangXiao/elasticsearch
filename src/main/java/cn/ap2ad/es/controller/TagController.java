package cn.ap2ad.es.controller;

import cn.ap2ad.es.entity.Tag;
import cn.ap2ad.es.repository.TagRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/es/tag")
public class TagController {

    private TagRepository tagRepository;

    @Autowired
    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public Tag getTag(@RequestParam(value = "id") String id){
        return tagRepository.findOne(id);
    }

    @PostMapping
    public String postTag(@RequestBody Tag tag){
        tagRepository.save(tag);
        return tag.getId();
    }

    @GetMapping(value = "all")
    public PageImpl<Tag> getTags(
            @RequestParam(name = "page")int page,
            @RequestParam(name = "size")int size
    ){
        Pageable pageable = new PageRequest(page, size);
        PageImpl<Tag> facetPages = (PageImpl<Tag>)tagRepository.findAll(pageable);

        //warning: repair spring boot here, where should be removed after the bug officially repaired.
        return new PageImpl<>(facetPages.getContent(),pageable,facetPages.getTotalElements());
    }

    @GetMapping(value = "search")
    public PageImpl<Tag> getTagsByFragments (
            @RequestParam(name = "fragment") String fragment,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        QueryBuilder queryBuilder = new MatchQueryBuilder("_all", fragment);
        Pageable pageable = new PageRequest(page, size);
        PageImpl<Tag> facetPages = (PageImpl<Tag>) tagRepository.search(queryBuilder, pageable);

        //warning: repair spring boot here, where should be removed after the bug officially repaired.
        return new PageImpl<>(facetPages.getContent(), pageable, facetPages.getTotalElements());
    }
}
