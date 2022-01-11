package group.seven.externalinterface.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "vaccinationpoint")
public class VaccinationPoint {
    @Id
    private Long index;
    private Integer adcode;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String address;
    private String phone;
}
