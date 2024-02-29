package org.eclipse.store.integrations.quarkus.deployment.test;

/*-
 * #%L
 * EclipseStore Quarkus 3 Extension - Deployment
 * %%
 * Copyright (C) 2023 - 2024 MicroStream Software
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */



import org.eclipse.store.integrations.quarkus.types.config.StorageManagerInitializer;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.store.storage.types.StorageManager;

@ApplicationScoped
public class SomeInitializerForStorage implements StorageManagerInitializer
{
    @Override
    public void initialize(StorageManager storageManager)
    {
        SomeRootWithStorage rootObject = (SomeRootWithStorage) storageManager.root();
        if (rootObject == null) {
            throw new IllegalStateException("StorageManager should have already a Root object assigned");
        } else {
            rootObject.setData("Initial value of Root");
        }
    }
}
