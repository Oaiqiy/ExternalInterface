package group.seven.externalinterface.api;

import group.seven.externalinterface.data.NucleicAcidDetectionPointRepo;
import group.seven.externalinterface.domain.NucleicAcidDetectionPoint;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/external")
public class NucleicAcidDetectionPointController {
    private NucleicAcidDetectionPointRepo repo;

    /**
     * /external/nucleic
     * @param adcode adcode
     * @param count count
     * @param page page
     * @return List of nucleic acid detection points
     */
    @GetMapping("/nucleic")
    public List<NucleicAcidDetectionPoint> getPoints(Integer adcode, Integer count, Integer page){
        return repo.getNucleicAcidDetectionPointsByAdcode(adcode,  PageRequest.of(page,count));
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
