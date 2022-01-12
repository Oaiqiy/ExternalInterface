package group.seven.externalinterface.api;

import group.seven.externalinterface.data.TravelPolicyRepo;
import group.seven.externalinterface.domain.TravelPolicy;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/external")
@AllArgsConstructor
public class TravelPolicyController {
    private TravelPolicyRepo repo;
    private RedisTemplate<String,Object> objectRedisTemplate;

    /**
     * /external/travel
     * @param adcode adcode
     * @return travel policy
     */
    @GetMapping("/travel")
    public TravelPolicy getCityPolicy(Integer adcode){
        return repo.getTravelPolicyByAdcode(adcode);
    }

    /**
     * /external/travel/route
     * @param from from adcode
     * @param to to adcode
     * @return two travel policies
     */

    @GetMapping("/travel/route")
    public List<TravelPolicy> route(Integer from,Integer to){
        List<TravelPolicy> result = new LinkedList<>();
        result.add((TravelPolicy) objectRedisTemplate.opsForValue().get(from.toString()+":t"));
        result.add((TravelPolicy) objectRedisTemplate.opsForValue().get(to.toString()+":t"));
        return result;
    }


}
