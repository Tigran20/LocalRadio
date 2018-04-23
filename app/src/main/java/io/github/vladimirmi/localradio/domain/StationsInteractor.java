package io.github.vladimirmi.localradio.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.github.vladimirmi.localradio.data.entity.Station;
import io.github.vladimirmi.localradio.data.repository.StationsRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Vladimir Mikhalev 07.04.2018.
 */

public class StationsInteractor {

    private final StationsRepository stationsRepository;
    private List<Station> filteredStations;
    private String filter = "";

    @Inject
    public StationsInteractor(StationsRepository stationsRepository) {
        this.stationsRepository = stationsRepository;
    }

    public Observable<List<Station>> getStationsObs() {
        return stationsRepository.stations
                .map(this::filter);
    }

    public List<Station> getStations() {
        return stationsRepository.stations.hasValue()
                ? stationsRepository.stations.getValue()
                : Collections.emptyList();
    }

    public Observable<Station> getCurrentStationObs() {
        return stationsRepository.currentStation;
    }

    public Station getCurrentStation() {
        return stationsRepository.currentStation.getValue();
    }

    public Completable loadUrlForCurrentStation() {
        return stationsRepository.loadUrlForCurrentStation();
    }

    public void setCurrentStation(Station station) {
        stationsRepository.setCurrentStation(station);
    }

    public void previousStation() {
        int indexOfCurrent = filteredStations.indexOf(getCurrentStation());
        if (indexOfCurrent == -1) return;

        int indexOfPrevious = (indexOfCurrent + filteredStations.size() - 1) % filteredStations.size();
        setCurrentStation(filteredStations.get(indexOfPrevious));
    }

    public void nextStation() {
        int indexOfCurrent = filteredStations.indexOf(getCurrentStation());
        if (indexOfCurrent == -1) return;

        int indexOfNext = (indexOfCurrent + 1) % filteredStations.size();
        setCurrentStation(filteredStations.get(indexOfNext));
    }

    public void filterStations(String filter) {
        this.filter = filter.toLowerCase();
        stationsRepository.stations.accept(stationsRepository.stations.getValue());
    }

    private List<Station> filter(List<Station> stations) {
        if (filter.isEmpty()) {
            filteredStations = stations;
            return filteredStations;
        }
        filteredStations = new ArrayList<>();

        for (Station station : stations) {
            if (checkCanFilter(station.getCallsign())
                    || checkCanFilter(station.getGenre())
                    || checkCanFilter(station.getDial())
                    || checkCanFilter(station.getBand())) {
                filteredStations.add(station);
            }
        }
        return filteredStations;
    }

    private boolean checkCanFilter(String field) {
        return field != null && field.toLowerCase().contains(filter);
    }

    public String getFilter() {
        return filter;
    }
}
