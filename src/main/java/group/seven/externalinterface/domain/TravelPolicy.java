package group.seven.externalinterface.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "travelpolicy")
public class TravelPolicy {
    @Id
    private Integer adcode;
    @Column(columnDefinition = "TEXT")
    private String backPolicy;
    private String backPolicyDate;
    private String leavePolicy;
    @Column(columnDefinition = "TEXT")
    private String leavePolicyDate;
    private String stayInfo;
}
