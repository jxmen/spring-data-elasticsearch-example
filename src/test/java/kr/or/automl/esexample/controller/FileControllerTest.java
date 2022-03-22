package kr.or.automl.esexample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.automl.esexample.application.TestFileFactory;
import kr.or.automl.esexample.domain.file.File;
import kr.or.automl.esexample.domain.file.FileRepository;
import kr.or.automl.esexample.domain.file.search.FileSearchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileSearchRepository fileSearchRepository;

    @BeforeEach
    void setUp() {
        fileSearchRepository.deleteAll();
        fileRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        fileSearchRepository.deleteAll();
        fileRepository.deleteAll();
    }

    private String toJson(Object it) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(it);
    }

    @Nested
    class GET_files_요청은 {
        private final MockHttpServletRequestBuilder REQUEST = get("/files");
        private final File.Response FILE_RESPONSE = File.Response.builder()
                .name("name")
                .size("size")
                .mainDivision("mainDivision")
                .subDivision("subDivision")
                .offerPeriod("offerPeriod")
                .aggregationCycle("aggregationCycle")
                .offerCycle("offerCycle")
                .structure("structure")
                .build();

        @BeforeEach
        void setUp() {
            File file = TestFileFactory.create();
            fileRepository.save(file);
        }

        @Test
        void 전체_파일목록을_리턴한다() throws Exception {
            mockMvc.perform(REQUEST)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value(FILE_RESPONSE.getName()))
                    .andExpect(jsonPath("$[0].mainDivision").value(FILE_RESPONSE.getMainDivision()))
                    .andExpect(jsonPath("$[0].subDivision").value(FILE_RESPONSE.getSubDivision()))
                    .andExpect(jsonPath("$[0].offerPeriod").value(FILE_RESPONSE.getOfferPeriod()))
                    .andExpect(jsonPath("$[0].aggregationCycle").value(FILE_RESPONSE.getAggregationCycle()))
                    .andExpect(jsonPath("$[0].offerCycle").value(FILE_RESPONSE.getOfferCycle()))
                    .andExpect(jsonPath("$[0].structure").value(FILE_RESPONSE.getStructure()));
        }
    }

    @Nested
    class POST_files_요청은 {
        private final MockHttpServletRequestBuilder REQUEST = post("/files")
                .contentType(MediaType.APPLICATION_JSON);
        private final File.CreateRequest FILE_CREATE_REQUEST = File.CreateRequest.builder()
                .name("name")
                .size("size")
                .mainDivision("mainDivision")
                .subDivision("subDivision")
                .offerPeriod("offerPeriod")
                .aggregationCycle("aggregationCycle")
                .offerCycle("offerCycle")
                .structure("structure")
                .build();

        @Test
        void status_201을_응답한다() throws Exception {
            mockMvc.perform(REQUEST.content(toJson(FILE_CREATE_REQUEST)))
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    class GET_files_keyword_요청은 {

        @BeforeEach
        void setUp() {
            File file = TestFileFactory.create();
            fileRepository.save(file);
            fileSearchRepository.save(file);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "name",
                "size",
                "mainDivision",
                "subDivision",
                "offerPeriod",
                "aggregationCycle",
                "offerCycle",
                "structure"
        })
        void 키워드_검색결과를_리턴한다(String keyword) throws Exception {
            mockMvc.perform(get(String.format("/files/%s", keyword)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)));
        }
    }
}
