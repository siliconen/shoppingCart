package pm.group01.courseproject.cart.config;

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
import pm.group01.courseproject.cart.model.ShoppingCart;

import javax.sql.DataSource;

/*
 * Authored with surgical percison by Islam Ahmad
 * */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pm.group01.courseproject.cart.repository",
        entityManagerFactoryRef = "cartEntityManagerFactory",
        transactionManagerRef = "cartTransactionManager"
)
public class CartDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("app.datasource.cart")
    public DataSourceProperties cartDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.cart.config")
    public DataSource cartDataSource() {
        return cartDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "cartEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean cartEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(cartDataSource())
                .packages(ShoppingCart.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager cartTransactionManager(
            final @Qualifier("cartEntityManagerFactory") LocalContainerEntityManagerFactoryBean cartEntityManagerFactory) {
        return new JpaTransactionManager(cartEntityManagerFactory.getObject());
    }
}