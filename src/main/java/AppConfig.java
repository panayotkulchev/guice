import adapter.db.*;
import adapter.http.*;
import adapter.http.bank.DepositPage;
import adapter.http.bank.WithdrawPage;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;
import core.*;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class AppConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(

                new ServletModule() {
                    @Override
                    protected void configureServlets() {

                        filter("/login").through(LoginFilter.class);

                        filter("/welcome").through(SecurityFilter.class);
                        filter("/withdraw").through(SecurityFilter.class);
                        filter("/deposit").through(SecurityFilter.class);
                        filter("/menu").through(SecurityFilter.class);
                        filter("/report").through(SecurityFilter.class);

                        filter("/withdraw").through(CacheFilter.class);
                    }
                },

                new SitebricksModule() {
                    @Override
                    protected void configureSitebricks() {

                        at("/register").show(RegisterPage.class);
                        at("/login").show(LoginPage.class);
                        at("/welcome").show(WelcomePage.class);
                        at("/deposit").show(DepositPage.class);
                        at("/withdraw").show(WithdrawPage.class);
                        at("/logout").show(LogoutPage.class);
                        at("/menu").show(Menu.class);
                        at("/report").show(ReportPage.class);

                        embed(Menu.class).as("Menu");
                    }
                },

                new AbstractModule() {
                    @Override
                    protected void configure() {

                        bind(SessionRepository.class).to(PersistentSessionRepository.class);

                        bind(UserRepository.class).to(PersistentUserRepository.class);

                        bind(FundsRepository.class).to(PersistentFundsRepository.class);

                        bind(FundsHistoryRepository.class).to(PersistentFundsHistoryRepository.class);

                    }


                    @Provides
                    @RequestScoped

                    public CurrentUser getCurrentUser(Provider<HttpServletRequest> requestProvider, UserRepository userRepository) {

                        String sid = SidProvider.getSid(requestProvider.get());

                        return userRepository.getBySid(sid);
                    }

                    @Provides
                    public Set<String> provideFilteredPages() {
                        return new HashSet<String>() {{
                            add("/withdraw");
                            add("/deposit");
                            add(("/report"));
                        }};
                    }

                    @Provides
                    @RequestScoped
                    public Connection getConnection(){

                        Connection connection = null;
                        String dbHost = DatabaseMetadata.get("db.host");
                        String dbUsername = DatabaseMetadata.get("db.username");
                        String dbPassword = DatabaseMetadata.get("db.password");

                        try {
                            connection = DriverManager.getConnection(dbHost, dbUsername, dbPassword);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return connection;
                    }

                });
    }


}

