package com.example.idus_exam.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        //스프링의 트랜잭션 값을 읽어서 SLAVE와 MASTER를 결정
        String dataSourceName = isCurrentTransactionReadOnly() ? "SLAVE" : "MASTER";
        //System.out.println("datasource : " + dataSourceName);
        return dataSourceName;
    }
}
