/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wikift.server.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.wikift.support.repository")
public class DataSourceConfig {

    @Resource
    private Environment environment;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        boolean embeddedDatabase = Boolean.valueOf(environment.getProperty("wikift.database.embedded.enable"));
        if (embeddedDatabase) {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.H2)
                    .addScripts("schema.sql", "data.sql")
                    .build();
        } else {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            String dbType = environment.getProperty("wikift.database.type");
            Assert.notNull(dbType, "dababase type must not null");
            switch (dbType) {
                case "mysql":
                    String dbMySQLClass = environment.getProperty("wikift.database.mysql.class");
                    dataSource.setDriverClassName(dbMySQLClass);
                    Assert.notNull(dbMySQLClass, "mysql driver class must not null");
                    String dbMySQLUrl = environment.getProperty("wikift.database.mysql.url");
                    Assert.notNull(dbMySQLUrl, "mysql connection url must not null");
                    dataSource.setUrl(dbMySQLUrl);
                    String dbMySQLUser = environment.getProperty("wikift.database.mysql.username");
                    Assert.notNull(dbMySQLUser, "mysql connection user name must not null");
                    dataSource.setUsername(dbMySQLUser);
                    String dbMySQLUserPassword = environment.getProperty("wikift.database.mysql.password");
                    Assert.notNull(dbMySQLClass, "mysql connection user password must not null");
                    dataSource.setPassword(dbMySQLUserPassword);
            }
            return dataSource;
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) throws PropertyVetoException {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan(new String[]{"com.wikift.model"});
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}
