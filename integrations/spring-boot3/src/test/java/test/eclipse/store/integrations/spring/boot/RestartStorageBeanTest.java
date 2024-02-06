package test.eclipse.store.integrations.spring.boot;

/*-
 * #%L
 * spring-boot3
 * %%
 * Copyright (C) 2023 MicroStream Software
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */


import java.nio.file.Path;

import org.eclipse.store.integrations.spring.boot.types.EclipseStoreProvider;
import org.eclipse.store.integrations.spring.boot.types.EclipseStoreSpringBoot;
import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationPair;
import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-run-test.properties")
@SpringBootTest(classes = {EclipseStoreSpringBoot.class})
@Import(RestartStorageBeanTest.RestartStorageBeanConfiguration.class)
@ActiveProfiles({"restart_storage"})
public class RestartStorageBeanTest
{

    @Autowired
    EclipseStoreProvider provider;

    @Autowired
    EclipseStoreProperties myConfiguration;

    @TempDir
    static Path tempFolder;


    @Test
    void restartStorageTest(@Autowired @Qualifier("restartStorageBean") final EmbeddedStorageManager manager)
    {
        final RestartRoot root = new RestartRoot("hello");
        manager.start();
        manager.setRoot(root);
        manager.storeRoot();
        manager.shutdown();

        Assertions.assertEquals(tempFolder.toAbsolutePath().toString(), manager.configuration().fileProvider().baseDirectory().toPathString());

        final ConfigurationPair pair = new ConfigurationPair("someKey", "someValue");
        final EmbeddedStorageFoundation<?> storageFoundation = this.provider.createStorageFoundation(this.myConfiguration, pair);
        final RestartRoot root2 = new RestartRoot();
        storageFoundation.setRoot(root2);
        try (EmbeddedStorageManager storage = storageFoundation.start())
        {
            final RestartRoot rootFromStorage = (RestartRoot) storage.root();
            Assertions.assertEquals("hello", rootFromStorage.getValue());
        }
    }

    static class RestartRoot
    {
        private String value;

        public RestartRoot(final String value)
        {
            this.value = value;
        }

        public RestartRoot()
        {
        }

        public String getValue()
        {
            return this.value;
        }

        public void setValue(final String value)
        {
            this.value = value;
        }
    }

    @TestConfiguration
    @Profile("restart_storage")
    static class RestartStorageBeanConfiguration
    {

        @Autowired
        EclipseStoreProvider provider;

        @Autowired
        @Qualifier("org.eclipse.store-org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties")
        EclipseStoreProperties myConfiguration;

        @Bean("restartStorageBean")
        EmbeddedStorageManager restartStorageBean()
        {
            this.myConfiguration.setStorageDirectory(tempFolder.toAbsolutePath().toString());
            final EmbeddedStorageFoundation<?> storageFoundation = this.provider.createStorageFoundation(this.myConfiguration);

            return storageFoundation.createEmbeddedStorageManager();
        }
    }
}
