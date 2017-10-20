package cn.ap2ad.es.controller;

import cn.ap2ad.es.entity.Article;
import cn.ap2ad.es.repository.ArticleRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/es/article")
public class ArticleController {

    private ArticleRepository articleRepository;
    private ElasticsearchOperations operations;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public Article getArticle(@RequestParam(value = "id") String id) {
        return articleRepository.findOne(id);
    }

    @PostMapping
    public String postArticle(@RequestBody Article article) {
        articleRepository.save(article);
        return article.getId();
    }

    @GetMapping(value = "search")
    public PageImpl<Article> getArticleByFragment(
            @RequestParam(name = "fragment") String fragment,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "type") String type,
            //专业类型
            @RequestParam(name = "major", required = false) String major,
            //成绩类型
            @RequestParam(name = "gt", required = false) List<String> gradeType,
            //成绩范围，from & to
            @RequestParam(name = "gvf", required = false) List<Float> gradeFrom,
            @RequestParam(name = "gvt", required = false) List<Float> gradeTo
    ) {
        QueryBuilder queryBuilder = new MatchQueryBuilder("_all", fragment);//match the full content
        Pageable pageable = new PageRequest(page, size);
        PageImpl<Article> facetPages = (PageImpl<Article>) articleRepository.search(queryBuilder, pageable);

        System.out.println(type);
        System.out.println(major);
        if(gradeType != null){
            int gradeLength = gradeType.size();
            if (gradeLength == gradeFrom.size() && gradeLength == gradeTo.size()) {
                for (int i = 0; i < gradeLength; i++) {
                    System.out.println(gradeType.get(i));
                    System.out.println(gradeFrom.get(i));
                    System.out.println(gradeTo.get(i));
                }
            }
        }

        //warning: repair spring boot here, where should be removed after the bug officially repaired.
        return new PageImpl<>(facetPages.getContent(), pageable, facetPages.getTotalElements());
    }

    @GetMapping(value = "all")
    public PageImpl<Article> getArticles(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        Pageable pageable = new PageRequest(page, size);
        PageImpl<Article> facetPages = (PageImpl<Article>) articleRepository.findAll(pageable);

        //warning: repair spring boot here, where should be removed after the bug officially repaired.
        return new PageImpl<>(facetPages.getContent(), pageable, facetPages.getTotalElements());
    }
}
