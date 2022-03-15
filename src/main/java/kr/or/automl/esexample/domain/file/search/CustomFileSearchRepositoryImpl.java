package kr.or.automl.esexample.domain.file.search;

import kr.or.automl.esexample.domain.file.File;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomFileSearchRepositoryImpl implements CustomFileSearchRepository {
    private static final List<String> FIELDS = Arrays.asList(
            "name",
            "size",
            "meta.mainDivision",
            "meta.subDivision",
            "meta.offerPeriod",
            "meta.aggregationCycle",
            "meta.offerCycle",
            "meta.structure"
    );

    private final ElasticsearchOperations elasticsearchOperations;

    public CustomFileSearchRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<File> search(String keyword, Pageable pageable) {
        List<Query> queries = getQueries(keyword, pageable);

        List<SearchHits<File>> searchHits = elasticsearchOperations.multiSearch(queries, File.class);

        return searchHits.stream()
                .filter(it -> it.getTotalHits() != 0)
                .flatMap(it -> it.getSearchHits().stream()
                        .map(SearchHit::getContent))
                .collect(Collectors.toList());
    }

    private List<Query> getQueries(String keyword, Pageable pageable) {
        return FIELDS.stream()
                .map(field ->
                        (Query) new CriteriaQuery(
                                new Criteria(field).contains(keyword),
                                pageable
                        )
                )
                .collect(Collectors.toList());
    }

}
