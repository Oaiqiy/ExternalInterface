package group.seven.externalinterface.api;

import group.seven.externalinterface.data.VaccinationPointRepo;
import group.seven.externalinterface.domain.VaccinationPoint;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/external")
@AllArgsConstructor
public class VaccinationPointController {
    private VaccinationPointRepo repo;
    private RedisTemplate<String,Object> objectRedisTemplate;


    /**
     * /external/vaccination
     * @param adcode adcode
     * @param count count
     * @param page page
     * @return List of vaccination points
     */
    @GetMapping("/vaccination")
    public Set<Object> getPoints(Integer adcode, Integer count, Integer page){
        return objectRedisTemplate.opsForZSet().range(adcode.toString()+":v",count*page,count*(page+1)-1);
        //return repo.getVaccinationPointsByAdcode(adcode,PageRequest.of(page,count));

    }

    /**
     * /external/vaccination/all
     * @param count count
     * @param page page
     * @return List of vaccination points
     */
    @GetMapping("/vaccination/all")
    public List<VaccinationPoint> getAllPoints(Integer count,Integer page){
        return repo.findAll( PageRequest.of(page,count));
    }

}
