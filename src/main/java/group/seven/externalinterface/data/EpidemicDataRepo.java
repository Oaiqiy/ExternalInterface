package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.EpidemicData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EpidemicDataRepo extends CrudRepository<EpidemicData, Long> {
    List<EpidemicData> findAll();
}
