package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.NucleicAcidDetectionPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface NucleicAcidDetectionPointRepo extends CrudRepository<NucleicAcidDetectionPoint, Long> {
    List<NucleicAcidDetectionPoint> getNucleicAcidDetectionPointsByAdcode(Integer adcode, Pageable pageable);
    List<NucleicAcidDetectionPoint> findNucleicAcidDetectionPointsByAdcode(Integer adcode);
    List<NucleicAcidDetectionPoint> findAll(Pageable pageable);
}
