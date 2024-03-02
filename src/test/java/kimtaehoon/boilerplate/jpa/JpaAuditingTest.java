package kimtaehoon.boilerplate.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import kimtaehoon.boilerplate.common.config.JpaAuditingConfig;
import kimtaehoon.boilerplate.common.entity.TestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Import({JpaAuditingConfig.class})
@Transactional
class JpaAuditingTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("엔티티에 BaseTimeEntity 상속이 잘 되었는지 확인한다.")
    void test() throws Exception {
        // given
        TestEntity testEntity = new TestEntity();

        // when
        em.persist(testEntity);
        em.flush();
        em.clear();

        // then
        TestEntity result = em.find(TestEntity.class, testEntity.getId());
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();
    }
}