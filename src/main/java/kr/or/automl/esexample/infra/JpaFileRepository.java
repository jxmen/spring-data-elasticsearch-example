package kr.or.automl.esexample.infra;

import kr.or.automl.esexample.domain.file.File;
import kr.or.automl.esexample.domain.file.FileRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFileRepository extends FileRepository, JpaRepository<File, Long> {
}
