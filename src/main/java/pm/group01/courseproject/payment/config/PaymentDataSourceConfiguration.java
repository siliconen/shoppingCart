package pm.group01.courseproject.payment.config;

import com.zaxxer.hikari.HikariDataSource;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pm.group01.courseproject.payment.model.Payment;

import javax.sql.DataSource;
/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pm.group01.courseproject.payment.repository",
        entityManagerFactoryRef = "paymentEntityManagerFactory",
        transactionManagerRef = "paymentTransactionManager"
)
public class PaymentDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("app.datasource.payment")
    public DataSourceProperties paymentDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.payment.config")
    public DataSource paymentDataSource() {
        return paymentDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "paymentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean paymentEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(paymentDataSource())
                .packages(Payment.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager paymentTransactionManager(
            final @Qualifier("paymentEntityManagerFactory") LocalContainerEntityManagerFactoryBean paymentEntityManagerFactory) {
        return new JpaTransactionManager(paymentEntityManagerFactory.getObject());
    }
}