package group.seven.externalinterface.refresh;

import group.seven.externalinterface.data.*;
import group.seven.externalinterface.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CacheData {
    private RedisTemplate<String,Object> objectRedisTemplate;
    private RedisTemplate<String,String> stringRedisTemplate;
    private EpidemicDataRepo epidemicDataRepo;
    private EpidemicNewsRepo epidemicNewsRepo;
    private NucleicAcidDetectionPointRepo nucleicAcidDetectionPointRepo;
    private TravelPolicyRepo travelPolicyRepo;
    private VaccinationPointRepo vaccinationPointRepo;
    private List<Integer> adcodes;
    private PythonConfig pythonConfig;

    public CacheData(PythonConfig pythonConfig,RedisTemplate<String,Object> redisTemplate, RedisTemplate<String,String> redisTemplate2,AdcodeRepo adcodeRepo, EpidemicDataRepo epidemicDataRepo, EpidemicNewsRepo epidemicNewsRepo, NucleicAcidDetectionPointRepo nucleicAcidDetectionPointRepo, TravelPolicyRepo travelPolicyRepo, VaccinationPointRepo vaccinationPointRepo) {
        this.pythonConfig = pythonConfig;
        this.objectRedisTemplate = redisTemplate;
        this.stringRedisTemplate = redisTemplate2;
        this.epidemicDataRepo = epidemicDataRepo;
        this.epidemicNewsRepo = epidemicNewsRepo;
        this.nucleicAcidDetectionPointRepo = nucleicAcidDetectionPointRepo;
        this.travelPolicyRepo = travelPolicyRepo;
        this.vaccinationPointRepo = vaccinationPointRepo;
        adcodes = adcodeRepo.findAll().stream().map(Adcode::getAdcode).collect(Collectors.toList());
    }


    @Scheduled(cron = "0 * 2 * * ?")
    public void refresh(){
        log.info("refresh begin");

        String command = pythonConfig.getPythonPath()+" src/main/java/group/seven/externalinterface/refresh/main.py ";
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }


        epidemicDataCache();
        epidemicNewsCache();
        nucleicAcidDetectionPointCache();
        travelPolicyCache();
        vaccinationPointCache();
        log.info("refresh end");
    }

    @Bean
    public CommandLineRunner init(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                if(!pythonConfig.getIfInit()){
                    log.info("not init");
                    return;
                }

                log.info("init begin");
                objectRedisTemplate.delete("news");
                stringRedisTemplate.opsForValue().set("news:count","0");
                stringRedisTemplate.opsForValue().set("news:index","0");
//                epidemicDataCache();
                epidemicNewsCache();
//                nucleicAcidDetectionPointCache();
//                travelPolicyCache();
//                vaccinationPointCache();

                log.info("init end");

            }
        };
    }


    public void epidemicDataCache(){
        objectRedisTemplate.opsForValue().set("epidemic:data",epidemicDataRepo.findFirstByOrderByDate());
    }

    public void epidemicNewsCache(){
        Long index = Long.valueOf(stringRedisTemplate.opsForValue().get("news:index"));
        List<EpidemicNews> list = epidemicNewsRepo.findEpidemicNewsByIndexGreaterThanOrderByIndexAsc(index);
        index = list.get(list.size()-1).getIndex();
        objectRedisTemplate.opsForList().leftPushAll("news", list.stream().map(p->(Object) p).collect(Collectors.toList()));

        objectRedisTemplate.opsForList().trim("news",0,99);
        stringRedisTemplate.opsForValue().set("news:count",String.valueOf(epidemicNewsRepo.count()));
        stringRedisTemplate.opsForValue().set("news:index",String.valueOf(index));

    }

    public void nucleicAcidDetectionPointCache(){

        for(var adcode : adcodes){
            List<NucleicAcidDetectionPoint> points = nucleicAcidDetectionPointRepo.findNucleicAcidDetectionPointsByAdcode(adcode);
            Set<ZSetOperations.TypedTuple<Object>> tuples = points.stream().map(p->new DefaultTypedTuple<Object>(p,p.getIndex().doubleValue())).collect(Collectors.toSet());
            objectRedisTemplate.delete(adcode+":n");
            if(!tuples.isEmpty())
            objectRedisTemplate.opsForZSet().add(adcode+":n",tuples);
        }

    }

    public void travelPolicyCache(){
        int page =0;
        List<TravelPolicy> travelPolicies = travelPolicyRepo.findAll(PageRequest.of(page,100));
        while (travelPolicies!=null){
            for(var policy:travelPolicies){
                objectRedisTemplate.opsForValue().set(policy.getAdcode()+":t",policy);
            }
            page++;
            travelPolicies = travelPolicyRepo.findAll(PageRequest.of(page,100));
        }
    }

    public void vaccinationPointCache(){
        for(var adcode : adcodes){
            List<VaccinationPoint> points = vaccinationPointRepo.findByAdcode(adcode);
            Set<ZSetOperations.TypedTuple<Object>> tuples = points.stream().map(p-> new DefaultTypedTuple<Object>(p,p.getIndex().doubleValue())).collect(Collectors.toSet());
            objectRedisTemplate.delete(adcode+":v");
            if(!tuples.isEmpty())
                objectRedisTemplate.opsForZSet().add(adcode+":v",tuples);
        }
    }

}
