import adapter.db.*;
import adapter.http.*;
import adapter.http.bank.DepositPage;
import adapter.http.bank.WithdrawPage;
import adapter.http.validator.ParamHolder;
import adapter.http.validator.RequestParamHolder;
import adapter.http.validator.Rule;

import adapter.http.validator.ValidationRule;
import com.google.common.collect.Lists;
import com.google.inject.*;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;
import core.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.List;
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

                        bind(ParamHolder.class).to(RequestParamHolder.class);

                    }


                    @Provides
                    @RequestScoped
                    public CurrentUser getCurrentUser(SidFetcher sidFetcher, UserRepository userRepository) {

                        String sid = sidFetcher.fetch();

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
                    public Connection getConnection(DatabaseMetadata databaseMetadata) {

                        Connection connection = null;

                        String dbHost = databaseMetadata.get("db.host");
                        String dbUsername = databaseMetadata.get("db.username");
                        String dbPassword = databaseMetadata.get("db.password");

                        try {
                            connection = DriverManager.getConnection(dbHost, dbUsername, dbPassword);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return connection;
                    }

                    @Provides
                    @Singleton
                    public DatabaseMetadata provideDatabaseMetadata() {
                        return new DatabaseMetadata();
                    }

                    @Provides
                    @Singleton
                    public ConfigurationProperties provideConfigurationProperties() {
                        return new ConfigurationProperties();
                    }

                    @Provides
                    @Singleton
                    @ValidationRules
                    public List<Rule> getValidationRules(){

                        List<Rule> rules = Lists.newArrayList();
                        rules.add(new ValidationRule("email", "Email is not valid", "^[a-z]{3,30}+$"));
                        rules.add(new ValidationRule("password", "Password is not valid", "^[a-z]{3,10}+$"));

                        return rules;
                    }


                });
    }


}

