package kr.or.automl.esexample.application;

import kr.or.automl.esexample.domain.file.File;
import kr.or.automl.esexample.domain.file.Meta;

public class TestFileFactory {
    public static File create() {
        Meta meta = Meta.builder()
                .aggregationCycle("aggregationCycle")
                .mainDivision("mainDivision")
                .offerCycle("offerCycle")
                .offerPeriod("offerPeriod")
                .structure("structure")
                .subDivision("subDivision")
                .structure("structure")
                .build();

        return new File("name", "size", meta);
    }
}
