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
    basePackages = "org.example.repository.basarabeasca",
    entityManagerFactoryRef = "basarabeascaEntityManagerFactory",
    transactionManagerRef = "basarabeascaTransactionManager"
)
public class BasarabeascaDbConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.basarabeasca")
  public DataSourceProperties basarabeascaDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public DataSource basarabeascaDataSource() {
    return basarabeascaDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean basarabeascaEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(basarabeascaDataSource())
        .packages("org.example.domain")
        .persistenceUnit("basarabeasca")
        .build();
  }

  @Bean
  public PlatformTransactionManager basarabeascaTransactionManager(
      @Qualifier("basarabeascaEntityManagerFactory") EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
