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




import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.store.storage.types.StorageManager;

@Path("/test")
@Produces(MediaType.TEXT_PLAIN)
public class StorageManagerController
{

    @Inject
    StorageManager storageManager;

    @GET
    public String testStorageManager()
    {
        return "storageManagerRoot=" + (storageManager.root() == null);
    }
}
