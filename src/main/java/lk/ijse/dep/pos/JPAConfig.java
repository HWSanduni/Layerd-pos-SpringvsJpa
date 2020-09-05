package lk.ijse.dep.pos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@PropertySource("classpath:application.properties")
@Configuration
public class JPAConfig {


    @Autowired
    private Environment evn;

    @Bean
    public static PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds){
        LocalContainerEntityManagerFactoryBean lcemf = new LocalContainerEntityManagerFactoryBean();
        lcemf.setDataSource(ds);
        lcemf.setPackagesToScan("lk.ijse.dep.pos");
        lcemf.setJpaVendorAdapter(jpaVendorAdapter());
        return lcemf;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(evn.getRequiredProperty("javax.persistence.jdbc.driver"));
        ds.setUsername( evn.getRequiredProperty("javax.persistence.jdbc.user"));
        ds.setPassword( evn.getRequiredProperty("javax.persistence.jdbc.password"));
        ds.setUrl( evn.getRequiredProperty("javax.persistence.jdbc.url"));
        return ds;

    }


    private JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setDatabasePlatform(evn.getRequiredProperty("hibernate.dialect"));
        adapter.setShowSql(evn.getRequiredProperty("hibernate.show_sql",Boolean.class));
        adapter.setGenerateDdl(evn.getRequiredProperty("hibernate.hbm2ddl.auto").equals("update")?true:false );

        return adapter;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

}
