package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.NucleicAcidDetectionPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface NucleicAcidDetectionPointRepo extends CrudRepository<NucleicAcidDetectionPoint, Long> {

    /**
     * find nucleic acid detection point by adcode, by pageable
     * @param adcode adcode
     * @param pageable spring pageable
     * @return list of points
     */
    List<NucleicAcidDetectionPoint> getNucleicAcidDetectionPointsByAdcode(Integer adcode, Pageable pageable);

    /**
     * find nucleic acid detection point by adcode
     * @param adcode adcode
     * @return list of points
     */
    List<NucleicAcidDetectionPoint> findNucleicAcidDetectionPointsByAdcode(Integer adcode);

    /**
     * find all by page
     * @param pageable spring pageable
     * @return list of points
     */
    List<NucleicAcidDetectionPoint> findAll(Pageable pageable);
}
