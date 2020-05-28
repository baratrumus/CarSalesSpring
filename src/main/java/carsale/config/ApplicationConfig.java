package carsale.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
@ComponentScan("carsale")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    final private static String DEFAULT_LOCALE = "ru";

    @Bean(name = "dataSource")
    public DataSource dataSource(@Value("${database.driverClassName}") String driver,
                                 @Value("${database.url}") String url,
                                 @Value("${database.username}") String username,
                                 @Value("${database.password}") String password) {
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
        factoryBean.setPackagesToScan("carsales.models");
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
    public LocaleResolver localeResolver(){
        SessionLocaleResolver resolver = new SessionLocaleResolver  ();
        resolver.setDefaultLocale(new Locale(DEFAULT_LOCALE));
        return resolver;
    }

/*
    @Bean
    public JdbcTemplate jdbc(DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean
    public SpringSecurityDialect getSpringSecurityDialect() {
        return new SpringSecurityDialect();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("locale");
        registry.addInterceptor(interceptor);
    }
  */

}
