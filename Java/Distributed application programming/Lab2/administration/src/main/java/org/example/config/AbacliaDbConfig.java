package org.example.config;

import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "org.example.repository.abaclia",
    entityManagerFactoryRef = "abacliaEntityManagerFactory",
    transactionManagerRef = "abacliaTransactionManager"
)
public class AbacliaDbConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.abaclia")
  public DataSourceProperties abacliaDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public DataSource abacliaDataSource() {
    return abacliaDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean abacliaEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(abacliaDataSource())
        .packages("org.example.domain")
        .persistenceUnit("abaclia")
        .build();
  }

  @Bean
  public PlatformTransactionManager abacliaTransactionManager(
      @Qualifier("abacliaEntityManagerFactory") EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }

  @Bean
  public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
    return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
  }
}
