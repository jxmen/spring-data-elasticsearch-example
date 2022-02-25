package kr.or.automl.esexample.domain.user.search;

import kr.or.automl.esexample.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserSearchRepositoryImpl implements CustomUserSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    public CustomUserSearchRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<User> searchByName(String name, Pageable pageable) {
        Criteria criteria = Criteria.where("basicProfile.name").contains(name);
        Query query = new CriteriaQuery(criteria).setPageable(pageable);
        SearchHits<User> search = elasticsearchOperations.search(query, User.class);

        return search.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
