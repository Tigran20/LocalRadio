package io.github.vladimirmi.localradio.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class Preferences {

    private static final String KEY_AUTODETECT = "AUTODETECT";
    public final Preference<Boolean> autodetect;

    private static final String KEY_COUNTRY_CODE = "COUNTRY_CODE";
    public final Preference<String> countryCode;

    private static final String KEY_CITY = "CITY";
    public final Preference<String> city;

    private static final String KEY_SEARCH_DONE = "SEARCH_DONE";
    public final Preference<Boolean> isSearchDone;

    private static final String KEY_CURRENT_STATION = "CURRENT_STATION";
    public final Preference<Integer> currentStation;

    private static final String KEY_PAGE = "PAGE";
    public final Preference<Integer> pagePosition;

    @Inject
    public Preferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("default", Context.MODE_PRIVATE);

        autodetect = new Preference<>(prefs, KEY_AUTODETECT, false);
        countryCode = new Preference<>(prefs, KEY_COUNTRY_CODE, "");
        city = new Preference<>(prefs, KEY_CITY, "");
        isSearchDone = new Preference<>(prefs, KEY_SEARCH_DONE, false);
        currentStation = new Preference<>(prefs, KEY_CURRENT_STATION, 0);
        pagePosition = new Preference<>(prefs, KEY_PAGE, 2);
    }
}
