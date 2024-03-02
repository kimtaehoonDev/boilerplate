package kimtaehoon.boilerplate.common.config;

import kimtaehoon.boilerplate.BoilerplateApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = BoilerplateApplication.class)
public class ScanningPropertiesConfig {

}
