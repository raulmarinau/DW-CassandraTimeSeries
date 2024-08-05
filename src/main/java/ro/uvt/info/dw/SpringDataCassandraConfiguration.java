package ro.uvt.info.dw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "ro.uvt.info.dw")
public class SpringDataCassandraConfiguration extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.contact-points:localhost}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port:9042}")
    private int port;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keySpace;

    @Value("${spring.data.cassandra.username:placeholder}")
    private String username;

    @Value("${spring.data.cassandra.password:placeholder}")
    private String password;

    @Value("${spring.data.cassandra.schema-action:null}")
    private String schemaAction;

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }
    @Override
    protected String getContactPoints() {
        return contactPoints;
    }
    @Override
    protected int getPort() {
        return port;
    }
    @Override
    public SchemaAction getSchemaAction() {
        return "null".equals(schemaAction)
                ? super.getSchemaAction()
                : SchemaAction.valueOf(schemaAction);
    }
}
