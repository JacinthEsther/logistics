package com.example.logistics.controller.locations;

import com.example.logistics.service.LocationService;
import com.example.logistics.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @PostMapping
    public Location addLocation(@RequestBody Location location) {
        return locationService.addLocation(location);
    }

    @PutMapping("/{locationName}")
    public Location updateLocation(@PathVariable String locationName, @RequestBody Location location) {
        return locationService.updateLocation(locationName, location);
    }

    @DeleteMapping("/{locationName}")
    public void removeLocation(@PathVariable String locationName) {
        locationService.removeLocation(locationName);
    }

}
