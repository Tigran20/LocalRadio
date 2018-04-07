package io.github.vladimirmi.localradio.presentation.stations;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import io.github.vladimirmi.localradio.R;
import io.github.vladimirmi.localradio.data.entity.Station;
import io.github.vladimirmi.localradio.di.Scopes;
import io.github.vladimirmi.localradio.presentation.core.BaseFragment;

/**
 * Created by Vladimir Mikhalev 06.04.2018.
 */

public class StationsFragment extends BaseFragment<StationsPresenter> implements StationsView {

    private StationsAdapter stationsAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_stations;
    }

    @Override
    protected StationsPresenter providePresenter() {
        return Scopes.getAppScope().getInstance(StationsPresenter.class);
    }

    @Override
    protected void setupView(View view) {
        RecyclerView stationList = (RecyclerView) view;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        stationList.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(stationList.getContext(),
                layoutManager.getOrientation());
        stationList.addItemDecoration(itemDecoration);
        stationsAdapter = new StationsAdapter();
        stationList.setAdapter(stationsAdapter);
    }

    @Override
    public void setStations(List<Station> stations) {
        stationsAdapter.setStations(stations);
    }
}