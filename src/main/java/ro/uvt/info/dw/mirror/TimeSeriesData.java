package ro.uvt.info.dw.mirror;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Table("time_series_data")
@Data
@Accessors(fluent = true)
public class TimeSeriesData {
    @PrimaryKeyColumn(name="asset_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @JsonProperty("id")
    String assetId;
    @PrimaryKeyColumn(name="time_series_definition_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    @JsonProperty("timeSeriesDefinitionId")
    String timeSeriesDefinitionId;
    @PrimaryKeyColumn(name="business_date_year", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    @JsonProperty("businessDateYear")
    int businessDateYear;
    @PrimaryKeyColumn(name="business_date", ordinal = 3, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    Date businessDate;
    @PrimaryKeyColumn(name="system_time", ordinal = 4, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    Date systemTime;
    @Column("values_blob")
    Map<String, ByteBuffer> valuesBlob = new HashMap<String, ByteBuffer>();
    @Column("values_boolean")
    Map<String, Boolean> valuesBoolean = new HashMap<String, Boolean>();
    @Column("values_double")
    Map<String, Double> valuesDouble = new HashMap<String, Double>();
    @Column("values_int")
    Map<String, Integer> valuesInt = new HashMap<String, Integer>();
    @Column("values_text")
    Map<String, String> valuesText = new HashMap<String, String>();
    @Column("values_timestamp")
    Map<String, Date> valuesTimestamp = new HashMap<String, Date>();
}
