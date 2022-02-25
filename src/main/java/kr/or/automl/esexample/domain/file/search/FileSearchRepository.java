package kr.or.automl.esexample.domain.file.search;

import kr.or.automl.esexample.domain.file.File;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FileSearchRepository extends ElasticsearchRepository<File, Long>, CustomFileSearchRepository {
}
