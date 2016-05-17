package in.codingeek.movies.realm.config;


import android.content.Context;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by root on 16/5/16.
 */
public class RealmConfig {

    public static Realm realm;

    public Realm initializeRealm(Context context) {

        if(realm != null) {
            realm.close();
        }

        RealmMigration migration = new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                RealmSchema schema = realm.getSchema();

                if (oldVersion == 0) {
                    schema.create("MovieObj")
                            .addField("movieName", String.class)
                            .addField("posterPath", String.class)
                            .addField("backdropPath", String.class)
                            .addField("releaseDate", String.class)
                            .addField("overview", String.class)
                            .addField("movieId", String.class)
                            .addField("voteAverage", String.class);
                    oldVersion++;
                }
            }
        };

        RealmConfiguration configuration = new RealmConfiguration
                .Builder(context)
                .migration(migration)
                .build();
        realm = Realm.getInstance(configuration);
        return realm;
    }
}
