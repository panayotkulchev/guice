import adapter.db.*;
import adapter.http.*;
import adapter.http.bank.Deposit;
import adapter.http.bank.Withdraw;
import adapter.shedule.SchedulerConnectionProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;
import core.*;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Panayot Kulchev on 15-4-27.
 * e-mail: panayotkulchev@gmail.com
 * happy coding ...
 */
public class AppConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(

                new ServletModule() {
                    @Override
                    protected void configureServlets() {
                        filter("/*").through(ConnectionFilter.class);
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

                        at("/register").show(Register.class);
                        at("/login").show(Login.class);
                        at("/welcome").show(Welcome.class);
                        at("/deposit").show(Deposit.class);
                        at("/withdraw").show(Withdraw.class);
                        at("/logout").show(Logout.class);
                        at("/menu").show(Menu.class);
                        at("/report").show(Report.class);

                        embed(Menu.class).as("Menu");
                    }
                },

                new AbstractModule() {
                    @Override
                    protected void configure() {

                        bind(ConnectionProvider.class).to(JdbcConnectionProvider.class);

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

                });
    }


}

