package org.eclipse.store.afs.sql.types;

/*-
 * #%L
 * EclipseStore Abstract File System SQL
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

import javax.sql.DataSource;

import org.eclipse.serializer.configuration.types.Configuration;


public class SqlFileSystemCreatorMariaDb extends SqlFileSystemCreator
{
	public SqlFileSystemCreatorMariaDb()
	{
		super("mariadb");
	}
	
	@Override
	protected SqlProvider createSqlProvider(
		final Configuration sqlConfiguration,
		final DataSource    dataSource
	)
	{
		return SqlProviderMariaDb.New(
			sqlConfiguration.get("catalog"),
			sqlConfiguration.get("schema") ,
			dataSource
		);
	}
	
}
