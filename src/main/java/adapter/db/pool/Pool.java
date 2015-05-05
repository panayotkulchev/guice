package adapter.db.pool;

/**
 * Created on 15-1-16.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */
public interface Pool<T> {

  T acquire();

  void release(T connection);

}
