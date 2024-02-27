package org.eclipse.store.integrations.quarkus.types.impl;

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



import java.util.Arrays;
import java.util.List;

import org.eclipse.store.integrations.quarkus.types.Storage;

/**
 * Information about a class annotated with {@link Storage}.
 */
public class StorageClassInfo
{

    private final Class<?> classReference;

    private final List<String> fieldsToInject;

    public StorageClassInfo(final Class<?> classReference, final List<String> fieldsToInject)
    {

        this.classReference = classReference;
        this.fieldsToInject = fieldsToInject;
    }

    public StorageClassInfo(final Class<?> classReference, final String fieldsToInject)
    {
        if (classReference != Object.class)
        {
            this.classReference = classReference;
        } else {
            // Object.class past since .map entries on BeanCreator blow up when null is specified in value.
            // see https://github.com/quarkusio/quarkus/issues/27664
            this.classReference = null;
        }
        this.fieldsToInject = Arrays.asList(fieldsToInject.split(","));
    }

    public Class<?> getClassReference()
    {
        return classReference;
    }

    public List<String> getFieldsToInject()
    {
        return this.fieldsToInject;
    }
}
