package ro.uvt.info.dw.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ro.uvt.info.dw.mirror.Attribute;

import java.util.List;

@Repository
public interface AttributesRepository
        extends CassandraRepository<Attribute, String>{
    List<Attribute> findAllById(String id);
}
