package gwfs.services;

import com.jolbox.bonecp.BoneCPDataSource;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.sqlite.SQLiteOpenMode;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class AppModule {
    public static void bind(ServiceBinder binder) {
        // binder.bind(MyServiceInterface.class, MyServiceImpl.class);

        // Make bind() calls on the binder object to define most IoC services.
        // Use service builder methods (example below) when the implementation
        // is provided inline, or requires more initialization than simply
        // invoking the constructor.
    }

    public static void contributeFactoryDefaults(
            MappedConfiguration<String, Object> configuration) {
        // The application version number is incorprated into URLs for some
        // assets. Web browsers will cache assets because of the far future expires
        // header. If existing assets are changed, the version number should also
        // change, to force the browser to download new versions. This overrides Tapesty's default
        // (a random hexadecimal number), but may be further overriden by DevelopmentModule or
        // QaModule.
        configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
    }

    public static void contributeApplicationDefaults(
            MappedConfiguration<String, Object> configuration) {
        // Contributions to ApplicationDefaults will override any contributions to
        // FactoryDefaults (with the same key). Here we're restricting the supported
        // locales to just "en" (English). As you add localised message catalogs and other assets,
        // you can extend this list of locales (it's a comma separated series of locale names;
        // the first locale name is the default when there's no reasonable match).
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
    }

    // Normally, all the connection pooling and management stuff comes out of Hibernate,
    // but this is a quick-and-dirty way to get about the same thing.

    /**
     * Define a DataSource service.
     */
    public DataSource buildBoneCPDataSource() throws Exception {

        Properties props = new Properties();

        props.put("open_mode", String.valueOf(SQLiteOpenMode.READONLY));

        BoneCPDataSource ds = new BoneCPDataSource();
        ds.setDriverClass("org.sqlite.JDBC");
        ds.setJdbcUrl("jdbc:sqlite:hello.db");
        ds.setProperties(props);
        ds.setUsername("n/a");
        ds.setPassword("n/a");
        // The test documentation indicates up to about 15 parallel requests, so let's make sure there's
        // always a connection ready in the pool.
        ds.setMinConnectionsPerPartition(15);
        ds.setMaxConnectionsPerPartition(20);

        return ds;
    }

}
