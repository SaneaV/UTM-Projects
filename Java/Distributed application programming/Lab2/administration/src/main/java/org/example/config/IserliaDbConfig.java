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
    basePackages = "org.example.repository.iserlia",
    entityManagerFactoryRef = "iserliaEntityManagerFactory",
    transactionManagerRef = "iserliaTransactionManager"
)
public class IserliaDbConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.iserlia")
  public DataSourceProperties iserliaDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public DataSource iserliaDataSource() {
    return iserliaDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean iserliaEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(iserliaDataSource())
        .packages("org.example.domain")
        .persistenceUnit("iserlia")
        .build();
  }

  @Bean
  public PlatformTransactionManager iserliaTransactionManager(
      @Qualifier("iserliaEntityManagerFactory") EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
