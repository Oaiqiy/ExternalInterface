package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.TravelPolicy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TravelPolicyRepo extends CrudRepository<TravelPolicy,Integer> {

    /**
     * find travel policy by adcode
     * @param adcode adcode
     * @return travel policy
     */
    TravelPolicy getTravelPolicyByAdcode(Integer adcode);

    /**
     * find all travel policies by page
     * @param pageable pageable
     * @return list of travel policies
     */
    List<TravelPolicy> findAll(Pageable pageable);
}
