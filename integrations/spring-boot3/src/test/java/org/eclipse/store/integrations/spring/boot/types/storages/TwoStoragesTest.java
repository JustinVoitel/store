package org.eclipse.store.integrations.spring.boot.types.storages;

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

import org.eclipse.store.integrations.spring.boot.types.EclipseStoreProvider;
import org.eclipse.store.integrations.spring.boot.types.EclipseStoreSpringBoot;
import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.NestedTestConfiguration;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-two-storages.properties")
@Import(TwoStoragesTest.TwoBeanConfiguration.class)
@SpringBootTest(classes = {EclipseStoreSpringBoot.class})
@ActiveProfiles({ "two_storages" })
public class TwoStoragesTest
{

    @Autowired
    @Qualifier("first_storage")
    EmbeddedStorageManager firstStorage;

    @Autowired
    @Qualifier("second_storage")
    EmbeddedStorageManager secondStorage;


    @Test
    void twoStoragesTest()
    {
        Assertions.assertEquals("FirstRoot{value='First root value'}", this.firstStorage.root().toString());
        Assertions.assertEquals("SecondRoot{intValue=50, c=c}", this.secondStorage.root().toString());
    }

    @TestConfiguration
    @Profile({ "two_storages" })
    static public class TwoBeanConfiguration
    {

        @Autowired
        private EclipseStoreProvider provider;

        @Bean("first_config")
        @ConfigurationProperties("org.eclipse.store.first")
        EclipseStoreProperties firstStoreProperties()
        {
            return new EclipseStoreProperties();
        }

        @Bean("second_config")
        @ConfigurationProperties("org.eclipse.store.second")
        EclipseStoreProperties secondStoreProperties()
        {
            return new EclipseStoreProperties();
        }

        @Bean
        @Qualifier("first_storage")
        EmbeddedStorageManager createFirstStorage(@Qualifier("first_config") final EclipseStoreProperties firstStoreProperties)
        {
            return this.provider.createStorage(firstStoreProperties);
        }

        @Bean
        @Qualifier("second_storage")
        EmbeddedStorageManager createSecondStorage(@Qualifier("second_config") final EclipseStoreProperties secondStoreProperties)
        {
            return this.provider.createStorage(secondStoreProperties);
        }


    }
}
