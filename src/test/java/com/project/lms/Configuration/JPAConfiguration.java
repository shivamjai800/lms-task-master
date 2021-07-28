//package com.project.lms.Configuration;
//
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.test.context.BootstrapWith;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//@PropertySource("application.properties")
//@EnableTransactionManagement
//public class JPAConfiguration {
//
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//       dataSource.setUrl(env.getProperty("jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.user"));
//        dataSource.setPassword(env.getProperty("jdbc.pass"));
//        return dataSource;
//    }
//
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
//    {
//        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
//        localContainerEntityManagerFactoryBean.setPackagesToScan(new String[] {"com.project.lms"});
//
//        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        localContainerEntityManagerFactoryBean.setJpaProperties(additionalProperties());
//        return localContainerEntityManagerFactoryBean;
//
//    }
//
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor()
//    {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//    @Bean
//    public JpaTransactionManager jpaTransactionManager()
//    {
//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
//        return jpaTransactionManager;
//    }
//    final Properties additionalProperties()
//    {
//        final  Properties properties = new Properties();
//
//        properties.setProperty("hibernate.hbm2ddl.auto", "create");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        properties.setProperty("hibernate.show_sql","true");
//        return properties;
//    }
//
//}
