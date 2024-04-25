package com.yogendra.config.account;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.postgresql.xa.PGXADataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.yogendra.account.repository")
public class AccountConfiguration {

    private final AccountProperties accountProperties;

    AccountConfiguration(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
    }


    @Bean
    public DataSource dataSource() {
        PGXADataSource dataSource = new PGXADataSource();
        dataSource.setURL(accountProperties.url());
        dataSource.setUser(accountProperties.username());
        dataSource.setPassword(accountProperties.password());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(dataSource);
        xaDataSource.setUniqueResourceName("xads-account");
        return xaDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);

        Properties properties = new Properties();
        properties.setProperty("hibernate.format_sql", "true");
        properties.put("javax.persistence.transactionType", "JTA");

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.yogendra.domain");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(adapter);
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("account");
        return localContainerEntityManagerFactoryBean;
    }

}
