package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.EpidemicNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EpidemicNewsRepo extends CrudRepository<EpidemicNews,Long> {

    /**
     * find all by page
     * @param pageable spring pageable
     * @return page of epidemic news
     */
    Page<EpidemicNews> findAll(Pageable pageable);

    /**
     * find epidemic news by index greater than, order by index asc
     * @param low the low index
     * @return list of epidemic news
     */
    List<EpidemicNews> findEpidemicNewsByIndexGreaterThanOrderByIndexAsc(Long low);
}
