package testutil;

import com.arjuna.ats.jta.utils.JNDIManager;

import org.jnp.server.NamingBeanImpl;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class JtaEnvironment implements BeforeEachCallback, AfterEachCallback {

    private NamingBeanImpl NAMING_BEAN;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        NAMING_BEAN.stop();

    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        NAMING_BEAN = new NamingBeanImpl();
        NAMING_BEAN.start();

        JNDIManager.bindJTAImplementation();
        TransactionalConnectionProvider.bindDataSource();
    }
}