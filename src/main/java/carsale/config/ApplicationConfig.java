package carsale.config;

import carsale.controller.AdsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Configuration
@ComponentScan({"carsale.service", "carsale.config", "carsale.dao"})
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class ApplicationConfig {

    final private static String DEFAULT_LOCALE = "ru";
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);
    @Value("${database.driverClassName}")
    private String driver;

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Value("${url}")
    private String herokuUrl;

    @Value("${username}")
    private String herokuUsername;

    @Value("${password}")
    private String herokuPassword;

    @Autowired
    private Environment env;
    private String dynoId = "";

    @PostConstruct
    private void herokuInit() {
        dynoId = System.getenv("DYNO");
        for (String profile : env.getActiveProfiles()) {
            LOG.warn("\n" + ">>>>>>" + profile + "\n");
        }
        if (dynoId != null) {
            url = System.getenv("JDBC_DATABASE_URL");
            username = herokuUsername;
            password = herokuPassword;
        }
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       @Value("${hibernate.dialect}") String dialect,
                                                                       @Value("${hibernate.show_sql}") String showSql,
                                                                       @Value("${hibernate.format_sql}") String formatSql,
                                                                       @Value("${hibernate.ddl-auto}") String ddl,
                                                                       @Value("${hibernate.use_sql_comments}") String comments) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("carsale.models");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        Properties cfg = new Properties();
        cfg.setProperty("hibernate.dialect", dialect);
        cfg.setProperty("hibernate.show_sql", showSql);
        cfg.setProperty("hibernate.format_sql", formatSql);
        cfg.setProperty("hibernate.ddl-auto", ddl);
        cfg.setProperty("hibernate.use_sql_comments", comments);
        factoryBean.setJpaProperties(cfg);
        return factoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale(DEFAULT_LOCALE));
        return resolver;
    }

}