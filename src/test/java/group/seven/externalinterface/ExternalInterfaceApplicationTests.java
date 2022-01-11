package group.seven.externalinterface;

import group.seven.externalinterface.data.EpidemicDataRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExternalInterfaceApplicationTests {
    @Autowired
    private EpidemicDataRepo dataRepo;
    @Test
    void contextLoads() {

    }

}
