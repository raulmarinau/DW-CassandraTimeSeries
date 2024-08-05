package ro.uvt.info.dw.mirror;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Table("asset")
@Data
@Accessors(fluent = true)
public class Asset {
    @PrimaryKeyColumn(name="id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @JsonProperty("id")
    private String id;
    @PrimaryKeyColumn(name="system_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @JsonProperty("systemTime")
    private Date systemTime;
    @Column("values_blob")
    private Map<String, ByteBuffer> valuesBlob = new HashMap<String, ByteBuffer>();
    @Column("values_boolean")
    private Map<String, Boolean> valuesBoolean = new HashMap<String, Boolean>();
    @Column("values_double")
    private Map<String, Double> valuesDouble = new HashMap<String, Double>();
    @Column("values_int")
    private Map<String, Integer> valuesInt = new HashMap<String, Integer>();
    @Column("values_text")
    private Map<String, String> valuesText = new HashMap<String, String>();
    @Column("values_timestamp")
    private Map<String, Date> valuesTimestamp = new HashMap<String, Date>();
}
