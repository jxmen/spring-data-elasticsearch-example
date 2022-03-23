package kr.or.automl.esexample.application;

import kr.or.automl.esexample.infra.StubFileRepository;
import kr.or.automl.esexample.domain.file.File;
import kr.or.automl.esexample.domain.file.search.FileSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileServiceTest {
    private FileService fileService;
    private FileSearchRepository mockFileSearchRepository = mock(FileSearchRepository.class);

    @BeforeEach
    void setUp() {
        StubFileRepository stubFileRepository = new StubFileRepository();

        this.fileService = new FileService(stubFileRepository, mockFileSearchRepository);
    }

    @Nested
    class getFiles_메서드는 {

        @Test
        void 모든_파일을_리턴한다() {
            List<File.Response> files = fileService.getFiles();

            assertThat(files).hasSize(1);
        }
    }

    @Nested
    class create_메서드는 {

        @Test
        void 저장된_파일_아이디를_리턴한다() {
            File.CreateRequest fileCreateRequest = File.CreateRequest.builder()
                    .build();

            Long id = fileService.create(fileCreateRequest);

            assertThat(id).isEqualTo(1L);
        }
    }

    @Nested
    class searchFrom_메서드는 {
        private Pageable pageable;

        @BeforeEach
        void setUp() {
            this.pageable = Pageable.unpaged();
        }

        @Nested
        class 존재하는_키워드일_경우 {
            private String existKeyword = "a.csv";

            @BeforeEach
            void setUp() {
                when(mockFileSearchRepository.search(eq(existKeyword), any(Pageable.class)))
                        .thenReturn(List.of(
                                TestFileFactory.create()
                        ));
            }

            @Test
            void 한개_이상의_파일을_리턴한다() {
                List<File.Response> responses = fileService.searchFrom(existKeyword, pageable);

                assertThat(responses).hasSizeGreaterThan(0);
            }
        }

        @Nested
        class 존재하지_않는_키워드일_경우 {
            private String notExistKeyword = "xxx";

            @BeforeEach
            void setUp() {
                when(mockFileSearchRepository.search(eq(notExistKeyword), any(Pageable.class)))
                        .thenReturn(List.of());
            }

            @Test
            void 빈_파일을_리턴한다() {
                List<File.Response> responses = fileService.searchFrom(notExistKeyword, pageable);

                assertThat(responses).hasSize(0);
            }
        }

    }
}
