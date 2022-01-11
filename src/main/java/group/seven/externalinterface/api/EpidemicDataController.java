package group.seven.externalinterface.api;

import group.seven.externalinterface.data.EpidemicDataRepo;
import group.seven.externalinterface.domain.EpidemicData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external")
@AllArgsConstructor
public class EpidemicDataController {
    private EpidemicDataRepo epidemicDataRepo;

    /**
     * /external/epidemic/data
     * @return epidemic data
     */
    @GetMapping("/epidemic/data")
    public EpidemicData getEpidemicData(){
        return epidemicDataRepo.findAll().get(0);
    }
}
