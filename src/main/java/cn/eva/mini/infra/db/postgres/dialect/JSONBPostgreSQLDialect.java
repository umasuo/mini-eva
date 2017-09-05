package cn.eva.mini.infra.db.postgres.dialect;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

/**
 * Json dialect for saving json into postgres.
 */
public class JSONBPostgreSQLDialect extends PostgreSQL94Dialect {

  /**
   * Dialect.
   */
  public JSONBPostgreSQLDialect() {
    super();
    registerColumnType(Types.JAVA_OBJECT, JSONBUserType.JSONB_TYPE);
  }
}
