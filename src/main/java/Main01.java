import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Main01 {
    private static double totalDistance = 0;
    private static List<City> visitedCities = new LinkedList<>();
    private static Duration interval;

    public static void main(String[] args) throws IOException {

        WorldBuilder.generateCities(1500);

//        --- exec time measure below this point
        Instant startClock = Instant.now();

        List<City> myWorld = WorldBuilder.createWorld();
        City startPoint = myWorld.get(0);
        float[][] distanceMatrix = WorldBuilder.computeDistance(myWorld);
        computeRoute(startPoint, myWorld, distanceMatrix);

        Instant stopClock = Instant.now();
//        --- exec time measure stopped below this pont

        interval = Duration.between(startClock, stopClock);
        recordResult();

//        WorldBuilder.generateCities(100);

//        WorldBuilder.createWorld();
//        float[][] distance = WorldBuilder.computeDistance();
//        for (int i = 0; i < distance.length; i++) {
//            for (int j = 0; j < distance.length; j++) {
//                System.out.print(distance[i][j] + " ");
//            }
//            System.out.println();
//        }
    }

    private static void computeRoute(City startCity, List<City> myWorld, float[][] distanceMatrix) {
        int currentCityId = startCity.getId();
        for (int i = 0; i < myWorld.size(); i++) {
            visitedCities.add(myWorld.get(currentCityId));
            float[] distancesFromCurrentCity = distanceMatrix[currentCityId];
            int nearestCityId = -1;
            float shortestDistance = Float.MAX_VALUE;
            for (int j = 0; j < distancesFromCurrentCity.length; j++) { // start search for nearest City to City where id = currentCityId
                if (distancesFromCurrentCity[j] <= shortestDistance) {
                    shortestDistance = distancesFromCurrentCity[j];
                    nearestCityId = j;
                }
                distanceMatrix[j][currentCityId] = Float.POSITIVE_INFINITY; // disabling City id=currentCityId for further search (that City will have infinite distance in further searches)
            }
            if (shortestDistance != Float.MAX_VALUE) {
                totalDistance += shortestDistance;
            }
            currentCityId = nearestCityId;
        }
    }

    private static void recordResult() throws IOException {
        FileWriter record = new FileWriter("result_shortest_route.txt", true);
        record.append("\n").append("---------- new calculation -----------").append("\n");
        record.append("Visited cities: ").append(String.valueOf(visitedCities.size())).append("\n");
        record.append("Total travelled distance: ").append(String.format("%.2f", totalDistance)).append("\n");
        record.append("Execution time approx.: ").append(String.valueOf(interval.get(SECONDS))).append(" s").append("\n");
        record.append("Visited cities (in visit order):").append("\n");
        for (City visitedCity : visitedCities) {
            record.append(visitedCity.toString()).append("\n");
        }
        record.close();
    }
}
