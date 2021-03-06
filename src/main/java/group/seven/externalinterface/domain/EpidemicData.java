package group.seven.externalinterface.domain;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "epidemicdata")
public class EpidemicData {
    @Id
    private Date date;
    private Long currentConfirmedCount;
    private Long currentConfirmedIncr;
    private Long confirmedCount;
    private Long confirmedIncr;
    private Long overseasCount;
    private Long overseasIncr;
    private Long curedCount;
    private Long curedIncr;
    private Long deadCount;
    private Long deadIncr;
    private Long asymptomaticCount;
    private Long asymptomaticIncr;
}
