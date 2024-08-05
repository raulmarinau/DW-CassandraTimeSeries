package ro.uvt.info.dw;

import com.datastax.driver.core.ResultSet;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class CreateKeyspaceTest {

    public static String CLUSTER_IP  = "localhost";
    private static int PORT = 9042;
    private static String USER = "cassandra";
    private static String PASSWORD = "cassandra";

    @Test
    public void testCreateKeyspaceSuccessfull() {
        String keyspaceName = "test1";
        CassandraConnection client = new CassandraConnection();

        ResultSet result = client
            .connect(CLUSTER_IP, PORT, USER, PASSWORD)
            .createKeyspace(keyspaceName, "SimpleStrategy", 3)
            .session()
            .execute("SELECT * FROM system_schema.keyspaces;");

        List<String> matchedKeyspaces = result.all().stream()
            .filter(r -> r.getString(0).equals(keyspaceName.toLowerCase()))
            .map(r -> r.getString(0))
            .collect(Collectors.toList());

        client
            .dropKeyspace(keyspaceName)
            .close();

        assert matchedKeyspaces.size() == 1;
        assert matchedKeyspaces.get(0).equals(keyspaceName.toLowerCase());
    }
}
