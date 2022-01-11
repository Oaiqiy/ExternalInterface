package group.seven.externalinterface.data;

import group.seven.externalinterface.domain.Adcode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdcodeRepo extends CrudRepository<Adcode,Integer> {
    List<Adcode> findAll();
}
