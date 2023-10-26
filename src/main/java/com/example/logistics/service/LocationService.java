package com.example.logistics.service;

import com.example.logistics.exception.LocationNotFoundException;
import com.example.logistics.model.Location;
import com.example.logistics.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(String locationName, Location newLocation) {
        Optional<Location> existingLocation = locationRepository.findById(locationName);
        if (existingLocation.isPresent()) {
            Location locationToUpdate = existingLocation.get();
            locationToUpdate.setLocationName(newLocation.getLocationName());
            locationToUpdate.setLongitude(newLocation.getLongitude());
            locationToUpdate.setLatitude(newLocation.getLatitude());
            return locationRepository.save(locationToUpdate);
        } else {
            throw new LocationNotFoundException("Location not found: " + locationName);
        }
    }

    public void removeLocation(String locationName) {
        locationRepository.deleteById(locationName);
    }

    public List<Location> findOptimalRoute(Location origin, Location destination) {
        // Initialize data structures for Dijkstra's algorithm
        Map<Location, Double> distance = new HashMap<>();
        Map<Location, Location> previous = new HashMap<>();
        PriorityQueue<Location> queue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

        // Initialize distances and queue
        distance.put(origin, 0.0);
        queue.add(origin);

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            if (current.equals(destination)) {
                // Found the destination, reconstruct the path
                return reconstructPath(origin, destination, previous);
            }

            // Iterate through neighboring locations
            for (Location neighbor : getNeighbors(current)) {
                double altDistance = distance.get(current) + calculateCost(current, neighbor);
                if (altDistance < distance.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    distance.put(neighbor, altDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // No path found
        return Collections.emptyList();
    }

    private List<Location> getNeighbors(Location location) {
        // Implement logic to retrieve the neighboring locations of the given location
        // For example, query a database to find locations connected to the current location.
        // Return a list of neighboring locations.
        // Make sure to adjust this logic based on how you represent location connections.

        // This is a simplified example:
        List<Location> neighbors = new ArrayList<>();
        // Add logic to retrieve neighbors here.
        return neighbors;
    }

    // Helper method to calculate the cost of traveling from one location to another
    private double calculateCost(Location from, Location to) {
        // Implement logic to calculate the cost (e.g., distance or travel cost) between two locations.
        // Return the cost as a double value.

        // This is a simplified example:
        float fromLongitude = from.getLongitude();
        float fromLatitude = from.getLatitude();
        float toLongitude = to.getLongitude();
        float toLatitude = to.getLatitude();

        // Calculate Euclidean distance as the cost (you can use a more complex distance formula):
        double distance = Math.sqrt(
                Math.pow(toLongitude - fromLongitude, 2) +
                        Math.pow(toLatitude - fromLatitude, 2)
        );

        return distance;
    }

    private List<Location> reconstructPath(Location origin, Location destination, Map<Location, Location> previous) {
        List<Location> path = new ArrayList<>();
        Location current = destination;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);
        return path;
    }
}
