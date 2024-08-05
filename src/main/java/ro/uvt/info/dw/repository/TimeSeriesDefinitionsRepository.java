package ro.uvt.info.dw.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ro.uvt.info.dw.mirror.TimeSeriesDefinition;

import java.util.List;

@Repository
public interface TimeSeriesDefinitionsRepository
        extends CassandraRepository<TimeSeriesDefinition, String> {
    List<TimeSeriesDefinition> findAllById(String id);
}
