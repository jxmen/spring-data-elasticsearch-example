# 모든 필드에 대해 검색 적용

모든 필드에 대해 검색할 것이기 때문에, 검색할 필드 리스트를 모두 가져옵니다.
```java
public class Foo {
    private static final List<String> FIELDS = new ArrayList<>();

    static {
        FIELDS.addAll(getFileFields());
        FIELDS.addAll(getFileEmbeddedFields());
    }
    
    // ... 기타 private 메서드
}
```

다음과 같은 방식으로 검색에 사용될 `Criteria`와 `Query`를 만들 수 있습니다.
```java
Criteria criteria = new Criteria(field).contains(keyword);

Query criteriaQuery = new CriteriaQuery(criteria).setPageable(pageable);
```

응용 - stream으로 필드에 키워드가 존재하는지에 대한 쿼리 리스트 만들기
```java
List<Query> quries = FIELDS.stream()
        .map(field -> createCriteria(field, keyword))
        .map(criteria -> createCriteriaQuery(criteria, pageable))
        .collect(Collectors.toList());
```

`elasticsearchOperations`의 `multisearch`메서드를 활용하여 쿼리 리스트를 넘기면 검색 결과를 가져올 수 있습니다.
```java
List<SearchHits<File>> searchHits = elasticsearchOperations.multiSearch(queries, File.class);
```

검색 결과로 `List<엔티티클래스>` 형태로 넘겨야 합니다. 
### 참고 링크
- [Tecoble - Spring Data Elasticsearch 설정 및 검색 기능 구현](https://tecoble.techcourse.co.kr/post/2021-10-19-elasticsearch/)
