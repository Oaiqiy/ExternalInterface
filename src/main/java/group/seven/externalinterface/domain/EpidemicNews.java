package group.seven.externalinterface.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "epidemicnews")
public class EpidemicNews {
    @Id
    private Long pubDate;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String summary;
    private String infoSource;
    @Column(columnDefinition = "TEXT")
    private String sourceUrl;
}
