package ro.uvt.info.dw.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ro.uvt.info.dw.mirror.Asset;

import java.util.List;

@Repository
public interface AssetsRepository
        extends CassandraRepository<Asset, String> {
    List<Asset> findAllById(String id);
}
