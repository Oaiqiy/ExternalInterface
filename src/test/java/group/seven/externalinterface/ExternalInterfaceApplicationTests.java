package group.seven.externalinterface;

import group.seven.externalinterface.data.AdcodeRepo;
import group.seven.externalinterface.data.EpidemicDataRepo;
import group.seven.externalinterface.data.NucleicAcidDetectionPointRepo;
import group.seven.externalinterface.domain.Adcode;
import group.seven.externalinterface.domain.NucleicAcidDetectionPoint;
import group.seven.externalinterface.domain.VaccinationPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class ExternalInterfaceApplicationTests {
    @Autowired
    private EpidemicDataRepo dataRepo;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private NucleicAcidDetectionPointRepo nucleicAcidDetectionPointRepo;

    @Autowired
    private AdcodeRepo adcodeRepo;
    @Test void contextLoads() {

        List<Adcode> adcodes = adcodeRepo.findAll();




//        EpidemicData epidemicData = dataRepo.findAll().get(0);
//        epidemicData = (EpidemicData) redisTemplate.opsForValue().get("epidemicData");
//        System.out.println(epidemicData.getConfirmedCount());


       for(var x : adcodes){
           String key = x.getAdcode()+":"+"n";
           List<NucleicAcidDetectionPoint> points = nucleicAcidDetectionPointRepo.findNucleicAcidDetectionPointsByAdcode(x.getAdcode());
           Set<ZSetOperations.TypedTuple<Object>> tuples = new LinkedHashSet<>();

           Map<String,NucleicAcidDetectionPoint> map = points.stream().collect(Collectors.toMap(p->p.getIndex().toString(),p->p));
           redisTemplate.opsForHash().putAll("nucleic",map);

           for(var y:points){
               tuples.add(new DefaultTypedTuple<>(y,y.getIndex().doubleValue()));
           }

           redisTemplate.delete(key);
           if(!tuples.isEmpty())
                redisTemplate.opsForZSet().add(key,tuples);
           else
               redisTemplate.opsForZSet().add(key,null,-1);

       }

       Set<NucleicAcidDetectionPoint> pointSet =  (Set<NucleicAcidDetectionPoint>) (Set<?>) redisTemplate.opsForZSet().range("110101:n",0,12220);


       System.out.println("aaa");
        for(var x:pointSet){
            System.out.println(x);
            NucleicAcidDetectionPoint point = (NucleicAcidDetectionPoint) x;
           System.out.println(point.getName());
        }

        System.out.println("fasdfas");

        //redisTemplate.opsForValue().set("epidemicData",epidemicData);
        //redisTemplate.opsForZSet().rangeByLex("fasdf",new RedisZSetCommands.Range().gt("fadsf"),new RedisZSetCommands.Limit().count(10));

    }

}
