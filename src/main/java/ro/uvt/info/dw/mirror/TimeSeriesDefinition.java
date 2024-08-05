package ro.uvt.info.dw.mirror;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table("time_series_definition")
@Data
@Accessors(fluent = true)
public class TimeSeriesDefinition {
    @PrimaryKeyColumn(name="id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @JsonProperty("id")
    private String id;
    @PrimaryKeyColumn(name="system_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @JsonProperty("systemTime")
    private Date systemTime;
    @Column("name")
    String name;
    @Column("description")
    String description;
    @Column("publisher")
    String publisher;
    @Column("type")
    int type;
    @Column("attributes")
    @JsonProperty("attributes")
    Set<String> attributes = new HashSet<String>();
}
