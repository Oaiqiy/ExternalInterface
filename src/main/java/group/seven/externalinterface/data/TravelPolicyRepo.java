package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.TravelPolicy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TravelPolicyRepo extends CrudRepository<TravelPolicy,Integer> {
    TravelPolicy getTravelPolicyByAdcode(Integer adcode);
    List<TravelPolicy> findAll(Pageable pageable);
}
