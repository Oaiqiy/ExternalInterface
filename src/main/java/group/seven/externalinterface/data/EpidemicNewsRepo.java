package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.EpidemicNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EpidemicNewsRepo extends CrudRepository<EpidemicNews,Long> {

    Page<EpidemicNews> findAll(Pageable pageable);
    List<EpidemicNews> findEpidemicNewsByIndexGreaterThanOrderByIndexAsc(Long low);
}
