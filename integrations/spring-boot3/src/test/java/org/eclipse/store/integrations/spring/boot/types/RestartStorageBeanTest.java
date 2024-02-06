package org.eclipse.store.integrations.spring.boot.types;

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

import org.eclipse.store.integrations.spring.boot.types.configuration.ConfigurationPair;
import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.converter.EclipseStoreConfigConverter;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-run-test.properties")
//@SpringBootTest(classes = {EclipseStoreProperties.class, EclipseStoreProviderImpl.class, EclipseStoreConfigConverter.class})
@SpringBootTest(classes = {EclipseStoreSpringBoot.class})
@Import(RestartStorageBeanTest.RestartStorageBeanConfiguration.class)
public class RestartStorageBeanTest
{

    @Autowired
    EclipseStoreProvider provider;

    @Autowired
    EclipseStoreProperties myConfiguration;

    @Test
    void restartStorageTest(@Autowired @Qualifier("restartStorageBean") final EmbeddedStorageManager manager)
    {
        final RestartRoot root = new RestartRoot("hello");
        manager.start();
        manager.setRoot(root);
        manager.storeRoot();
        manager.shutdown();

        final EmbeddedStorageFoundation<?> storageFoundation = this.provider.createStorageFoundation(this.myConfiguration);
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
    static class RestartStorageBeanConfiguration
    {

        @Autowired
        EclipseStoreProvider provider;

        @Autowired
        EclipseStoreProperties myConfiguration;

        @Bean("restartStorageBean")
        @Lazy
        EmbeddedStorageManager restartStorageBean()
        {
            final EmbeddedStorageFoundation<?> storageFoundation = this.provider.createStorageFoundation(this.myConfiguration);

            return storageFoundation.createEmbeddedStorageManager();
        }
    }
}
