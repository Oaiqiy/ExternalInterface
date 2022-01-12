package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.EpidemicData;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EpidemicDataRepo extends CrudRepository<EpidemicData, Date> {
    /**
     * find all epidemic data
     * @return a list of epidemic data
     */
    List<EpidemicData> findAll();

    /**
     * find epidemic data by date
     * @param date date of epidemic
     * @return epidemic data
     */
    EpidemicData findEpidemicDataByDate(Date date);

    /**
     * find the last epidemic data
     * @return epidemic data
     */
    EpidemicData findFirstByOrderByDate();
}
