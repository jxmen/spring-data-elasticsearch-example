package kr.or.automl.esexample.controller;

import kr.or.automl.esexample.application.FileService;
import kr.or.automl.esexample.domain.file.File;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<File.Response>> getFiles() {
        List<File.Response> fileResponses = fileService.getFiles();

        return ResponseEntity.ok(fileResponses);
    }

    @PostMapping
    public ResponseEntity<File.Response> createFile(
            @Valid @RequestBody File.CreateRequest fileCreateRequest
    ) {
        Long fileId = fileService.create(fileCreateRequest);
        URI uri = URI.create(String.valueOf(fileId));

        return ResponseEntity.created(uri)
                .build();
    }

    @GetMapping("/{keyword}")
    public ResponseEntity<List<File.Response>> searchFromKeyword(
            @PathVariable String keyword,
            Pageable pageable
    ) {
        List<File.Response> fileResponses = fileService.searchFrom(keyword, pageable);

        return ResponseEntity.ok(fileResponses);
    }
}

