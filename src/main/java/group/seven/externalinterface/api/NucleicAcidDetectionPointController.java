package group.seven.externalinterface.api;

import group.seven.externalinterface.data.NucleicAcidDetectionPointRepo;
import group.seven.externalinterface.domain.NucleicAcidDetectionPoint;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/external")
public abstract class NucleicAcidDetectionPointController {
    private NucleicAcidDetectionPointRepo repo;
    private RedisTemplate<String,Object> objectRedisTemplate;

    /**
     * /external/nucleic
     * @param adcode adcode
     * @param count count
     * @param page page
     * @return List of nucleic acid detection points
     */
    @GetMapping("/nucleic")
    public Set<Object> getPoints(Integer adcode, Integer count, Integer page){
        var points = objectRedisTemplate.opsForZSet().range(adcode+":n",count*page,count*(page+1)-1);
        if(points!=null)
            return points;
        return repo.getNucleicAcidDetectionPointsByAdcode(adcode,PageRequest.of(page,count)).stream().map(p->(Object) p).collect(Collectors.toSet());
    }

    /**
     * /external/nucleic/all
     * @param count count
     * @param page page
     * @return List of nucleic acid detection points
     */
    @GetMapping("/nucleic/all")
    public List<NucleicAcidDetectionPoint> getAllPoints(Integer count,Integer page){
        return repo.findAll( PageRequest.of(page,count));
    }


}
