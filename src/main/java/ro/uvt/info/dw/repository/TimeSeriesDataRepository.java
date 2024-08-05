package ro.uvt.info.dw.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ro.uvt.info.dw.mirror.TimeSeriesData;

import java.util.List;

@Repository
public interface TimeSeriesDataRepository
        extends CassandraRepository<TimeSeriesData, String> {
    List<TimeSeriesData> findAllByAssetIdAndTimeSeriesDefinitionIdAndBusinessDateYear(String assetId, String tsDefinitionId, int businessDateYear);
    List<TimeSeriesData> findAllByAssetId(String assetId);
}
