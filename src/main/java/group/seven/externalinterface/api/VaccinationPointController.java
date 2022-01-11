package group.seven.externalinterface.api;

import group.seven.externalinterface.data.VaccinationPointRepo;
import group.seven.externalinterface.domain.VaccinationPoint;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/external")
@AllArgsConstructor
public class VaccinationPointController {
    private VaccinationPointRepo repo;


    /**
     * /external/vaccination
     * @param adcode adcode
     * @param count count
     * @param page page
     * @return List of vaccination points
     */
    @GetMapping("/vaccination")
    public List<VaccinationPoint> getPoints(Integer adcode,Integer count,Integer page){
        return repo.getVaccinationPointsByAdcode(adcode,PageRequest.of(page,count));
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
