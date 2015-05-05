package adapter.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Panayot Kulchev on 15-4-30.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
@Singleton
public class CacheFilter implements Filter {

  private Set<String> pages;

  @Inject
  public CacheFilter(Set<String> pages) {
    this.pages = pages;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;

    String uri = request.getRequestURI();
    if (pages.contains(uri)) {
      System.out.println("CacheFilter have a hit ");

      HttpServletResponse response = (HttpServletResponse) servletResponse;

      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
      response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
      response.setDateHeader("Expires", 0); // Proxies.

      filterChain.doFilter(servletRequest,servletResponse);
      return;
    }
    filterChain.doFilter(servletRequest,servletResponse);
  }

  @Override
  public void destroy() {

  }
}
