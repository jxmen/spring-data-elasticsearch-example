package kr.or.automl.esexample.application;

import kr.or.automl.esexample.domain.file.File;
import kr.or.automl.esexample.domain.file.FileRepository;
import kr.or.automl.esexample.domain.file.search.FileSearchRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final FileSearchRepository fileSearchRepository;

    public FileService(FileRepository fileRepository, FileSearchRepository fileSearchRepository) {
        this.fileRepository = fileRepository;
        this.fileSearchRepository = fileSearchRepository;
    }

    public List<File.Response> getFiles() {
        List<File> files = fileRepository.findAll();

        return files.stream()
                .map(File.Response::from)
                .collect(Collectors.toList());
    }

    public Long create(File.CreateRequest fileCreateRequest) {
        File file = File.from(fileCreateRequest);

        File savedFile = fileRepository.save(file);
        fileSearchRepository.save(file);

        return savedFile.getId();
    }

    public List<File.Response> searchFrom(String keyword, Pageable pageable) {
        return fileSearchRepository.search(keyword, pageable);
    }
}
