package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.EpidemicData;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EpidemicDataRepo extends CrudRepository<EpidemicData, Date> {
    List<EpidemicData> findAll();
    EpidemicData findEpidemicDataByDate(Date date);
    EpidemicData findFirstByOrderByDate();
}
