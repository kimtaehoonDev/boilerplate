package kimtaehoon.boilerplate.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "test")
@SuppressWarnings("java:S6218")
public record TestProperties(
    String str,
    int[] ary
) {

}
