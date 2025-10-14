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
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
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
@EnableJpaRepositories(basePackages = "br.com.ctcea.gestaoinv.repositories.gestaoinv", // Pacote onde os repositórios
																						// JPA estão
		entityManagerFactoryRef = "entityManagerFactoryGestaoInv", // Referência ao bean do EntityManagerFactory
		transactionManagerRef = "transactionManagerGestaoInv" // Referência ao bean do TransactionManager
)
@EntityScan(basePackages = "br.com.ctcea.gestaoinv.entities.gestaoinv")
public class DBGestaoInvConfig {

	@Value("${spring.datasource.gestaoinv.url}")
	private String dbUrl;

	@Value("${spring.datasource.gestaoinv.username}")
	private String dbUser;

	@Value("${spring.datasource.gestaoinv.password}")
	private String dbPassword;

	private final Environment env;

	public DBGestaoInvConfig(Environment env) {
		this.env = env;
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.gestaoinv")
	DataSource dataSourceGestaoInv() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPassword);

		String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "default";
		if ("test".equals(profile)) {
			dataSource.setDriverClassName("org.h2.Driver");
		} else {
			dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		}

		return dataSource;
	}

	@Bean
	@Primary
	LocalContainerEntityManagerFactoryBean entityManagerFactoryGestaoInv(
			@Qualifier("dataSourceGestaoInv") DataSource dataSourceGestaoInv) {

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSourceGestaoInv);
		factoryBean.setPackagesToScan("br.com.ctcea.gestaoinv.entities.gestaoinv");
		factoryBean.setPersistenceUnitName("gestaoinv");

		// Definir explicitamente o provedor de persistência (Hibernate)
		factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Map<String, Object> jpaProperties = new HashMap<>();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		jpaProperties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql", "false"));
		jpaProperties.put("hibernate.format_sql",
				env.getProperty("spring.jpa.properties.hibernate.format_sql", "false"));
		jpaProperties.put("hibernate.implicit_naming_strategy",
				"org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
		jpaProperties.put("hibernate.physical_naming_strategy",
				"org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
		factoryBean.setJpaPropertyMap(jpaProperties);

		return factoryBean;
	}

	@Bean
	@Primary
	PlatformTransactionManager transactionManagerGestaoInv(
			@Qualifier("entityManagerFactoryGestaoInv") EntityManagerFactory entityManagerFactoryGestaoInv) {

		return new JpaTransactionManager(entityManagerFactoryGestaoInv);
	}
}
