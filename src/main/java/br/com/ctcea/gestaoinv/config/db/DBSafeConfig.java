package br.com.ctcea.gestaoinv.config.db;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "br.com.ctcea.gestaoinv.repositories.safe",   // Pacote onde os repositórios JPA estão
    entityManagerFactoryRef = "entityManagerFactorySafe",   // Referência ao bean do EntityManagerFactory
    transactionManagerRef = "transactionManagerSafe"   // Referência ao bean do TransactionManager
)
@EntityScan(basePackages = "br.com.ctcea.gestaoinv.entities.safe")
public class DBSafeConfig {
	
	@Value("${spring.datasource.safe.url}")
    private String dbUrl;

    @Value("${spring.datasource.safe.username}")
    private String dbUser;

    @Value("${spring.datasource.safe.password}")
    private String dbPassword;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.safe")
    DataSource dataSourceSafe() {
    	HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactorySafe(
        @Qualifier("dataSourceSafe") DataSource dataSourceSafe) {
        
    	LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSourceSafe);
        factoryBean.setPackagesToScan("br.com.ctcea.gestaoinv.entities.safe");
        factoryBean.setPersistenceUnitName("safe");
        factoryBean.setJpaPropertyMap(jpaProperties());
        
        // Definir explicitamente o provedor de persistência (Hibernate)
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return factoryBean;
    }

    @Bean
    PlatformTransactionManager transactionManagerSafe(
        @Qualifier("entityManagerFactorySafe") EntityManagerFactory entityManagerFactorySafe) {
        
        return new JpaTransactionManager(entityManagerFactorySafe);
    }
    
    private Map<String, Object> jpaProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.jdbc.lob.non_contextual_creation", true);
        return properties;
    }
}