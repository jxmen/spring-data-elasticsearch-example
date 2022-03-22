package kr.or.automl.esexample.application.doubles;

import kr.or.automl.esexample.domain.file.File;
import kr.or.automl.esexample.domain.file.FileRepository;
import kr.or.automl.esexample.domain.file.Meta;

import java.util.List;

public class StubFileRepository implements FileRepository {
    private static final Meta META = Meta.builder()
            .mainDivision("mainDivision")
            .subDivision("subDivision")
            .offerPeriod("offerPeriod")
            .aggregationCycle("aggregationCycle")
            .offerCycle("offerCycle")
            .structure("structure")
            .build();
    private static final File FILE = File.builder()
            .id(1L)
            .name("name")
            .size("size")
            .meta(META)
            .build();

    @Override
    public File save(File file) {
        return FILE;
    }

    @Override
    public List<File> findAll() {
        return List.of(FILE);
    }

    @Override
    public void deleteAll() {

    }
}
