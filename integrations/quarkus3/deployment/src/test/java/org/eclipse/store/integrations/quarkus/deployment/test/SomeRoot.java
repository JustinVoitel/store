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



public class SomeRoot {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
