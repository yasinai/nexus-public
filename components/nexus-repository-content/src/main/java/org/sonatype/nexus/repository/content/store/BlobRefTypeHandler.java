/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.repository.content.store;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobRef;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * MyBatis {@link TypeHandler} that maps {@link BlobRef}s to/from SQL.
 *
 * @since 3.20
 */
@Named
@Singleton
public class BlobRefTypeHandler
    extends BaseTypeHandler<BlobRef>
{
  @Override
  public void setNonNullParameter(final PreparedStatement ps,
                                  final int parameterIndex,
                                  final BlobRef parameter,
                                  final JdbcType jdbcType)
      throws SQLException
  {
    ps.setString(parameterIndex, parameter.toString());
  }

  @Override
  public BlobRef getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
    return nullableBlobRef(rs.getString(columnName));
  }

  @Override
  public BlobRef getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
    return nullableBlobRef(rs.getString(columnIndex));
  }

  @Override
  public BlobRef getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
    return nullableBlobRef(cs.getString(columnIndex));
  }

  @Nullable
  private BlobRef nullableBlobRef(@Nullable final String blobRef) {
    return blobRef != null ? BlobRef.parse(blobRef) : null;
  }
}
