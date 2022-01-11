package group.seven.externalinterface.api;

import group.seven.externalinterface.data.EpidemicNewsRepo;
import group.seven.externalinterface.domain.EpidemicNews;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/external")
public class EpidemicNewsController {
    private EpidemicNewsRepo epidemicNewsRepo;

    /**
     * /external/epidemic/news
     * @param count count
     * @param page page
     * @return List of epidemic news
     */
    @GetMapping("/epidemic/news")
    public List<EpidemicNews> getNews(Integer count,Integer page){
        return epidemicNewsRepo.findAll(PageRequest.of(page,count)).toList();
    }
}
