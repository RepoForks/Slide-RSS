package me.ccrama.rssslide;

import android.app.Activity;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Carlos on 3/16/2017.
 */

public class XMLToRealm {
    public static void convert(final String URL, final List<FeedParser.Entry> items, final ConversionCallback c, Activity baseActivity){
        baseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Listing l = realm.createObject(Listing.class);
                        l.time = System.currentTimeMillis();
                        l.URL = URL;
                        l.init();
                        for(FeedParser.Entry i : items){
                            Article a = new Article();
                            a.setAll(i);
                            realm.copyToRealmOrUpdate(a);
                            l.addArticle(a);
                        }
                        c.onCompletion(l);
                    }
                });

            }
        });

    }
}
