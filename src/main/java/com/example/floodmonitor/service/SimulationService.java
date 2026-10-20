package com.example.floodmonitor.service;

import com.example.floodmonitor.model.Station;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SimulationService {

    private static final String[] STATION_NAMES = {
        "Station Vienna", "Station Linz", "Station Salzburg",
        "Station Graz", "Station Innsbruck"
    };

    private final Random random = new Random();
    private final List<Station> stations = new ArrayList<>();
    private int nextStationId = STATION_NAMES.length + 1;

    public SimulationService() {
        for (int i = 0; i < STATION_NAMES.length; i++) {
            stations.add(createStation(String.valueOf(i + 1), STATION_NAMES[i]));
        }
    }

    public List<Station> generateStations() {
        return List.copyOf(stations);
    }

    public Station generateStation(int stationNum) {
        String id = String.valueOf(stationNum);
        return stations.stream()
            .filter(station -> station.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public Station addStation(Station station) {
        station.setId(String.valueOf(nextStationId++));
        stations.add(station);
        return station;
    }

    public int getCount() {
        return stations.size();
    }

    private Station createStation(String id, String stationName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Station station = new Station();
        station.setId(id);
        station.setStationName(stationName);
        station.setTimestamp(timestamp);
        station.setWaterLevel(Math.round((0.5 + random.nextDouble() * 4.5) * 100.0) / 100.0);
        station.setWaterTemperature(Math.round((4.0 + random.nextDouble() * 16.0) * 10.0) / 10.0);
        station.setWaterflow(Math.round((random.nextDouble() * 25) * 10.0) / 10.0);
        station.setUnit("m");
        return station;
    }
}
