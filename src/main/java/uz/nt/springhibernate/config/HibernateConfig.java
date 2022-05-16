package uz.nt.springhibernate.config;


import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

//    For postgresql

    @Bean("postgres")
    public LocalSessionFactoryBean sessionFactoryPostgres() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        sessionFactoryBean.setDataSource(dataSourcePostgres());
        sessionFactoryBean.setPackagesToScan("uz.nt.springhibernate.model");
        sessionFactoryBean.setHibernateProperties(hibernatePropertiesPostgres());
        return sessionFactoryBean;
    }

    @Bean()
    public PlatformTransactionManager transactionManagerPostgres() {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactoryPostgres().getObject());

        return hibernateTransactionManager;
    }

    @Bean()
    public DataSource dataSourcePostgres() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }

    private Properties hibernatePropertiesPostgres() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");

        return properties;
    }

//    MySQL

    @Bean("mysql")
    public LocalSessionFactoryBean sessionFactoryMySQL() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceMySQL());
        sessionFactory.setPackagesToScan(
                "uz.nt.springhibernate.model");
        sessionFactory.setHibernateProperties(hibernatePropertiesMySQL());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSourceMySQL() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/book_store");
        dataSource.setUsername("root");
        dataSource.setPassword("root123");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManagerMySQL() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryMySQL().getObject());
        return transactionManager;
    }

    private final Properties hibernatePropertiesMySQL() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
        "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return hibernateProperties;
    }

}
