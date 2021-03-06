package com.kyleboon.contact.healthchecks

import com.yammer.metrics.core.HealthCheck
import org.hibernate.SessionFactory

import static com.yammer.metrics.core.HealthCheck.Result.*


public class MySQLHealthCheck extends HealthCheck {
    private final SessionFactory sessionFactory

    public MySQLHealthCheck(SessionFactory sessionFactory) {
        super('MySQL')
        this.sessionFactory = sessionFactory
    }

    @Override
    protected com.yammer.metrics.core.HealthCheck.Result check() {
        if (sessionFactory.closed) {
            return unhealthy('Session Factory is Closed!')

        }

        return healthy()
    }
}
