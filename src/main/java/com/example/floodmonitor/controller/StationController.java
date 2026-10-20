package com.example.floodmonitor.controller;

import com.example.floodmonitor.model.Station;
import com.example.floodmonitor.service.SimulationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StationController {

    private final SimulationService simulationService;

    public StationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @GetMapping("/stations")
    public List<Station> getStations() {
        return simulationService.generateStations();
    }

    @GetMapping("/stations/{id}")
    public Station getStation(@PathVariable int id) {
        if (id < 1 || id > simulationService.getCount()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found");
        }
        return simulationService.generateStation(id);
    }

    @GetMapping("/stations/count")
    public int getCount(){return simulationService.getCount();}

    @PostMapping("/stations")
    public ResponseEntity<Station> addStation(@RequestBody Station station) {
        Station createdStation = simulationService.addStation(station);
        return ResponseEntity
            .created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStation.getId())
                .toUri())
            .body(createdStation);
    }
}
