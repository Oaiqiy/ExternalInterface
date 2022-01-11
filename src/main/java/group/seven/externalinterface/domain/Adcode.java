package group.seven.externalinterface.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Adcode {
    @Id
    private Integer adcode;
    private String chineseName;
}
