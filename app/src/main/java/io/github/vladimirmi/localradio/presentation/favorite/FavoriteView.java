package io.github.vladimirmi.localradio.presentation.favorite;

import java.util.List;

import io.github.vladimirmi.localradio.data.entity.Station;
import io.github.vladimirmi.localradio.presentation.core.BaseView;

/**
 * Created by Vladimir Mikhalev 13.04.2018.
 */
public interface FavoriteView extends BaseView {

    void selectStation(List<Station> stations);
}
