package kr.or.automl.esexample.domain.file;

import lombok.AccessLevel;
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

    public static class CreateRequest {
        private String name;
        private String size;

        private String mainDivision;
        private String subDivision;
        private String offerPeriod;
        private String aggregationCycle;
        private String offerCycle;
        private String structure;

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
        private String name;
        private String size;

        private String mainDivision;
        private String subDivision;
        private String offerPeriod;
        private String aggregationCycle;
        private String offerCycle;
        private String structure;

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
