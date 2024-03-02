package kimtaehoon.boilerplate.common.config;

import kimtaehoon.boilerplate.BoilerplateApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

// TODO 1. PropertiesScan Configuration 클래스를 설정한다.
@Configuration
@ConfigurationPropertiesScan(basePackageClasses = BoilerplateApplication.class)
public class ScanningPropertiesConfig {

}
