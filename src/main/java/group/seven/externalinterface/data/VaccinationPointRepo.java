package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.VaccinationPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface VaccinationPointRepo extends CrudRepository<VaccinationPoint,Long> {

    /**
     * find vaccination points by adcode, by pageable
     * @param adcode adcode
     * @param pageable spring pageable
     * @return list of points
     */
    List<VaccinationPoint> getVaccinationPointsByAdcode(Integer adcode, Pageable pageable);

    /**
     * find all by pageable
     * @param pageable spring pageable
     * @return list of points
     */
    List<VaccinationPoint> findAll(Pageable pageable);

    /**
     * find points by adcode
     * @param adcode adcode
     * @return list of points
     */
    List<VaccinationPoint> findByAdcode(Integer adcode);
}
