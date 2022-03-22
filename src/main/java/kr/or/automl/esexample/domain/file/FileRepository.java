package kr.or.automl.esexample.domain.file;

import java.util.List;

public interface FileRepository {
    File save(File file);

    List<File> findAll();

    void deleteAll();
}
