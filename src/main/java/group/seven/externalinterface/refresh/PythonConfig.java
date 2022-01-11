package group.seven.externalinterface.refresh;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external")
@Data
public class PythonConfig {
    private String pythonPath;
    private Boolean ifInit;
}
