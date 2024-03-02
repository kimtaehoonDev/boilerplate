package kimtaehoon.boilerplate.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

// TODO 3. 프로퍼티를 자바 클래스로 매핑한다.
@ConfigurationProperties(prefix = "test")
@SuppressWarnings("java:S6218")
public record TestProperties(
    String str,
    int[] ary
) {

}
