package kr.or.automl.esexample.domain.file.search;

import kr.or.automl.esexample.domain.file.File;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomFileSearchRepository {
    List<File.Response> search(String keyword, Pageable pageable);
}
