package kimtaehoon.boilerplate.properties;

import static org.assertj.core.api.Assertions.assertThat;

import kimtaehoon.boilerplate.common.config.ScanningPropertiesConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

// TODO 4. 테스트 작성
@Import({ScanningPropertiesConfig.class})
@SpringBootTest
class TestPropertiesTest {

    @Autowired
    TestProperties properties;

    @Test
    @DisplayName("프로퍼티들이 잘 입력되었는지 확인한다.")
    void test() throws Exception {
        // when & then
        assertThat(properties.str()).isEqualTo("문자열");
        assertThat(properties.ary()).hasSize(3)
            .containsExactly(1, 10, 100);
    }
}