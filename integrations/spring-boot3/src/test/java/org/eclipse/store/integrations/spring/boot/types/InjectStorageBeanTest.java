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

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.converter.EclipseStoreConfigConverter;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-inject-test.properties")
@SpringBootTest(classes = {EclipseStoreSpringBoot.class})
@Import(InjectStorageBeanTest.InjectConfiguration.class)
public class InjectStorageBeanTest
{

    @Autowired
    @Qualifier("embeddedStorageManager")
    EmbeddedStorageManager manager;


    @Test
    void injectStorageTest()
    {
        this.manager.start();
        this.manager.setRoot("hello");
        this.manager.storeRoot();
        this.manager.shutdown();
    }

    @TestConfiguration
    static class InjectConfiguration{

        @Autowired
        EclipseStoreProperties properties;



    }
}
