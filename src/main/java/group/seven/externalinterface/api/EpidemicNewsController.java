package group.seven.externalinterface.api;

import group.seven.externalinterface.data.EpidemicNewsRepo;
import group.seven.externalinterface.domain.EpidemicNews;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/external")
public class EpidemicNewsController {
    private EpidemicNewsRepo epidemicNewsRepo;
    private RedisTemplate<String,String> stringStringRedisTemplate;
    private RedisTemplate<String,Object> objectRedisTemplate;


    /**
     * /external/epidemic/news
     * @param count count
     * @param page page
     * @return List of epidemic news
     */
    @GetMapping("/epidemic/news")
    public List<Object> getNews(Integer count, Integer page){
        if(count*(page+1) > 100)
            return  epidemicNewsRepo.findAll(PageRequest.of(page,count)).toList().stream().map(p->(Object) p).collect(Collectors.toList());
        else
            return  objectRedisTemplate.opsForList().range("news",count*page,count*(page+1)-1);
    }

    /**
     * /external/epidemic/news/count
     * @return count
     */
    @GetMapping("/epidemic/news/count")
    public String getCount(){
        return stringStringRedisTemplate.opsForValue().get("news:count");
    }




}
