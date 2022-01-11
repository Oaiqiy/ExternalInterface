package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.TravelPolicy;
import org.springframework.data.repository.CrudRepository;

public interface TravelPolicyRepo extends CrudRepository<TravelPolicy,Integer> {
    TravelPolicy getTravelPolicyByAdcode(Integer adcode);
}
