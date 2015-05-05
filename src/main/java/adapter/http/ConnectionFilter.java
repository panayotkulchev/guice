package adapter.http;

import adapter.db.pool.ConnectionPool;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.sql.Connection;

@Singleton
public class ConnectionFilter implements Filter {

  public final static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (connectionThreadLocal.get() == null) {

      Connection connection = ConnectionPool.getInstance().acquire();
      connectionThreadLocal.set(connection);
    }

    chain.doFilter(request, response);

    Connection connection = connectionThreadLocal.get();

    ConnectionPool.getInstance().release(connection);

    connectionThreadLocal.set(null);
  }


  @Override
  public void destroy() {

  }
}
