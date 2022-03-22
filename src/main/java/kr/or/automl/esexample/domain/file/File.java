package kr.or.automl.esexample.domain.file;

import antlr.ANTLRParser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "files")
@Entity
public class File {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String size;

    @Embedded
    private Meta meta;

    @PersistenceConstructor
    public File(String name, String size, Meta meta) {
        this.name = name;
        this.size = size;
        this.meta = meta;
    }

    @Builder
    @PersistenceConstructor
    public File(Long id, String name, String size, Meta meta) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.meta = meta;
    }

    public static File from(CreateRequest fileCreateRequest) {
        return new File(
                fileCreateRequest.name,
                fileCreateRequest.size,
                new Meta(
                        fileCreateRequest.mainDivision,
                        fileCreateRequest.subDivision,
                        fileCreateRequest.offerPeriod,
                        fileCreateRequest.aggregationCycle,
                        fileCreateRequest.offerCycle,
                        fileCreateRequest.structure
                )
        );
    }

    @Getter
    public static class CreateRequest {
        private final String name;
        private final String size;

        private final String mainDivision;
        private final String subDivision;
        private final String offerPeriod;
        private final String aggregationCycle;
        private final String offerCycle;
        private final String structure;

        @Builder
        public CreateRequest(
                String name,
                String size,
                String mainDivision,
                String subDivision,
                String offerPeriod,
                String aggregationCycle,
                String offerCycle,
                String structure
        ) {
            this.name = name;
            this.size = size;
            this.mainDivision = mainDivision;
            this.subDivision = subDivision;
            this.offerPeriod = offerPeriod;
            this.aggregationCycle = aggregationCycle;
            this.offerCycle = offerCycle;
            this.structure = structure;
        }
    }

    @Getter
    public static class Response {
        private final String name;
        private final String size;

        private final String mainDivision;
        private final String subDivision;
        private final String offerPeriod;
        private final String aggregationCycle;
        private final String offerCycle;
        private final String structure;

        @Builder
        public Response(String name, String size, String mainDivision, String subDivision, String offerPeriod, String aggregationCycle, String offerCycle, String structure) {
            this.name = name;
            this.size = size;
            this.mainDivision = mainDivision;
            this.subDivision = subDivision;
            this.offerPeriod = offerPeriod;
            this.aggregationCycle = aggregationCycle;
            this.offerCycle = offerCycle;
            this.structure = structure;
        }

        public static Response from(File file) {
            return new Response(
                    file.name,
                    file.size,
                    file.meta.getMainDivision(),
                    file.meta.getSubDivision(),
                    file.meta.getOfferPeriod(),
                    file.meta.getAggregationCycle(),
                    file.meta.getOfferCycle(),
                    file.meta.getStructure()
            );
        }
    }
}
