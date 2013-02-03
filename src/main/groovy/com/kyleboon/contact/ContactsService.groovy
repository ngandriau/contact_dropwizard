package com.kyleboon.contact

import com.kyleboon.contact.db.ContactDAO
import com.kyleboon.contact.resources.ContactResource
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle
import com.kyleboon.contact.core.Contact
import com.kyleboon.contact.core.Address

/**
 * User: kboon
 * Date: 11/14/12
 */
class ContactsService extends Service<ContactsConfiguration> {
    public static void main(String[] args) throws Exception {
        new ContactsService().run(args)
    }

    HibernateBundle<ContactsConfiguration> hibernateBundle =
        new HibernateBundle<ContactsConfiguration>([Contact, Address]) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(ContactsConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        }

    MigrationsBundle<ContactsConfiguration> migrationsBundle =
        new MigrationsBundle<ContactsConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(ContactsConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        }

    AssetsBundle assetsBundle = new AssetsBundle()

    @Override
    public void initialize(Bootstrap<ContactsConfiguration> bootstrap) {
        bootstrap.name = "hello-world"

        bootstrap.addBundle assetsBundle
        bootstrap.addBundle migrationsBundle
        bootstrap.addBundle hibernateBundle
    }

    @Override
    public void run(ContactsConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {

        final ContactDAO contactDAO = new ContactDAO(hibernateBundle.getSessionFactory())
        environment.addResource(new ContactResource(contactDAO))
    }
}
