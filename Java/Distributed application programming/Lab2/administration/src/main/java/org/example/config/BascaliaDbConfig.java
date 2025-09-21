package org.example.config;

import jakarta.persistence.EntityManagerFactory;
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
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "org.example.repository.bascalia",
    entityManagerFactoryRef = "bascaliaEntityManagerFactory",
    transactionManagerRef = "bascaliaTransactionManager"
)
public class BascaliaDbConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.bascalia")
  public DataSourceProperties bascaliaDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public DataSource bascaliaDataSource() {
    return bascaliaDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean bascaliaEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(bascaliaDataSource())
        .packages("org.example.domain")
        .persistenceUnit("bascalia")
        .build();
  }

  @Bean
  public PlatformTransactionManager bascaliaTransactionManager(
      @Qualifier("bascaliaEntityManagerFactory") EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
