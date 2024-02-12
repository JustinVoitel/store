package org.microstream.spring.boot.example;

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.factories.EmbeddedStorageFoundationFactory;
import org.eclipse.store.integrations.spring.boot.types.factories.EmbeddedStorageManagerFactory;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration
{

    private final EmbeddedStorageManagerFactory managerFactory;
    private final EmbeddedStorageFoundationFactory foundationFactory;
    private final EclipseStoreProperties myConfiguration;

    public StorageConfiguration(EmbeddedStorageManagerFactory managerFactory, EmbeddedStorageFoundationFactory foundationFactory, EclipseStoreProperties myConfiguration)
    {
        this.managerFactory = managerFactory;
        this.foundationFactory = foundationFactory;
        this.myConfiguration = myConfiguration;
    }

    @Bean
    public EmbeddedStorageManager createStorage()
    {
        EmbeddedStorageFoundation<?> foundation = foundationFactory.createStorageFoundation(myConfiguration);

        return managerFactory.createStorage(foundation, myConfiguration.isAutoStart());
    }
}
