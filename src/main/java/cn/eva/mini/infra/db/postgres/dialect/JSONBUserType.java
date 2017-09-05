package cn.eva.mini.infra.db.postgres.dialect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Json user type.
 */
public class JSONBUserType implements ParameterizedType, UserType {

  /**
   * Object mapper.
   */
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /**
   * Class loader service.
   */
  private static final ClassLoaderService CLASS_LOADER_SERVICE = new ClassLoaderServiceImpl();

  /**
   * Json type.
   */
  public static final String JSONB_TYPE = "jsonb";

  /**
   * Class name.
   */
  public static final String CLASS = "CLASS";

  /**
   * Json class type.
   */
  private transient Class jsonClassType;

  /**
   * returned class.
   *
   * @return Class<Object>
   */
  @Override
  public Class<Object> returnedClass() {
    return Object.class;
  }

  /**
   * Sql types.
   *
   * @return int []
   */
  @Override
  public int[] sqlTypes() {
    return new int[] {Types.JAVA_OBJECT};
  }

  /**
   * Null safe get.
   *
   * @param resultSet
   * @param names
   * @param session
   * @param owner
   * @return
   * @throws HibernateException
   * @throws SQLException
   */
  @Override
  public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session,
                            Object owner) throws HibernateException, SQLException {
    try {
      final String json = resultSet.getString(names[0]);
      return json == null ? null : OBJECT_MAPPER.readValue(json, jsonClassType);
    } catch (IOException e) {
      throw new HibernateException(e);
    }
  }

  /**
   * Null safe set.
   *
   * @param st
   * @param value
   * @param index
   * @param session
   * @throws HibernateException
   * @throws SQLException
   */
  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor
      session) throws HibernateException, SQLException {
    try {
      final String json = value == null ? null : OBJECT_MAPPER.writeValueAsString(value);
      PGobject pgo = new PGobject();
      pgo.setType(JSONB_TYPE);
      pgo.setValue(json);
      st.setObject(index, pgo);
    } catch (JsonProcessingException e) {
      throw new HibernateException(e);
    }
  }

  /**
   * Set value.
   *
   * @param parameters
   */
  @Override
  public void setParameterValues(Properties parameters) {
    final String clazz = (String) parameters.get(CLASS);
    jsonClassType = CLASS_LOADER_SERVICE.classForName(clazz);
  }

  /**
   * Deep copy.
   *
   * @param value
   * @return
   * @throws HibernateException
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object deepCopy(Object value) {

    if (!(value instanceof Collection)) {
      return value;
    }

    Collection<?> collection = (Collection) value;
    Collection collectionClone = CollectionFactory.newInstance(collection.getClass());

    collectionClone.addAll(collection.stream().map(this::deepCopy).collect(Collectors.toList()));

    return collectionClone;
  }

  /**
   * Collection factory.
   */
  private static final class CollectionFactory {

    /**
     * Default private constructor.
     */
    private CollectionFactory() {
    }

    /**
     * create new instance.
     *
     * @param collectionClass
     * @param <E>
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <E, T extends Collection<E>> T newInstance(Class<T> collectionClass) {
      if (List.class.isAssignableFrom(collectionClass)) {
        return (T) new ArrayList<E>();
      } else if (Set.class.isAssignableFrom(collectionClass)) {
        return (T) new HashSet<E>();
      } else {
        throw new IllegalArgumentException("Unsupported collection type : " + collectionClass);
      }
    }
  }

  /**
   * Is mutable.
   *
   * @return
   */
  @Override
  public boolean isMutable() {
    return true;
  }

  /**
   * Equals.
   *
   * @param x
   * @param y
   * @return
   * @throws HibernateException
   */
  @Override
  public boolean equals(Object x, Object y) {
    if (x == null || y == null) {
      return false;
    }

    return x.equals(y);
  }

  /**
   * Hash code.
   *
   * @param x
   * @return
   */
  @Override
  public int hashCode(Object x) {
    assert x != null;
    return x.hashCode();
  }

  /**
   * Assemble.
   *
   * @param cached
   * @param owner
   * @return
   */
  @Override
  public Object assemble(Serializable cached, Object owner) {
    return deepCopy(cached);
  }

  /**
   * Disassemble.
   *
   * @param value
   * @return
   * @throws HibernateException
   */
  @Override
  public Serializable disassemble(Object value) {
    Object deepCopy = deepCopy(value);

    if (!(deepCopy instanceof Serializable)) {
      throw new SerializationException(String.format("%s is not serializable class", value), null);
    }

    return (Serializable) deepCopy;
  }

  /**
   * Replace.
   *
   * @param original
   * @param target
   * @param owner
   * @return
   * @throws HibernateException
   */
  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return deepCopy(original);
  }
}