package kr.or.automl.esexample.domain.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Meta {
    private String mainDivision;
    private String subDivision;
    private String offerPeriod;
    private String aggregationCycle;
    private String offerCycle;
    private String structure;

    public Meta(String mainDivision, String subDivision, String offerPeriod, String aggregationCycle, String offerCycle, String structure) {
        this.mainDivision = mainDivision;
        this.subDivision = subDivision;
        this.offerPeriod = offerPeriod;
        this.aggregationCycle = aggregationCycle;
        this.offerCycle = offerCycle;
        this.structure = structure;
    }

}
