package org.example;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class FlywayManualConfig {

    @Bean
    public Flyway customFlyway(DataSource dataSource) {
      return Flyway.configure()
          .dataSource(dataSource)
          .locations("classpath:/db/migration")
          .baselineOnMigrate(true)
          .load();
    }

    @EventListener(ContextRefreshedEvent.class)
    public void runFlywayMigrations(ContextRefreshedEvent event) {
      Flyway flyway = event.getApplicationContext().getBean(Flyway.class);
      flyway.migrate(); // запускаем миграции после полной инициализации контекста
    }
  }
