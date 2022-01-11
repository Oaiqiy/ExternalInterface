package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.VaccinationPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface VaccinationPointRepo extends CrudRepository<VaccinationPoint,Long> {
    List<VaccinationPoint> getVaccinationPointsByAdcode(Integer adcode, Pageable pageable);
    List<VaccinationPoint> findAll(Pageable pageable);
    List<VaccinationPoint> findByAdcode(Integer adcode);
}
