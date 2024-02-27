package org.eclipse.store.integrations.quarkus.types.config;

/*-
 * #%L
 * EclipseStore Quarkus 3 Extension - Runtime
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


import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;

public interface EmbeddedStorageFoundationCustomizer
{
    void customize(final EmbeddedStorageFoundation<?> embeddedStorageFoundation);
}
