package br.ufsc.framework.services.properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private Properties props;

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        this.props = props;
    }

    public Properties getProps() {
        return props;
    }
}
