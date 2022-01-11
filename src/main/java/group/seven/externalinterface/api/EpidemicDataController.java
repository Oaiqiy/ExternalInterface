package group.seven.externalinterface.api;

import group.seven.externalinterface.data.EpidemicDataRepo;
import group.seven.externalinterface.domain.EpidemicData;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external")
@AllArgsConstructor
public class EpidemicDataController {
    private EpidemicDataRepo epidemicDataRepo;
    private RedisTemplate<String,Object> objectRedisTemplate;

    /**
     * /external/epidemic/data
     * @return epidemic data
     */
    @GetMapping("/epidemic/data")
    public EpidemicData getEpidemicData(){
        EpidemicData epidemicData = (EpidemicData) objectRedisTemplate.opsForValue().get("epidemic:data");
        if(epidemicData == null)
            epidemicData = epidemicDataRepo.findFirstByOrderByDate();
        return epidemicData;
    }
}
