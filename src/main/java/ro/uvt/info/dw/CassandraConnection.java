package ro.uvt.info.dw;

import com.datastax.driver.core.*;

import java.io.Closeable;

import static java.lang.System.out;

public class CassandraConnection implements Closeable {
    private Cluster cluster;

    private Session session;

    public CassandraConnection connect(String node, Integer port, String user, String password) {
        Cluster.Builder b = Cluster.builder()
                .addContactPoint(node)
                .withPort(port);

        if (user!=null && password!=null) {
            b.withAuthProvider(new PlainTextAuthProvider(user, password));
        }

        cluster = b.build();
        printMetadata();
        session = cluster.connect();
        return this;
    }

    private void printMetadata() {
        final Metadata metadata = cluster.getMetadata();
        out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for (final Host host : metadata.getAllHosts())
        {
            out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }
    }

    public Session session() {
        return this.session;
    }

    public CassandraConnection createKeyspace(
            String keyspaceName,
            String replicationStrategy,
            int replicationFactor) {

        String cqlCmd = "CREATE KEYSPACE IF NOT EXISTS " +
                keyspaceName + " WITH replication = {" +
                "'class':'" + replicationStrategy +
                "','replication_factor':" + replicationFactor +
                "};";

        session.execute(cqlCmd);
        return this;
    }

    public CassandraConnection dropKeyspace(String keyspaceName) {
        session.execute("DROP KEYSPACE IF EXISTS " + keyspaceName);
        return this;
    }

    public void close() {
        session.close();
        cluster.close();
    }

}
