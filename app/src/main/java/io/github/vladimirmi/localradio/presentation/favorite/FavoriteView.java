package io.github.vladimirmi.localradio.presentation.favorite;

import io.github.vladimirmi.localradio.data.entity.Station;
import io.github.vladimirmi.localradio.presentation.core.BaseView;

/**
 * Created by Vladimir Mikhalev 13.04.2018.
 */
public interface FavoriteView extends BaseView {

    void selectStation(Station station);

    void setSelectedPlaying(boolean playing);

    void showPlaceholder();

    void hidePlaceholder();
}
