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
    private String back_policy;
    private String back_policy_date;
    private String leave_policy;
    @Column(columnDefinition = "TEXT")
    private String leave_policy_date;
    private String stay_info;
}
